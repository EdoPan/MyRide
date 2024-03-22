%%%-------------------------------------------------------------------
%% Module that instantiate a chat server using Cowboy library.
%%%-------------------------------------------------------------------
-module(chat_server).

-export([start_chat/3, end_chat/3, send_message/4, send_message_in_chat/3, get_chat_requests/0]).

% Get list of currently chat requests
get_chat_requests() ->
	io:format("[chat_server] get_chat_requests"),
	Requests = mnesia_manager:get_chat_requests(),
	Message = jsone:encode(
        #{
            <<"opcode">> => <<"GET_CHAT_REQUESTS">>,
            <<"list">> => Requests
        }
    ),
	Message.

% Execute start_chat for user
start_chat(Pid, Username, BikeID) when is_pid(Pid), is_integer(BikeID) ->
	io:format("[chat_server] start_chat => pid ~p, bike_id ~p~n", [Pid, BikeID]),
	mnesia_manager:start_chat(Pid, Username, BikeID).



% Execute end_chat for user
end_chat(Pid, Username, BikeID) when is_pid(Pid), is_integer(BikeID)->
	io:format("[chat_server] end_chat => pid ~p, bike_id ~p~n", [Pid, BikeID]),
	% Remove the websocket PID from DB list of users inside the chat
	mnesia_manager:end_chat(Pid, Username, BikeID),
	ok.


% Send a message in the chat
send_message(PidSender, SenderName, BikeID, Text) 
		when is_pid(PidSender), is_integer(BikeID), is_list(Text) ->
	io:format(
		"[chat_server] send_message => pid ~p, username ~p, bike_id ~p, text ~p~n", 
		[PidSender, SenderName, BikeID, Text]
	),
	% Get list of currently online users and forward the message to all of them
	case mnesia_manager:get_bike_pids(BikeID) of

		List when is_list(List), List /= [] ->
			% Prepare the message as a JSON document
			Message = jsone:encode(
				#{
					<<"opcode">> => <<"MESSAGE">>,
					<<"sender">> => list_to_binary(SenderName),
					<<"text">> => list_to_binary(Text)
				}
			),
			io:format("[chat_server] send_message => send message ~p to ~p~n", [Message, List]),
			% Send the message to the receiver
			send_message_in_chat(List, PidSender, Message);

		_ ->
			io:format("[chat_server] send_message => error the bike_id does not exist"),
			% The list was empty or the call returned a wrong result, 
			% so the course does not exist
			PidSender ! {send_message, PidSender, "Error: bike_id does not exist~n"}
	end,
	ok.


%% send_message_in_chat/3: send a message in a chat
send_message_in_chat([], _PidSender, _Message) when is_pid(_PidSender) ->
	%stop recursion
	ok;

send_message_in_chat([PidReceiver | T], PidSender, Message) 
		when is_pid(PidReceiver), is_pid(PidSender), PidReceiver /= PidSender ->
	% Send the message to the user
	io:format("[chat_server] send_message_in_chat => sending message to ~p~n", [PidReceiver]),
	PidReceiver ! {send_message, Message},
	send_message_in_chat(T, PidSender, Message);

send_message_in_chat([_H | T], PidSender, Message) when is_pid(_H), is_pid(PidSender)->
	% The current head of the PID list is the sender process, so
	% do not send a message but keep going with the recursion
	send_message_in_chat(T, PidSender, Message).
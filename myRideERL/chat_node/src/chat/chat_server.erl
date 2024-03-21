%%%-------------------------------------------------------------------
%% Module that instantiate a chat server using Cowboy library.
%%%-------------------------------------------------------------------
-module(chat_server).

-export([start_chat/3, end_chat/3, send_message/4, send_message_in_chat/3]).



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
send_message(PidSender, Username, BikeID, Text) 
		when is_pid(PidSender), is_integer(BikeID), is_list(Text) ->
	io:format(
		"[chat_server] send_message => pid ~p, username ~p, bike_id ~p, text ~p~n", 
		[PidSender, Username, BikeID, Text]
	),
	% Get list of maintainers and forward the message to all of them
	case mnesia_manager:get_maintainer_pid(BikeID) of

		List when is_list(List), List /= [] ->
			% Prepare the message as a JSON document
			Message = jsone:encode(
				#{
					<<"opcode">> => <<"MESSAGE">>,
					<<"sender">> => list_to_binary(Username),
					<<"text">> => list_to_binary(Text)
				}
			),
			io:format("[chat_server] send_message => send message ~p to ~p~n", [Message, List]),
			% Send the message inside the chat
			send_message_in_chat(List, PidSender, Message);

		_ ->
			io:format("[chat_server] send_message => error the bike does not exist"),
			% The list was empty or the call returned a wrong result, 
			% so the bike does not exist
			PidSender ! {send_message, PidSender, "Error: bike does not exist~n"}
	end,
	ok.

%% send_message_in_chat/3: send a message in a chat
send_message_in_chat([], _PidSender, _Message) when is_pid(_PidSender) ->
	% No more users to send the message => stop recursion
	ok;

send_message_in_chat([PidReceiver | T], PidSender, Message) 
		when is_pid(PidReceiver), is_pid(PidSender), PidReceiver /= PidSender ->
	% Send the message to all users except from the sender
	io:format("[chat_server] send_message_in_chat => sending message to ~p~n", [PidReceiver]),
	PidReceiver ! {send_message, Message},
	send_message_in_chat(T, PidSender, Message);

send_message_in_chat([_H | T], PidSender, Message) when is_pid(_H), is_pid(PidSender)->
	% The current head of the PID list is the sender process, so
	% do not send a message but keep going with the recursion
	send_message_in_chat(T, PidSender, Message).
%%%-------------------------------------------------------------------
%% Module that instantiate a chat server using Cowboy library.
%%%-------------------------------------------------------------------
-module(chat_server).

-export([start_chat/3, end_chat/3, send_message/4]).



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
    Message = jsone:encode(
        #{
            <<"opcode">> => <<"MESSAGE">>,
            <<"sender">> => list_to_binary(Username),
            <<"text">> => list_to_binary(Text)
        }
    ),
    io:format("[chat_server] send_message => send message ~p", [Message]),
	ok.
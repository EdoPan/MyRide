-module(mnesia_manager).

-export([get_chat_requests/0]).

% Get all the active chat requests
get_chat_requests()-> 
	Fun = fun() ->
        io:format("[mnesia_manager] get_chat_requests => Get all the chat requests~n"),
		mnesia:match_object({requests,'_', '_', '_'})
    end,
	{atomic, Result} = mnesia:transaction(Fun),
	io:format("[mnesia_manager] get_chat_requests => Chat Requests: ~p~n", [Result]),
	Result.
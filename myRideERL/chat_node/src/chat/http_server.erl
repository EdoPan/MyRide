-module(http_server).
-export([init/2]).

init(Req, _State) ->
    get_chat_requests(Req, _State).


% Get list of currently chat requests
%get_chat_requests(Req, State) ->
%	io:format("[chat_server] get_chat_requests"),
%	Requests = mnesia_manager:get_chat_requests(),
%	
%	JsonBody = jsone:encode({[{<<"data">>, Requests}]}),
%	{ok, Response} = cowboy_req:reply(200, #{<<"content-type">> => <<"text/plain">>}, JsonBody, Req),
%	{ok, Response, State}.


get_chat_requests(Req, State) ->
	io:format("[chat_server] get_chat_requests"),
	Requests = mnesia_manager:get_chat_requests(),
	Message = jsone:encode(
        #{
            <<"opcode">> => <<"GET_CHAT_REQUESTS">>,
            <<"list">> => Requests
        }
    ),
	Message.

-module(http_server).
-export([init/2, get_chat_requests/2]).

init(Req, _State) ->
    %Req1 = cowboy_req:set_resp_header(<<"access-control-allow-methods">>, <<"GET">>, Req),
    %Req2 = cowboy_req:set_resp_header(<<"access-control-allow-origin">>, <<"*">>, Req1),
    %{ok, Req2, _State},
    get_chat_requests(Req, _State).


get_chat_requests('GET', _Arg) ->
    %io:format("~n ~p:~p GET Request ~n", [?MODULE, ?LINE]),
    Requests = mnesia_manager:get_chat_requests(),
    Json = jsone:encode(
        #{
            <<"data">> => Requests
        }
    ),
    {html, Json}.

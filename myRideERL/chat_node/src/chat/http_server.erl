-module(http_server).

-export([init/2, get_chat_requests/2]).

init(Req, _State) ->
    get_chat_requests(Req, _State).

get_chat_requests('GET', _Arg) ->
    Requests = mnesia_manager:get_chat_requests(),
    Json = jsone:encode(
        #{
            <<"data">> => Requests
        }
    ),
    {html, Json}.
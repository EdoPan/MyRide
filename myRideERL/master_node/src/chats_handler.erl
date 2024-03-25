-module(chats_handler).
%-behaviour(cowboy_rest).

%% Callback Callbacks
-export([allowed_methods/2]).
-export([content_types_accepted/2]).
-export([resource_exists/2]).
-export([commands/2]).

-export([handle/3, init/2]).

%% Helpes
-import(helper, [get_body/2, get_model/3, reply/3]).

-record(requests, {user_pid, username, bike_id}).

init(Req, State) ->
    io:format("DEBUG1"),
    Method = cowboy_req:method(Req),
    HasBody = cowboy_req:has_body(Req),
    Reply = handle(Method, HasBody, Req),
    {ok, Reply, State}.
    %{cowboy_rest, Req, State}.

allowed_methods(Req, State) ->
    {[<<"GET">> , <<"POST">>], Req, State}.

commands(Req, State) ->
    {reply, State, Req}.

content_types_accepted(Req, State) ->
    {
        [
            {<<"application">>, <<"json">>, handle},
            {<<"application/json">>, handle}
        ],
        Req,
        State
    }.

resource_exists(Req, State) ->
    {false, Req, State}.

iter_chat_list(Requests) ->
    iter_chat_list(Requests, []).

iter_chat_list([], Acc) ->
    lists:reverse(Acc);

iter_chat_list([Request | Rest], Acc) ->
    #requests{user_pid = UserPid, username = Username, bike_id = BikeID} =
        Request,
    UserTuple = {<<"user_pid">>, UserPid},
    UsernameTuple = {<<"username">>, Username},
    BikeTuple = {<<"bike_id">>, BikeID},
    iter_chat_list(Rest, [UserTuple, UsernameTuple, BikeTuple | Acc]).

handle(_, true, Req) ->
    io:format("[CHATS REQUESTS HANDLER] Handling chats requests in handle/3~n", []),
    io:format("DEBUG2"),
    Requests = mnesia_manager:get_chat_requests(),
    ResultList = {iter_chat_list(Requests)},
    JsonString = jsone:encode(ResultList),
    BinString = iolist_to_binary([JsonString]),
    Resp = cowboy_req:reply(
        200,
        #{<<"content-type">> => <<"text/plain">>},
        BinString
        ),
    io:format("HISTORY HANDLER] Response sent from handle/3~n");

handle(_, _, Req) ->
    cowboy_req:reply(
        200,
        #{},
        <<>>,
        Req
    ).

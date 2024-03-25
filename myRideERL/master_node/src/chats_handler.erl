-module(chats_handler).
-behaviour(cowboy_rest).

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
    Method = cowboy_req:method(Req),
    HasBody = cowboy_req:has_body(Req),
    Reply = handle(Method, HasBody, Req),
    {ok, Reply, State},
    {cowboy_rest, Reply, State}.

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
    #requests{user_pid = UserPid, username = Username, bike_id = BikeID} = Request,
    UserTuple = {<<"user_pid">>, UserPid},
    UsernameTuple = {<<"username">>, Username},
    BikeTuple = {<<"bike_id">>, BikeID},
    iter_chat_list(Rest, [BikeTuple, UsernameTuple, UserTuple | Acc]).

handle(_, false, Req) ->
    io:format("[chats_handler] => handle~n", []),
    Requests = mnesia_manager:get_chat_requests(),
    ResultList = {iter_chat_list(Requests)},
    JsonString = jsone:encode(ResultList),
        Resp = cowboy_req:reply(         
            200,         
        #{<<"content-type">> => <<"application/json">>},         
        JsonString,
        Req
    ),
    {ok, Resp};

handle(_, _, Req) ->
    cowboy_req:reply(
        200,
        #{},
        <<>>,
        Req
    ).
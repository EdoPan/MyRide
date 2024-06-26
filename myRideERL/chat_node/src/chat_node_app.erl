-module(chat_node_app).
-behavior(application).

-export([start/2, stop/1]).

start(_StartType, _StartArgs) ->
    mnesia:start(),
    ok = mnesia:wait_for_tables([requests], 30000),
    io:format("[chat_node_app] start => mnesia is ready~n"),
    % Start supervisor
    chat_node_sup:start_link().

stop(_State) ->
    mnesia:stop(),
    ok.
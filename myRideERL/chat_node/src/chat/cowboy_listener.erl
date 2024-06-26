-module(cowboy_listener).
-behaviour(gen_server).

-export([start_link/0, init/1, handle_call/3, handle_cast/2]).

start_link() ->
	io:format("[cowboy_listener] start_link~n"),
    gen_server:start_link({local, ?MODULE}, ?MODULE, [], []).

init(_) ->
	% Cleanup Mnesia (in case of restoring from a crash)
	ok = mnesia_manager:remove_users_by_username(node()),
	% Read endpoint and port from configuration file
	{ok, Url} = application:get_env(websocket_endpoint),
	{ok, Port} = application:get_env(websocket_port),
	io:format("[cowboy_listener] init => Start listener on endpoint ~p and port ~p~n", [Url, Port]),

	% Compile the route for the websocket handler
	Dispatch = cowboy_router:compile([
	{'_', [
		{Url, chat_websocket, []}  % Define the WebSocket route
	]}
	]),
	% Start listening for connections over a clear TCP channel 
	{ok, Pid} = cowboy:start_clear(chat_listener,
		[{port, Port}],
		#{env => #{dispatch => Dispatch}}
	),
	io:format("[cowboy_listener] init => cowboy is listening from process ~p~n", [Pid]),
	{ok, []}.

handle_call(Req, _, State) ->
	{reply, Req, State}.

handle_cast(_, State) ->
	{noreply, State}.
-module(chat_websocket).

-export([init/2, websocket_handle/2, websocket_info/2, terminate/3]).


% Cowboy will call init/2 whenever a request is received,
% in order to establish a websocket connection.
init(Req, _State) ->
    % Switch to cowboy_websocket module
    {cowboy_websocket, Req, none}.


% Cowboy will call websocket_handle/2 whenever a text, binary, ping or pong frame arrives from the client.
websocket_handle(Frame = {text, Message}, State) ->
    io:format("[chat_websocket] websocket_handle => Frame: ~p, State: ~p~n", [Frame, State]),
    io:format("[chat_websocket] websocket_handle => Received ~p~n", [Frame]),
    
    DecodedMessage = jsone:try_decode(Message),

    Response = case element(1, DecodedMessage) of
        ok ->
            Json = element(2, DecodedMessage),
            handle_websocket_frame(Json, State);
        error ->
            io:format("[chat_websocket] websocket_handle => jsone:try_decode: error: ~p~n",[element(2, DecodedMessage)]),
            {ok, State}
    end,
    Response;

websocket_handle(_Frame, State) ->
    {ok, State}.



% Handle a frame after JSON decoding
handle_websocket_frame(Map, State) ->
    io:format("[chat_websocket] handle_websocket_frame => Map is ~p~n", [Map]),
    {ok, Opcode} = maps:find(<<"opcode">>, Map),

    Response = case Opcode of
        <<"START">> ->
            handle_start(Map, State);
        <<"MESSAGE">> ->
            handle_chat_message(Map, State)
    end,
    Response.



% Handle a start_chat request
handle_start(Map, _State) ->
    {ok, BikeID} = maps:find(<<"bike_id">>, Map),
    {ok, Username} = maps:find(<<"username">>, Map),
    io:format(
        "[chat_websocket] handle_start => start_chat request received for bike_id ~p by user ~p~n",
        [BikeID, Username]
    ),
    chat_server:start_chat(self(), Username, BikeID),
    {ok, {BikeID, Username}}. % init state with bike_id id and username



% Handle a new message sent in the chat
handle_chat_message(Map, State = {BikeID, Username}) ->
    io:format("[chat_websocket] handle_chat_message => message received from Pid: ~p in the bike_id: ~p ~n", [self(), BikeID]),
    {ok, Text} = maps:find(<<"text">>, Map),
    chat_server:send_message(self(),
                                binary_to_list(Username), 
                                BikeID, 
                                binary_to_list(Text)),
    {ok, State}.


% Cowboy will call websocket_info/2 whenever an Erlang message arrives 
% (=> from another Erlang process).
websocket_info({send_message, Msg}, State) ->
    io:format("[chat_websocket] websocket_info({send_message, Msg}, State) => Send message ~p~n", [Msg]),
    {[{text, Msg}], State};

websocket_info(Info, State) ->
    io:format("chat_websocket:websocket_info(Info, State) => Received info ~p~n", [Info]),
    {ok, State}.


% Cowboy will call terminate/3 with the reason for the termination of the connection. 
terminate(_Reason, _Req, _State = {BikeID, Username}) ->
    % end_chat user from chat
    io:format("[chat_websocket] terminate => end_chat request received from Pid: ~p in the bike_id: ~p ~n", [self(), BikeID]),
    chat_server:end_chat(self(), Username, BikeID),
    ok.
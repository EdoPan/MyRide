-module(mnesia_manager).

-export([start_chat/3, end_chat/3, remove_users_by_username/1, get_bike_pids/1, get_chat_requests/0]).

-record(requests, {user_pid, username, bike_id}).

% Add a user to a given chat
start_chat(UserPid, Username, BikeID) 
		when is_pid(UserPid), is_integer(BikeID) ->
	Fun = fun() ->
		io:format("[mnesia_manager] start_chat => Check if the pid of the user: ~p is already in a chat ~n", [UserPid]),
		NewUser = #requests{user_pid='$1', username = '$2', bike_id = '$3'},
		Guard = {'==', '$1', UserPid},
		NewUserCheck = mnesia:select(requests, [{NewUser, [Guard], ['$2']}]),

		io:format("[mnesia_manager] start_chat => Check result: ~p~n", [NewUserCheck]),
		case NewUserCheck == [] of 
			true ->
				io:format("[mnesia_manager] start_chat => user not in any chat, user can start this chat ~n"),
				mnesia:write(
					#requests{
						user_pid = UserPid,
						username = Username,
						bike_id = BikeID
					}
				);
		
			false ->
				io:format("[mnesia_manager] start_chat => user is already in a chat ~n"),
				false  
		end
	end,
	{atomic, Result} = mnesia:transaction(Fun),
	io:format("[mnesia_manager] start_chat => chat start request returned response: ~p~n",[Result]),
	Result.



% Remove a user from a given chat
end_chat(UserPid, Username, BikeID) when is_pid(UserPid), is_integer(BikeID) -> 
	Fun = fun() ->
		mnesia:delete_object(
			#requests{
				user_pid = UserPid,
				username = Username,
				bike_id = BikeID
			}
		),
		io:format("[mnesia_manager] end_chat => user ~p end_chat from the chat ~p~n", [Username, BikeID])
	end,

	{atomic, Result} = mnesia:transaction(Fun),
	io:format("[mnesia_manager] end_chat => transaction result is ~p~n", [Result]),
	Result.



% Remove all the users connected to this server
remove_users_by_username(Username) ->
	Fun = fun() ->
		io:format("[mnesia_manager] remove_users_by_username => Remove all users ~p~n", [Username]),
		Requests = #requests{username='$2', bike_id = '$3'},
		Guard = {'==', '$2', Username},
		RecordList = mnesia:select(requests, [{Requests, [Guard], ['$_']}]),
		delete_by_username(RecordList)
	end,
	{atomic, ok} = mnesia:transaction(Fun),
	io:format("[mnesia_manager] remove_users_by_username => transaction succeeded"),
	ok.

% Get all the active chat requests
get_chat_requests()-> 
	Fun = fun() ->
        io:format("[mnesia_manager] get_chat_requests => Get all the chat requests~n"),
		qlc:e(qlc:q([X || X <- mnesia:table(requests)]))
    end,
	{atomic, Result} = mnesia:transaction(Fun),
	io:format("[mnesia_manager] get_chat_requests => Chat Requests: ~p~n", [Result]),
	Result.

% Recursively remove all users connected to the chat through the crashed erlang node
delete_by_username([]) ->
	ok;

delete_by_username([{_, Username, BikeID} | T]) ->
	mnesia:delete_object(
		#requests{
			username = Username,
			bike_id = BikeID
		}
	),
	io:format("[mnesia_manager] delete_by_username => User ~p removed from the chat ~p~n", [Username, BikeID]),
	delete_by_username(T).

% Get the process ids of the maintainer
get_bike_pids(BikeID) when is_integer(BikeID) ->
	Fun = fun() ->
		Maintainer = #requests{user_pid='$1', username = '$2', bike_id = '$3'},
		Guard = {'==', '$3', BikeID},
		mnesia:select(requests, [{Maintainer, [Guard], ['$1']}])
	end,
	
	{atomic, Result} = mnesia:transaction(Fun),
	io:format("[mnesia_manager] get_bike_pids => ~p~n", [Result]),
	Result.
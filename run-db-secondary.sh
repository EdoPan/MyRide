cd ../rqlite-v8.23.0-linux-amd64
./rqlited -node-id 2 -http-addr 10.2.1.122:4001 -raft-addr 10.2.1.122:4002 -join 10.2.1.121:4002 data/
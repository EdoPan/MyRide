# CREATE

curl -XPOST '127.0.0.1:4001/db/execute?pretty&timings' -H "Content-Type: application/json" -d '[
    "CREATE TABLE IF NOT EXISTS  users (username text not null primary key, password text, isMaintainer boolean)"
]'

curl -XPOST '127.0.0.1:4001/db/execute?pretty&timings' -H "Content-Type: application/json" -d '[
    "CREATE TABLE IF NOT EXISTS rides (id INTEGER PRIMARY KEY autoincrement, startTime text, endTime text, bikeID INTEGER, username text)"
]'

curl -XPOST '127.0.0.1:4001/db/execute?pretty&timings' -H "Content-Type: application/json" -d '[
    "CREATE TABLE IF NOT EXISTS stations (id INTEGER PRIMARY KEY autoincrement, address text)"
]'

curl -XPOST '127.0.0.1:4001/db/execute?pretty&timings' -H "Content-Type: application/json" -d '[
    "CREATE TABLE IF NOT EXISTS bikes (id INTEGER PRIMARY KEY autoincrement, type text, price double, stationID INTEGER)"
]'

# USER

curl -XPOST '127.0.0.1:4001/db/execute?pretty&timings' -H "Content-Type: application/json" -d '[
    "INSERT INTO users(username, password, isMaintainer) VALUES (\"maintainer\", \"maintainer\", true)"
]'

# STATIONS

curl -XPOST '127.0.0.1:4001/db/execute?pretty&timings' -H "Content-Type: application/json" -d '[
    "INSERT INTO stations(address) VALUES (\"Via Diotisalvi 2\")"
]'

curl -XPOST '127.0.0.1:4001/db/execute?pretty&timings' -H "Content-Type: application/json" -d '[
    "INSERT INTO stations(address) VALUES (\"Largo Bruno Pontecorvo 3\")"
]'

curl -XPOST '127.0.0.1:4001/db/execute?pretty&timings' -H "Content-Type: application/json" -d '[
    "INSERT INTO stations(address) VALUES (\"Corso Italia 60\")"
]'

curl -XPOST '127.0.0.1:4001/db/execute?pretty&timings' -H "Content-Type: application/json" -d '[
    "INSERT INTO stations(address) VALUES (\"Via di Gello 138\")"
]'

curl -XPOST '127.0.0.1:4001/db/execute?pretty&timings' -H "Content-Type: application/json" -d '[
    "INSERT INTO stations(address) VALUES (\"Piazza Martiri della Libertà 33\")"
]'

curl -XPOST '127.0.0.1:4001/db/execute?pretty&timings' -H "Content-Type: application/json" -d '[
    "INSERT INTO stations(address) VALUES (\"Via S. Marco 15\")"
]'
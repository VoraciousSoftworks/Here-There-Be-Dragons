String Messages
===============
 - Formatted like: 02 {UTF-8 String} 04
 
Binary Messages
===============
 - Formatted like: 01 {data} 04 
 
Authentication
==============
 1. Server sends "Hello!" (with the header and tail bytes like any other message)
 2. Client sends either R: or L: to signify registration and login respectively "{username}:{password}" (: is disallowed) ex: "L:admin:pass"
   - At some point we should find a way encrypt this like https or something
 3. Server sends back "RS:{sessionID}" for registration success, "LS:{sessionID}" for login success, "LRE:{error message}" for an error in either process.
 
New Game
========
 1. Client sends "CR:{sessionId}:{playerToChallege}" which stands for Challenge Request
 2. Server sends back "CRS:{gameID}:{isPlayer1}" or "CRE:{ErrorMessage}" for CR Success and CR Error
 3. goto Existing Game step 3
 
Existing Game
=============
 1. Client tells server it is opening gameId with "PG:{gameId}:{sessionId}"
 2. Server sends back "PGS:{gameState}" or "PGE:{message}" errors might happen if client tries to play a game it isn't in
 3. If it's client's turn, make it and then send it with 07 {turnData}, server sends back "TS" or "TE:{message}" errors might happen if it's not the client's turn or tries to play a turn on a game it doesn't own
 4. If the other player has made a turn, server sends back 07 {turnData} (with a sessionId full of "0" in the string)
 5. After it is finished simulating, the server sends the client "PGS:{gameState}" the client finishes simulating if it isn't and replaces its gamestate with the official one
 6. goto 4 
 
Statistics
==========
 1. Client requests stats type with "PS:{type_num}:{session_id}". PS stands for Player Statistics
 2. Server sends back "PS:{type_num}:{stats_data}" each stats type may send a different kind of data. If it requires multiple fields separate them with colons.

Game list
=========
 1. Client requests list with "GL:{sessionId}" which stands for Game List
 2. Server sends back either "GLE:{message}" on error or "GLS:{numGameInfos}:;{GameInfoString1};{GameInfoString2};..."
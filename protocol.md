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
 
Statistics
==========
 1. Client requests stats type with "PS:{type_num}:{session_id}". PS stands for Player Statistics
 2. Server sends back "PS:{type_num}:{stats_data}" each stats type may send a different kind of data. If it requires multiple fields separate them with colons.

Game list
=========
 1. Client requests list with "GL:{sessionId}" which stands for Game List
 2. Server sends back either "GLE:{message}" on error or "GLS:{numGameInfos}:;{GameInfoString1};{GameInfoString2};..."
 
Challenge Request
=================
 1. Client sends "CR:{sessionId}:{playerToChallege}" which stands for Challenge Request
 2. Server sends back "CRS:{gameID}:{isPlayer1}" or "CRE:{ErrorMessage}" for CR Success and CR Error
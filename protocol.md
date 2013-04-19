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
 3. Server sends back "RS:{sessionID}" for registration success, "LS:{sessionID}" for login success, "E:{error message}" for an error in either process.
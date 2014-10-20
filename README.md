fm-api
======

/user 
  - return all the users.
  
/user/{id} 
  - return user with matching id.
  
/user/login
  - requires json file containing username and password.

/user/logout
  - requires a username(String)
  
/user/create
  - requires a User class (userName, password, type, firstName, lastName) **Case sensitive

/user/edit
  - requires a User class. **userName could not be edited.

/user/delete
   - requires a username(String)

fm-api
======

*Get All the users
/user 
  - return all the users.
  
*Get a user using id
/user/{id} 
  - return user with matching id.
  
*Login  
/user/login
  - requires json file containing username and password.
  
*Logout
/user/logout
  - requires a username(String)
  
*Create user
/user/create
  - requires a User class (userName, password, type, firstName, lastName) **Case sensitive

*Edit user
/user/edit
  - requires a User class. **userName could not be edited.

*Remove user
/user/delete
   - requires a username(String)

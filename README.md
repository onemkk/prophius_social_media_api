# <h2 align="center">Social media Api Project</h3>

<div align="center">


</div>

---


## 📝 Table of Contents
- [Getting Started](#getting_started)
- [Usage](#usage)
- [API Documentation](#api_docs)
- [Built Using](#built_using)
- [Authors](#authors)

## 🏁 Getting Started <a name = "getting_started"></a>
### Prerequisites  
To successfully use this project, you'll need the following installed on your machine:
- **JDK >= v17.** That is because this project was developed using Spring Boot 3, which requires Java 17
as a minimum version.
- **Apache Maven**
- **Docker**

## 🎈 Usage <a name="usage"></a>
To use this project:
1. Fork the repository

2. Clone the repository using the following command:
   ```
   git clone https://github.com/<your-git-username>/spring-security-jwt.git
   ```

3. A docker compose file is attached to this project to help you easily set up a development database. To do this,
   run the following command in your terminal or command prompt
   ```
   docker compose up -d
   ```
   This command will spin-up the following container running in a detached mode: 
   - A postgres database running on `localhost:5432`, and 

4. Using any dbeaver or pgAdmin interface for your `psql`, create a database called `prophius`. 

5 Lastly, run the following commands to start the application:
   ```
   mvn clean install 
   mvn spring-boot:run
   ```
   Liquibase will run the migration files within the `resources/db` directory, upon startup, to set up the database.



## 📄 API Documentation <a name="api_docs"></a>
This project comes with the following APIs:
1. `POST '/api/v1/users'`
   - Registers a new user.
   - Body: A JSON containing the details of the user as shown below:

   ```json
   {
       
        "userName": "john",
        "email": "john@prophius.com",
        "password": "123456",
        "type": "ADMIN"
   }
   ```
   - Returns: A JSON of the registered user's details.

   ```json
   {
      "status": "Successful",
      "message": "User registered successfully",
      "data": {
         "id": 1,
         "username": "john.doe@gmail.com",
         "verified": false,
         "roles": [
            {
               "id": 1,
               "name": "ROLE_ADMIN",
               "permissions": [
                  "users:read"
               ]
            }
         ]
      }
   }
   ```

2. `POST '/api/v1/auth/login'`

   - Authenticates a user
   - Body: A JSON of the user's username and password

   ```json
   {
       "username": "john",
       "password": "password"
   }
   ```
   - Returns: A JSON of access token, token type, expires in (seconds), and a refresh token.

   ```json
   {
       "accessToken": "accessToken",
       "tokenType": "bearer",
       "expiresIn": 1800,
       "refreshToken": "refreshToken"
   }
   ```

3. `POST '/api/v1/auth/refresh'`

   - Refreshes the access token upon expiration
   - Body: A JSON of the refresh token

   ```json
   {
      "refreshToken": "refreshToken"
   }
   ```
   - Returns: A JSON of the new access token, token type, expires in (seconds), and the refresh token that was passed in.

   ```json
   {
       "accessToken": "accessToken",
       "tokenType": "bearer",
       "expiresIn": 1800,
       "refreshToken": "refreshToken"
   }
   ```

4. `POST '/api/v1/auth/logout'` - `Protected`

   - Invalidates a user's active tokens

   - Returns: A JSON of the following response 

      ```json
      {
         "status": "Successful",
         "message": "User logged out successfully",
         "data": null
      }
      ```
     
5. `GET '/api/v1/users'` - `Protected`
   - Fetches all users (must have the role: role_admin)

   - Returns: A JSON of the registered user's details.

   ```json
   {
      "status": "Successful",
      "message": null,
      "data": {
         "content": [
              {
                  "id": 1,
                  "firstName": "John",
                  "lastName": "Doe",
                  "phoneNumber": "+2348123456789",
                  "username": "john.doe@gmail.com",
                  "verified": false,
                  "roles": [
                      { 
                          "id": 1,
                          "name": "ROLE_ADMIN",
                          "permissions": [
                              "users:read"
                          ]
                      }
                  ]
              }
         ]
      }
   }
   ```

6 `POST '/api/v1/users/getconnections/{userId}` - 
   - Creates a connection between two friends or users or customers

   - Returns: A JSON of the registered user's details.

   ```json
   {
   "status": "Successful",
   "message": null,
   "data": {
      "user": {
         "createdDate": "2024-01-18T08:02:57.128642Z",
         "id": 1,
         "email": "makera@prophius.com",
         "username": "makera",
         "accountNonExpired": true,
         "accountNonLocked": true,
         "credentialsNonExpired": true,
         "enabled": true,
         "verified": true,
         "roles": [
            {
               "id": 1,
               "name": "role_admin",
               "description": "Role for users that carry out administrative functions on the application",
               "permissions": [
                  {
                     "id": 1,
                     "name": "users:read",
                     "description": "Permission to fetch all users",
                     "requiresVerification": true
                  }
               ]
            }
         ],
         "likedPosts": []
      },
      "user2": {
         "createdDate": "2024-01-18T08:04:35.490590Z",
         "id": 2,
         "email": "bosom@prophius.com",
         "username": "bosom",
         "accountNonExpired": true,
         "accountNonLocked": true,
         "credentialsNonExpired": true,
         "enabled": true,
         "verified": false,
         "roles": [
            {
               "id": 2,
               "name": "role_customer",
               "description": "Role for users that carry out regular functions on the application",
               "permissions": []
            }
         ],
         "likedPosts": []
      },
      "status": true
   }
}
   ```
7`POST '/api/v1/posts/createpost'`

   - creates a post
   - Body: A JSON of the id of the user posting and the post content

   ```json
   {
   "postContent": "hello 3",
   "user_id": 1
}
   ```
   - Returns: A JSON of the id of the post and the message

   ```json
   {
   "status": "Successful",
   "message": "created post successfully",
   "data": 2
}
   ```

8 `GET 'api/v1/posts/getallposts'`

- Gets all posts

 
- Returns: All posts paginated

   ```json
   {
    "status": "Successful",
    "message": null,
    "data": {
        "content": [
            {
                "postContent": "hello 2",
                "user_id": 1
            },
            {
                "postContent": "hello 3",
                "user_id": 1
            }
        ],
        "page": {
            "first": true,
            "last": true,
            "size": 20,
            "totalElements": 2,
            "totalPages": 1,
            "number": 0
        }
    }
  }
   ```
 9 `POST 'api/v1/posts/{postId}'`

   - edits a post
   - Body: A JSON of the id of the user posting and the post content

   ```json
   {
   "postContent": "yess edited",
   "user_id": 1
   }
   ```
   - Returns: A JSON of the id of the post and the message

   ```json
   {
    "status": "Successful",
    "message": "Post edited successfully",
    "data": {
        "postContent": "yess edited",
        "user_id": 1
    }
   }
   ```
10  `POST 'api/v1/posts/getpostsbyconnections/{userid}'`

- gets all posts of friends
- Returns: A JSON of the id of the post and the message

   ```json
   {
    "status": "Successful",
    "message": null,
    "data": [
        {
            "postContent": "hello 3",
            "user_id": 1
        },
        {
            "postContent": "yess edited",
            "user_id": 1
        }
    ]
   }
   ```
  10  `PUT 'api/v1/posts/{postId}/like/{userId}'`

- likes the posts of someone
- Returns: A JSON of the id of the post and the message

   ```json
   {
    "status": "Successful",
    "message": "Post liked count : 1",
    "data": "yess edited"
   }
   ```
  11  `POST 'api/v1/comments/createcomment/{userId}/{postId}'`

- creates a comment on a post of someone
   ```json
   {
    "comment_body": "my comment"
   }
   ```
- Returns: A JSON of the id of the comment

   ```json
   {
    "status": "Successful",
    "message": null,
    "data": 1
   }
   ```
12  `GET 'api/v1/comments/getcommentsbypost/{postID}'`

- Gets all comments


- Returns: All comments of a post

   ```json
   {
    "status": "Successful",
    "message": "fetched comments of post: 1",
    "data": [
        {
            "comment_body": "my comment"
        }
    ]
}
   ```
13  `DELETE 'api/v1/comments/deletecomment/{commentId}'`

- deletes comments with Id: commentId

- Returns: status of deleted comment of a post

   ```json
  {
    "status": "Successful",
    "message": null,
    "data": {
        "obj": {
            "comment_body": "my comment"
        },
        "deleted": true
    }
   }
   ```

   

## ⛏️ Built Using <a name = "built_using"></a>
- [Spring Boot v3.1.2](https://spring.io/projects/spring-boot) - Spring Boot 3
- [PostgreSQL](https://www.postgresql.org/) - PostgreSQL Database

## ✍️ Authors <a name = "authors"></a>
- [@makera-apiri](https://github.com/onemkk)
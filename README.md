Local run

Prerequisites:
- Docker installed locally

Steps:
1. Clone the repo
2. From the repo root package run command (this will create two containers: DB and Spring app locally):
   ```docker-compose up -d```
3. After the containers is run, it will be available on 8080 port locally.

## API:
 Flyway automatically create two role: `ROLE_USER`, `ROLE_ADMIN`, and one person: email=`admin@gmail.com`, password=`qwerty`.

### POST localhost:8080/api/v1/auth/sign-up

Creates a new person.

##### Example Input:
```
{
    "email": "test@gmail.com",
    "password": "qwerty"
}
```


### POST localhost:8080/api/v1/auth/sign-in

Get JWT Token based on user credentials.

##### Example Input:
```
{
    "email": "test@gmail.com",
    "password": "qwerty"
}
```

##### Example Response:
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTY3ODM0MTY1MywiaWF0IjoxNjc4Mjk4NDUzLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dfQ.XpyfDcfx9oXJniJm4c6Yh7TrOJxp_fQmAOxnS4FMAmQ",
    "type": "Bearer",
    "id": 2,
    "email": "test@gmail.com",
    "roles": [
        "ROLE_USER"
    ]
}
```

### GET localhost:8080/api/v1/persons

##### Example Input:
```Bearer: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTY3ODM0MTY1MywiaWF0IjoxNjc4Mjk4NDUzLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dfQ.XpyfDcfx9oXJniJm4c6Yh7TrOJxp_fQmAOxnS4FMAmQ```

##### Example Response:
```
{
    "id": 2,
    "email": "test@gmail.com",
    "roles": [
        "ROLE_USER"
    ]
}
```

### GET localhost:8080/api/v1/panels/user

Request to secure API for `ROLE_USER`, `ROLE_ADMIN`.

##### Example Input:
```Bearer: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTY3ODM0MTY1MywiaWF0IjoxNjc4Mjk4NDUzLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dfQ.XpyfDcfx9oXJniJm4c6Yh7TrOJxp_fQmAOxnS4FMAmQ```

##### Example Response:
```
hello user!
```

### GET localhost:8080/api/v1/panels/admin

Request to secure API for `ROLE_ADMIN`.

##### Example Input:
```Bearer: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJleHAiOjE2NzgzNDIxMjAsImlhdCI6MTY3ODI5ODkyMCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.DTd8eXQLoJquihXaGS287INXzKvIKdtn8oSUgBzFwkI```

##### Example Response:
```
hello admin!
```

### PUT localhost:8080/api/v1/admin/roles/add

Request to secure API for `ROLE_ADMIN`. Add the specified role for the specified email.

##### Example Input:
```
Bearer: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJleHAiOjE2NzgzNDIxMjAsImlhdCI6MTY3ODI5ODkyMCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.DTd8eXQLoJquihXaGS287INXzKvIKdtn8oSUgBzFwkI
{
    "email": "test@gmail.com",
    "roles": ["ROLE_ADMIN"]
}
```

##### Example Response:
```
{
    "id": 2,
    "email": "test@gmail.com",
    "roles": [
        "ROLE_USER",
        "ROLE_ADMIN"
    ]
}
```
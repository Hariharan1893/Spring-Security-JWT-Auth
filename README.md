# Spring Boot JWT Authentication & Role-Based Authorization Demo

This is a sample Spring Boot project demonstrating JWT (JSON Web Token) based authentication and **role-based authorization** using Spring Security. The project implements:

- User signup with BCrypt password encoding
- JWT token generation on login
- Stateless authentication with JWT token in the `Authorization` header
- Role-based access control (`USER` and `ADMIN`)
- Method-level security with `@PreAuthorize`
- JWT token validation via a custom filter
- Basic CORS support (can be customized)
- Stateless session management

---

## Table of Contents

- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Key Features](#key-features)
- [Security Workflow](#security-workflow)
- [API Endpoints](#api-endpoints)
- [Role-based Authorization](#role-based-authorization)
- [Configuration Details](#configuration-details)
- [How to Run](#how-to-run)
- [Testing](#testing)
- [Future Improvements](#future-improvements)

---

## Getting Started

### Prerequisites

- Java 17+ (compatible with Spring Boot 3.x)
- Spring Security 6
- Maven or Gradle
- Postman or any API client for testing

---

## Project Structure

```

src/main/java/com/auth/democode/
├── config/
│   └── SecurityConfig.java       # Security configuration and JWT filter setup
├── controller/
│   ├── AuthController.java       # Authentication endpoint
│   └── HomeController.java       # Public and protected APIs with
│   └── SignUpController.java     # Public signup api
├── filter/
│   └── JWTAuthFilter.java        # JWT validation filter
├── model/
│   ├── AuthRequest.java          # Login request payload
│   └── User.java                 # User entity implementing UserDetails
├── repository/
│   └── UserRepo.java             # JPA repository for User entity
├── service/
│   ├── CustomUserDetailService.java  # Loads user details for authentication
│   └── SignUpService.java             # User signup service with password encoding
├── utils/
│   └── JWTUtil.java              # JWT token generation and validation utility
└── SpringAuthenticationApplication.java # Main Spring Boot application class
```

---

## Key Features

- **JWT-based authentication:** Stateless token generation and validation.
- **Password encoding:** BCrypt hashing for storing user passwords securely.
- **Role-based authorization:** Use of `@PreAuthorize` to restrict access by user roles.
- **SecurityFilterChain:** Custom Spring Security configuration with stateless session.
- **Custom JWT filter:** Validates JWT token in every request before reaching protected endpoints.
- **Sign up new users:** `/signup` endpoint to register with username, password, and role.

## Security Workflow

1. **User Signup:**

   - Client calls `/signup` with username, password, and role (`USER` or `ADMIN`).
   - Password is hashed and user is stored in DB.

2. **Authentication:**

   - Client sends credentials to `/authenticate`.
   - Server authenticates credentials via `AuthenticationManager`.
   - On success, server generates a JWT token containing username and role info.
   - JWT token is returned to client.

3. **Request with JWT:**
   - Client sends JWT token in `Authorization` header as `Bearer <token>`.
   - `JWTAuthFilter` intercepts requests, validates token, extracts user details.
   - If valid, Spring Security sets authentication context.
   - Protected endpoints enforce role-based access via annotations or config.

---

## API Endpoints

| HTTP Method | Endpoint        | Access        | Description                     |
| ----------- | --------------- | ------------- | ------------------------------- |
| POST        | `/signup`       | Public        | Register new user               |
| POST        | `/authenticate` | Public        | Authenticate and get JWT token  |
| GET         | `/`             | Public        | Public welcome message          |
| GET         | `/afterauthall` | USER or ADMIN | Authenticated users (all roles) |
| GET         | `/user`         | USER only     | USER role access only           |
| GET         | `/admin`        | ADMIN only    | ADMIN role access only          |

---

## Role-based Authorization

- Roles used are **`USER`** and **`ADMIN`** (without prefix `ROLE_` in DB).
- Your `User` entity prepends `"ROLE_"` prefix internally via `getAuthorities()`.
- Method-level security enabled via `@EnableMethodSecurity`.
- Example usage:
  ```java
  @PreAuthorize("hasRole('ADMIN')")
  public String adminOnlyEndpoint() { ... }
  ```

## Configuration Details

- **SecurityConfig.java**

  - Disables CSRF (since JWT is stateless).
  - Sets session management to stateless.
  - Configures public vs protected endpoints.
  - Registers JWT filter before Spring Security’s username/password filter.
  - Enables method-level security with `@EnableMethodSecurity`.
  - Defines beans for `AuthenticationManager`, `UserDetailsService`, and `PasswordEncoder`.

- **JWTUtil.java**

  - Generates JWT tokens with HS256 signature.
  - Extracts username from token.
  - Validates token expiration and user match.

- **JWTAuthFilter.java**

  - Intercepts all requests.
  - Extracts token from Authorization header.
  - Validates token and sets authentication context if valid.

---

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/Hariharan1893/Spring-Security-JWT-Auth.git
   cd Spring-Security-JWT-Auth
   ```

2. Build the project:

   ```bash
   ./mvnw clean install
   ```

3. Run the Spring Boot application:

   ```bash
   ./mvnw spring-boot:run
   ```

4. Test the endpoints using Postman or curl.

---

## Testing the API

### Sign Up

```bash
POST /signup
Content-Type: application/json

{
  "username": "john",
  "password": "password123",
  "role": "USER"
}
```

### Authenticate

```bash
POST /authenticate
Content-Type: application/json

{
  "username": "john",
  "password": "password123"
}
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Access Protected Endpoint

```bash
GET /user
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## Future Improvements

- Implement **Refresh Token** mechanism for extended sessions.
- Add **logout** support (e.g., token blacklisting).
- Integrate with **OAuth2** providers.
- Add **unit and integration tests**.
- Support **role hierarchy**.
- Enhance **CORS** and frontend integration.

---

## License

This project is open source under the MIT License.

---

## Contact

For any questions or suggestions, please open an issue or contact me at `hariramesh1893@gmail.com`.

---

Happy coding! 🚀

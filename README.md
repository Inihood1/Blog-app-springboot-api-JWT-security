# CRUD Blog API with Spring Security in Spring Boot

Welcome to the CRUD Blog API with Spring Security in Spring Boot! This RESTful API allows you to manage blog posts, categories, users, comments, and more. Whether you are building a blog platform or just exploring Spring Boot and Spring Security, this project provides a comprehensive example.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Authentication](#authentication)
- [Endpoints](#endpoints)
  - [Blog Posts](#blog-posts)
  - [Categories](#categories)
  - [Users](#users)
  - [Comments](#comments)
- [Pagination and Sorting](#pagination-and-sorting)
- [Error Handling](#error-handling)
- [Sample Requests](#sample-requests)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

Before you start using this API, make sure you have the following:

- Java Development Kit (JDK) 11 or higher
- Apache Maven
- MySQL database
- Git (optional, for cloning the repository)

## Getting Started

1. Clone this repository (if you haven't already):

   ```bash
   git clone https://github.com/Inihood1/Blog-app-springboot-api-JWT-security.git
   ```

2. Configure your MySQL database settings in `src/main/resources/application.properties`.

3. Build and run the application:

   ```bash
   mvn spring-boot:run
   ```

   The API will be available at `http://localhost:9090`.

## Authentication

This API uses Spring Security for authentication. To access certain endpoints, you must obtain a JWT (JSON Web Token) by authenticating with your credentials. The following endpoints are secured:

- `/app/auth/register`: Register a new user to recieve JWT.
- `/app/auth/login`: Sign in and receive a JWT.

Include the obtained JWT in the `Authorization` header of your requests:

```
Authorization: Bearer YOUR_JWT_TOKEN
```

## Endpoints

### Blog Posts

- `GET /api/posts`: Get all blog posts.
- `GET /api/posts/{id}`: Get a specific blog post by ID.
- `POST /api/posts`: Add a new blog post.
- `PUT /api/posts/{id}`: Edit an existing blog post.
- `DELETE /api/posts/{id}`: Delete a blog post by ID.
- `GET /api/posts/category/{categoryId}`: Get all blog posts in a specific category.

### Categories

- `GET /api/categories`: Get all categories.
- `GET /api/categories/{id}`: Get a specific category by ID.
- `POST /api/categories`: Add a new category (Admin).
- `DELETE /api/categories/{id}`: Delete a category by ID (Admin).

### Users

- `GET /api/users`: Get all users.
- `GET /api/users/{id}`: Get a specific user by ID.
- `POST /api/users`: Add a new user (Admin).
- `DELETE /api/users/{id}`: Delete a user by ID (Admin).
- `PUT /api/users/{id}`: Update user information.

### Comments

- `GET /api/posts/{postId}/comments`: Get all comments for a specific blog post.
- `POST /api/posts/{postId}/comments`: Add a new comment.
- `GET /api/posts/{postId}/comments/{commentId}`: Get a specific comment by ID.

## Pagination and Sorting

For endpoints that return multiple items (e.g., blog posts, comments, users), you can use the `page` and `size` query parameters to paginate the results. Additionally, you can use the `sort` query parameter to specify sorting criteria.

Example:

- `/api/posts?page=1&size=10&sort=title,asc` will return the first page of 10 blog posts sorted by title in ascending order.

## Error Handling

The API provides detailed error responses in JSON format. Please refer to the error message and status code to identify the issue.

## Sample Requests

You can find sample requests in the [Postman](https://www.postman.com) collection included in the `postman` directory. Import the collection to Postman for easy testing.

## Contributing

Contributions are welcome! If you have suggestions, bug reports, or feature requests, please open an issue or create a pull request. Make sure to follow the [code of conduct](CODE_OF_CONDUCT.md).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

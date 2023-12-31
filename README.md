
Blog Project
Overview
This project is a blog application developed using the Spring framework, incorporating features such as Spring Security, JWT token authentication, Swagger API documentation, and deployment on AWS.

Features
Spring Security: The project utilizes Spring Security for handling authentication and authorization. It provides a secure environment for users and protects sensitive endpoints.

JWT Token Authentication: JSON Web Tokens (JWT) are used for secure and stateless authentication. This ensures that users can securely access the application without the need for server-side sessions.

Swagger API Documentation: The project includes Swagger for API documentation. You can explore and test the API endpoints easily using the Swagger UI, which provides a user-friendly interface.

AWS Deployment: The application is deployed on Amazon Web Services (AWS) for scalability and reliability. This allows your blog to be accessible to a wide audience and ensures high availability.

Technologies Used
Java
Spring Boot
Hibernate
Spring Security
JWT
Swagger
AWS (Amazon RDS, AWS Elastic Beanstalk, etc.)

#AWS Postman testing url
http://myblog1.ap-south-1.elasticbeanstalk.com/api/auth/signup
Body:
{
    "email":"vicky@gmail.com",
    "name":"vicky",
    "password":"vICKYVICKY@1",
    "username":"vicky@1"
}

#Swagger api Documentation
Postman url: http://localhost:8080/v3/api-docs
Web:http://localhost:8080/swagger-ui/index.html

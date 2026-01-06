# Java Application Template

A comprehensive template for the development stack used in building modern Java applications with Spring Boot 3, PostgreSQL, and containerized deployment.

## Technology Choices

This template comes from actually evaluating each piece of the tech stack. We do not chase the latest versions, we picked the most solid, reliable choices that are still current with modern Java development.

## Architecture Overview

This template demonstrates a layered architecture following Spring Boot 3 best practices with clear separation of concerns and modern development practices.

### Core Framework Stack

- **Spring Boot 3.2.1** - Modern Java framework with Jakarta EE support
- **Java 21** - Latest LTS version with modern language features  
- **Jakarta EE** - Enterprise Java specification (replacing Java EE)
- **Spring Data JPA** - Data access layer with Hibernate ORM
- **Spring MVC** - Web layer for REST APIs

### Database Strategy

- **PostgreSQL** - Production database (configured via Docker Compose)
- **H2 Database** - In-memory database for testing (lightweight, fast test execution)
- **HikariCP** - High-performance connection pooling

### Build & Dependency Management

- **Maven** - Build automation and dependency management
- **Maven Wrapper** - Ensures consistent Maven version across environments

### Containerization & Deployment

- **Docker** - Application containerization
- **Docker Compose** - Multi-service orchestration (app + PostgreSQL)
- **Dockerfile** - Defines the application container image

### CI/CD Pipeline

The **GitHub Actions** workflow provides:
- Automated testing on every push/PR to main branch
- Java 21 with Temurin distribution setup
- Maven caching for faster builds
- Docker image building
- Runs on Ubuntu latest

## 🚀 What's Next?

This template covers the "development stack", everything you need to build and run your Java application locally or in basic containerized environments.

For production deployments, there will be another "deployment stack" template that provides AWS infrastructure as code using Terraform. It includes all the AWS services and configurations you'll need to deploy this Java application in a scalable, production-ready environment.


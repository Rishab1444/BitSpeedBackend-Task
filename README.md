# BitSpeedBackend-Task
## Tech Stack

- **Backend:** Java, Spring Boot
- **Database:** PostgreSQL
- **Other Technologies:** Maven, JPA/Hibernate, Lombok

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them:

- Java JDK 11
- Maven 
- PostgreSQL

### Installing

A step-by-step series of examples that tell you how to get a development environment running.

#### Setting up the Database

1. Install PostgreSQL and create a database for the project.
2. Configure the `src/main/resources/application.properties` file with your database details:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/yourDatabase
spring.datasource.username=yourUsername
spring.datasource.password=yourPassword
spring.jpa.hibernate.ddl-auto=update


# To do List Web Application

 The To-Do Web App is a task management system which assists users to organize and
keep track of their daily activities. It provides simple interface to create, update, delete and mark tasks as
complete or incomplete.


## Features

- Create, update, delete and view tasks.
- Mark tasks as completed or incomplete
- Well-structured REST APIs for task operations   (CRUD)
- Tasks stored securely in database using JPA/Hibernate
- Efficient mapping between entities and DTOs using MapStruct
- Redis Caching implemented to reduce database load and improve response times
- Rate Limiting to prevent API abuse and ensure fair usage
- Centralized logging for tracking method behaviour and errors
- Performance monitoring to identify bottlenecks and optimize application behavior
- Responsive UI built with React


## Installation and Setup

Setup my-project backend

```bash
  git clone repositoryURL
  cd Full stack to-do-app\To-Do-App-Backend
  configure a SQL database
  configure redis type, host and port in application.properties file
  install dependencies (mvn clean install)
  run the spring boot application
```

Setup my-project frontend

```bash
cd Full stack to-do-app\To-do-app
install dependencies (npm install)
run the vite development server (npm run dev)
```
    
## Tech Stack

**Client:** React, TailwindCSS

**Server:** Java, SpringBoot

**Database:** PostgreSQL


## Screenshot
<img width="1920" height="1069" alt="To do app" src="https://github.com/user-attachments/assets/bdc34319-6bcd-45b8-8e80-24c7c4fbec37" />



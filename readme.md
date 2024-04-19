# Book Archive

The Book Archive is intended for archiving information about book series and books to facilitate tasks such as collecting a library. This document is a simplified user manual for using the application.

## Installation

To set up the development environment, run the following command in a Docker container:

```bash
docker compose -f docker-compose.dev.yml up
```
The database container uses the latest mysql image and can be accessed on port 3306.
The backend utilizes the amazoncorretto:21-alpine-jdk image as its base and runs on port 8080.
The frontend uses the node:21-alpine3.19 image as its base and can be accessed on port 4200.

For the production environment, Docker stack deployment is required along with setting secrets for Docker Swarm. To run the production container, the following secrets need to be specified:
- db_user_password: Database user's password
- api_admin_password: Application's admin password. The backend creates an admin user with this password.
- jwt_secret: A password for JSON Web Token used for token generation. Do note the length requirements.

Additionally, both the frontend and backend require their own images. You can customize their names in the docker-compose.yml file.

## Participation

Don't participate yet, this is still very much a work in progress...

## License

[We don't need no licenses](https://www.youtube.com/watch?v=tdSJjY3lWvE)

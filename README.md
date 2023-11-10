## How to dockerize application:
- Choose the docker profile (uncomment ```spring.profiles.active=docker``` property)
- Build the .jar file of your project version (Gradle in the top right -> build -> bootJar)
- Download [Docker](https://www.docker.com/products/docker-desktop/) and run it
- Open the terminal with the project location
- Run Docker Compose using ```docker-compose up -d --build```
- To stop the application, use ```docker-compose down```

### PS - To check the server logs:
- Type ```docker ps``` to check all the running container
- Type ```docker logs -f <id>```, where id = Container Id of the server (**carpooling_server**)

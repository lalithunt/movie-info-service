# movie-info-service
This is a Movie Information Service which is written in Spring Boot + Kotlin + JPA + H2

Service is url is `http://localhost:8080/info-guide/v1/movies`

Post man collection at : src/main/resources/postman-collection/Movies-Info-Crud.postman_collection.json

Postman Collection has both Happy path and negative request which can be tested.
![](../../../../../Desktop/Screen Shot 2023-02-26 at 5.43.45 PM.png)


Service supports all the below operations.
1. Adding a Movie Information. (Url : `http://localhost:8080/info-guide/v1/movies`)
2. Getting all the Movies Information. (Url : `http://localhost:8080/info-guide/v1/movies`)
3. Getting a single Movie Information. (Url : `http://localhost:8080/info-guide/v1/movies/{id}`)
4. Updating a Movie Information. (Url : `http://localhost:8080/info-guide/v1/movies`)
5. Deleting a Movie Information. (Url : `http://localhost:8080/info-guide/v1/movies/{id}`)

Steps for local setup.
1. Clone or pull from https://github.com/lalithunt/movie-info-service
2. Open in intellj.
3. I have used jdk 17 for this project. 
4. Run mvn clean install -U 
5. Then Go to file MoviesInfoServiceApplication, and right click and run it as java application
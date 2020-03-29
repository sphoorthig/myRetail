# myRetail
### Functionality Implemented
1. Retrieve product and price information by Product Id.
2. Update the price information in the database.
3. Secure API with basic authentication.
4. Swagger2 API documentation.

All the end points are secure with basic security using Spring Security. 

### Technology Stack
1. Spring Projects - Spring Boot, Spring Security, Spring Cloud OpenFeign, Spring Data MongoDB
2. Data Source -  MongoDB Atlas (MongoDB in cloud, using the Database-as-a-service on AWS provided my MongoDB Atlas)
3. Build Tool - Gradle
4. Testing FrameWorks - Junit/Mockito
5. Integration test of application - Postman

### Deployed instance
The application can be tested on deployed instance of heroku.
```https://myretailtarget.herokuapp.com```

### Running the test cases
To run the tests in the terminal run the below
```gradle test``` or
```./gradlew test```
### Testing the application in postman
Download the postman(if you already don't have) and import the collection (<a href="My Retailpostman_collection.json">Click here to download postman collection</a>) it into the postman

To test application using postman on a local instance 
1. Clone the git project https://github.com/sphoorthig/myRetail.git
2. BootRun the spring boot application 
    - If you intend to run on terminal use the command `gradle bootRun`
    - If you want to run as a packaged application
3. Run the myRetail postman collection as a runner from local folder. (Tests included in postman requests)

To test application using postman on a Heroku deployed instance 
1. Run the myRetail postman collection as a runner from Heroku folder. (Tests included in postman requests)
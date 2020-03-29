# myRetail

## Functionality Implemented
1. Retrieve product and price information by Product Id - integrates information from an external source and from mongoDB (if found)
2. Update the price information in the database - updates (if found) in mongoDB 
3. Secure API with basic authentication.
4. Swagger2 API documentation.

All the end points are secure with basic security using Spring Security. 

## Technology Stack
1. Spring Projects - Spring Boot, Spring Security, Spring Cloud OpenFeign, Spring Data MongoDB
2. Data Source -  MongoDB Atlas (MongoDB in cloud, using the Database-as-a-service on AWS provided my MongoDB Atlas)
3. Build Tool - Gradle
4. Testing FrameWorks - Junit/Mockito
5. Integration test of application - Postman


## Building the application and run the running the test cases
Clone the repository ```git clone https://github.com/sphoorthig/myRetail.git```

To build the application locally, run the below command in the terminal
```gradle build``` or
```./gradlew clean build```

To run the tests in the terminal run the below command in the terminal
```gradle test``` or
```./gradlew clean test```

To bootRun the application locally run the below command in the terminal
```gradle bootRun```
The application will start in port 8080

## Deployed instance
The application can be tested on deployed instance of heroku.
```https://myretailtarget.herokuapp.com```

## Swagger documentation
Find the swagger at
https://myretailtarget.herokuapp.com/swagger-ui.html

## Testing the application in postman
1. Download the postman at https://www.postman.com/downloads/
2. Download the collection (<a href="https://github.com/sphoorthig/myRetail/blob/master/PostmanCollection/myRetailPostman.json">Click here to download postman collection</a>)
3. Import the downloaded collection to postman

#### Test application on a local instance 
1. BootRun the spring boot application 
3. Run the myRetail postman collection as a runner from local folder. (Tests included in postman requests)

#### Test application on a local instance 
To test application using postman on a Heroku deployed instance 
1. Run the myRetail postman collection as a runner from Heroku folder. (Tests included in postman requests)
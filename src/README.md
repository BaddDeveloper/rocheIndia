# FizzBuzz Application

This is a simple Spring Boot application that implements the FizzBuzz logic. It exposes two endpoints:

1. `/fizzbuzz`: Returns a FizzBuzz sequence based on provided parameters.
2. `/statistics`: Returns statistics about the FizzBuzz requests.

## Running the Application

1. Clone the repository.
2. Ensure you have Java 17 installed.
3. Navigate to the project directory.
4. Run the application using `./mvnw spring-boot:run` (or `mvnw.cmd` on Windows).

The application will start, and you can access the endpoints at http://localhost:8080.

## API Documentation

Explore the FizzBuzz API using the [API Document](https://docs.google.com/document/d/1Dxtew1p3syh0AOcnaarEALYO1c5ojQzYyPktL7yCwAQ/edit?usp=sharing)

## List of Third-Party Libraries Used

- **Spring Boot Starter Web (Version 3.2.1):**
  - Description: Provides the dependencies and configurations needed for building web applications with Spring MVC.
  - Usage: Enables the development of #RESTful APIs and web endpoints.

- **Spring Boot Starter Test (Version 3.2.1):**
  - Description: Includes testing dependencies for Spring Boot applications.
  - Usage: Facilitates writing and executing unit and integration tests for the application.
  
## Endpoints

### FizzBuzz Endpoint

- **Path:** `/fizzbuzz`
- **Method:** `GET`
- **Parameters:**
  - `int1` (optional): First divisor (default is 3).
  - `int2` (optional): Second divisor (default is 5).
  - `limit` (optional): Upper limit for the sequence (default is 100).
  - `str1` (optional): String to replace multiples of `int1` (default is "Fizz").
  - `str2` (optional): String to replace multiples of `int2` (default is "Buzz").

#### Sample cURL Request

curl -X GET "http://localhost:8080/fizzbuzz?int1=3&int2=5&limit=15&str1=Fizz&str2=Buzz"


#### Sample fizzbuzz Response 
["1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","14","FizzBuzz"]

### Statistics Endpoint
- **Path:** `/statistics`
- **Method:** `GET`

#### Sample cURL Request
curl -X GET "http://localhost:8080/statistics"

#### Sample Statistics Response
{
  "mostUsedRequest": "3-5-15-Fizz-Buzz",
  "mostUsedHits": 3
}

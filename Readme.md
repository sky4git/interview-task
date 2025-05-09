[![Run Kotlin Spring Boot Tests](https://github.com/sky4git/interview-task/actions/workflows/test.yml/badge.svg)](https://github.com/sky4git/interview-task/actions/workflows/test.yml)

## Challenge

URL Shortener API – Code Test

## Task Overview

Build a simple URL Shortener API with the following functionality:
Example
Original URL - https://www.example.com.au/electricity-gas/plans.html
Short URL - http://short.ly/a1B2c3
Requirements (Mandatory)

1. Shorten URL when provided with a long URL
2. Redirect to Original URL when a short URL is requested
   Requirements (Optional)
1. Get Original URL Info when a short URL is passed in

## Constraints

1. Use an in-memory store for simplicity
2. URL codes should be unique, short, and non-sequential
3. Validate the incoming URL format
4. Error handling (404 for not found, 400 for invalid input etc.)
5. Programming Language – Java/Kotlin
6. Framework – Spring Ecosystem (Spring Boot, Spring Data JPA etc)

## Deliverables

- Source code (with README)
- Instructions to run locally
- Test cases

## Build, testing and run

### build

```shell
./gradlew clean build
```

### testing

```shell
./gradlew test
```

### run

```shell
./gradlew bootRun
```

## Endpoints

### 1. Shorten the URL

Endpoint: `/api/shorten`

Curl: ```curl --location 'http://localhost:8080/api/shorten?url=https%3A%2F%2Fbing.com'```
Expected Response:

```json
{
  "shortUrl": "http://short.ly/eN5UlY"
}
```

### 2. Redirect to original url

Endpoint: `/api/redirect/{shortCode}`

Curl: ```curl --location 'http://localhost:8080/api/redirect/eN5UlY'```

Expected Response is HTTP status code 302 redirect to the original URL.

### 3. Get info about the original URL

Endpoint: `/api/info/{shortCode}`

Curl: ```curl --location --request POST 'http://localhost:8080/api/info/eN5UlY'```

Expected Response:

```json
{
  "shortUrl": "http://short.ly/eN5UlY",
  "fullUrl": "https://bing.com",
  "clicks": 2
}
```
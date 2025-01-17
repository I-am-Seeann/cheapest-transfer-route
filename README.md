
# Cheapest Transfer Route

This application calculates the cheapest transfer route using the 0/1 Knapsack algorithm. It provides a REST API for calculating the cheapest route based on the available transfers and maximum weight.

## Table of Contents

- [How to Build and Run the Application](#how-to-build-and-run-the-application)
- [How to Test the Application](#how-to-test-the-application)
- [API Endpoints](#api-endpoints)
- [Example CURL Requests and Responses](#example-curl-requests-and-responses)

---

## How to Build and Run the Application

### Prerequisites
- Java 17 or newer
- Gradle 

### Build the Application

1. Clone the repository to your local machine.
   ```bash
   git clone https://github.com/I-am-Seeann/cheapest-transfer-route.git
2. Navigate to the project directory.
   ```bash
   cd cheapest-transfer-route
3. Build the project using Gradle:
   ```bash
   ./gradlew build

### Run the Application

To run the application locally, use the following command:
   ```bash
   ./gradlew bootRun
   ```
The application will be available at http://localhost:8081.


## How to Test the Application

To run tests, run the following command

```bash
  ./gradlew test
  ```

## API Endpoints

POST /calculate-cheapest-route

This endpoint calculates the cheapest transfer route based on the available transfers and maximum weight.

Request Body:
```json
{
  "maxWeight": 15,
  "availableTransfers": [
    { "weight": 5, "cost": 10 },
    { "weight": 10, "cost": 20 },
    { "weight": 3, "cost": 5 },
    { "weight": 8, "cost": 15 }
  ]
}
```
Response:
```json
{
   "selectedTransfers": [
      { "weight": 5, "cost": 10 },
      { "weight": 10, "cost": 20 }
   ],
   "totalCost": 30,
   "totalWeight": 15
}
```

## Example CURL Requests and Responses

Example 1: Basic Request
Request:
```bash
curl --location 'http://localhost:8081/calculate-cheapest-route' \
--header 'Content-Type: application/json' \
--data '{
    "maxWeight": 15,
    "availableTransfers": [
        {
            "weight": 5,
            "cost": 10
        },
        {
            "weight": 10,
            "cost": 20
        },
        {
            "weight": 3,
            "cost": 5
        },
        {
            "weight": 8,
            "cost": 15
        }
    ]
}'
```



# MyRetail
## Setup
To run and test this application the following resources are needed:
1. MongoDb instance
2. REST client

After verifying MongoDB is installed and running:
1. Configure `src/main/resources/application.properties` to your MongoDB instance
2. Start the application
    1. Run `.\gradlew bootRun` to start the application locally 
    2. Or `.\gradlew bootJar` to build a jar to run manually

## Application Usage

The spring application will run on `http://localhost:8080` by default. Use this as the base URI for testing via the REST client.

The `/products/{id}` endpoint supports both **GET** and **PUT** requests and will respond with a JSON formatted as such.
```
{
	"id": 16696652,
	"name": "Beats Solo 2 Wireless - Black",
	"current_price": {
		"value": 10.12,
		"currency_code": "USD"
	}
}
```

_Note: The application will insert test pricing data for the following example products_

Example Product Ids:
`16696652 13860429 13860428`

To update the pricing information of a product, **PUT** to the same corresponding productId with updated value and currency_code values.

Example: _note the name of the product is not required on **PUT**_
```
{
	"id": 16696652,
	"current_price": {
		"value": 10.25,
		"currency_code": "CAN"
	}
}
```

## Testing

Run `.\gradlew test` to run and verify Repository and Service integration tests.

_Note: MongoDB needs to be running to connect and test_

## Notes



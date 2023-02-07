Intra Bank Payment Transfer System Demo

app uses h2 with preloaded data
it was used n-layer pattern for microservice

for compile use     mvn clean install 
to run service use  mvn spring-boot:run

endpoints:

GET:
localhost:8080/intra/accounts/{id}                  should return details for given id
localhost:8080/intra/accounts/{id}/balance          should return balance for given id
localhost:8080/intra/accounts/{id}/statements/mini  should return collection of statements for given id

POST:
localhost:8080/intra/statements                     should return statement json
json body for test:
        {
        "creditId": 111,
        "debitId": 222,
        "amount": 0.1
        }


collection for postman is included in root folder
swagger url: localhost:8080/intra/swagger-ui/index.html


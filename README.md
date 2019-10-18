# graphql-kotlin-ktor-employee
GraphQL employee example using Ktor

## How to run
Hello Kotlin will run on port 7001. You can run it either in IntelliJ Idea by running the WebService.kt class or using the docker file.
The federation schema can be tested by importing it into Apollo Gateway, see https://github.com/sgohlke/apollo-api-gateway-example.

Use Dockerfile(example):
- Run gradle build in project
- Execute **docker build -t kotlingql .** in project root folder
- Run **docker run --name kotlingql -d -p 7001:7001 kotlingql**

## Endpoints
- / : root endpoint, returns "Hello REST" and a current Datetime
- /employee : Returns all Employee available
- /employee/0 : From 0 to 2, returns specific Employee with given index 
- /graphql : GraphQL post endpoint

### GraphQL endpoint

Send following body in post request to receive an answer from the GraphQL endpoint.

Get the federation SDL:
`{ _service { sdl } }`

Get all employees:
`{
   employees() {
       lastName
       firstName
       age
       id
     }
   }`
   
Get all employees with age 40:
`{
   employees(age:40) {
       lastName
       firstName
       age
       id
     }
   }`
   
Get the firstName of all employees with age 40 and lastName Meier:
   `{
      employees(age:40, lastName:"Meier") {
          firstName
        }
      }`
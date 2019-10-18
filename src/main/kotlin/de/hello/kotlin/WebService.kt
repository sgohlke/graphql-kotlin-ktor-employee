package de.hello.kotlin

import com.expediagroup.graphql.extensions.print
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import graphql.GraphQL
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.time.LocalDateTime
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("de.hello")!!

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, 7002) {
        install(DefaultHeaders)
        install(Compression)
        install(CallLogging)
        install(ContentNegotiation) {
            jackson {
                configure(
                    SerializationFeature.INDENT_OUTPUT, true
                )
                setDefaultPrettyPrinter(
                    DefaultPrettyPrinter().apply {
                        indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                        indentObjectsWith(DefaultIndenter("  ", "\n"))
                    }
                )
                registerModule(JavaTimeModule()) // support java.time.* types
            }
        }

        routing {
            get("/") {
                call.respondText("Hello REST " + LocalDateTime.now(), ContentType.Text.Html)
            }
            get("/employee") { _ -> call.respondText("List of available Employees " + EmployeeStorage.employeeList.toString()) }
            get("/employee/{index}") {
                val employeeIndex = call.parameters["index"]!!.toIntOrNull()
                call.respond(getEmployee(employeeIndex))
            }
            post("/graphql") {
                val query = call.receive(GraphQLRequest::class).query
                logger.info("Query is: $query\n")
                val schema = EmployeeStorage.getEmployeeSchema()
                logger.info("Schema is\n" + schema.print() + "\n")
                /* If _service is null because SDL creation failed, maybe sending the broken sdl from schema.print to Gateway API might provide
                information about validation problems. E.g.
                 call.respond(GraphQLResponse.kt(ServiceSDL(_Service(schema.print(includeDefaultSchemaDefinition = false, includeDirectives = false)))))
                */
                val builder = GraphQL.newGraphQL(schema).build()
                val result = builder.execute(query).toGraphQLResponse()
                call.respond(result)
            }
        }
    }
    server.start(wait = true)
}

fun getEmployee(employeeIndex: Int?): Any {
    if (employeeIndex == null) {
        return Employee(1, "UNKNOWN", "UNKNOWN", "SOMEWHERE", 999)
    } else return EmployeeStorage.employeeList.get(employeeIndex.toInt())
}

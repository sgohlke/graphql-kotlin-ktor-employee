package de.hello.kotlin

import com.expediagroup.graphql.TopLevelObject
import com.expediagroup.graphql.federation.FederatedSchemaGeneratorConfig
import com.expediagroup.graphql.federation.FederatedSchemaGeneratorHooks
import com.expediagroup.graphql.federation.execution.FederatedTypeRegistry
import com.expediagroup.graphql.federation.toFederatedSchema
import graphql.schema.GraphQLSchema

object EmployeeStorage {
    val employeeList: MutableList<Employee> = mutableListOf()

    init {
        employeeList.add(Employee(1, "Mustermann", "Max", "Mustermann GmbH", 40))
        employeeList.add(Employee(2, "Hansen", "Sabine", "Malerei Hansen", 32))
        employeeList.add(Employee(3, "Meier", "Hubertus", "MyAdds.com", 56))
    }

    // Generate the schema
    val config = FederatedSchemaGeneratorConfig(
        supportedPackages = listOf("de.hello"),
        hooks = FederatedSchemaGeneratorHooks(FederatedTypeRegistry(emptyMap()))
    )
    val queries = listOf(TopLevelObject(EmployeeService()))
    val mutations = listOf(TopLevelObject(EmployeeUpdater()))

    fun getEmployeeSchema(): GraphQLSchema {
        return toFederatedSchema(config, queries, mutations)
    }
}

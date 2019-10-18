package de.hello.kotlin

import com.expediagroup.graphql.federation.directives.FieldSet
import com.expediagroup.graphql.federation.directives.KeyDirective

@KeyDirective(fields = FieldSet("id"))
data class Employee(val id: Int, val lastName: String = "UNKNOWN", val firstName: String = "UNKNOWN", val companyAddress: String = "SOMEWHERE", val age: Int = 999)

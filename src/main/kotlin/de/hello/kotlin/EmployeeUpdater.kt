/**
 *    @author Stefan Gohlke
 */
package de.hello.kotlin

class EmployeeUpdater {
    fun saveEmployee(id: Int?, lastName: String?, firstName: String?, companyAddress: String?, age: Int?): Employee {
        return Employee(
            id ?: 1, lastName ?: "UNKNOWN", firstName ?: "UNKNOWN", companyAddress ?: "SOMEWHERE",
            age ?: 999
        )
    }
}

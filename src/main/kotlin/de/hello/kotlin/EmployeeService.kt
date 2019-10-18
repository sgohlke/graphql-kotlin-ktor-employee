/**
 *    @author Stefan Gohlke
 */
package de.hello.kotlin

class EmployeeService {
    fun employee(id: Int?, lastName: String?, firstName: String?, companyAddress: String?, age: Int?): List<Employee> =
        EmployeeStorage.employeeList.filter { employee: Employee ->
            (lastName == null || lastName.equals(employee.lastName)) &&
                (firstName == null || firstName.equals(employee.firstName)) &&
                (companyAddress == null || firstName.equals(employee.companyAddress)) &&
                (age == null || age == employee.age) &&
                (id == null || id == employee.id)
        }
}

package Svider.Zadanie2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import Svider.Zadanie2.entity.Employee;
import Svider.Zadanie2.service.EmployeeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeService.findById(id);

        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee id not found - " + id);
        }
        return employee;
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        employee.setId(null);
        Employee savedEmployee = employeeService.save(employee);
        return savedEmployee;
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {

        Employee existingEmployee = employeeService.findById(id);
        if (existingEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee id not found - " + id);
        }

        employee.setId(id);
        Employee updatedEmployee = employeeService.save(employee);
        return updatedEmployee;
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, String> deleteEmployee(@PathVariable Integer id) {

        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee id not found - " + id);
        }

        employeeService.deleteById(id);

        return Map.of("message", "Deleted employee id - " + id);
    }

    @GetMapping("/employees/search")
    public List<Employee> searchEmployees(@RequestParam String firstName, @RequestParam String lastName) {
        return employeeService.findByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/employees/sorted")
    public List<Employee> getSortedEmployees() {
        return employeeService.getSortedEmployees();
    }
}
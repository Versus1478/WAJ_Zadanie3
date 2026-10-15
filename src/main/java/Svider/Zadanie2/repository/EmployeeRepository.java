package Svider.Zadanie2.repository;

import Svider.Zadanie2.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    List<Employee> findAllByOrderByLastNameAsc();
}
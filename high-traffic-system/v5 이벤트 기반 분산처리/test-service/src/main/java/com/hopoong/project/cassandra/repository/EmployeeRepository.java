package com.hopoong.project.cassandra.repository;

import com.hopoong.project.cassandra.entity.Employee;
import com.hopoong.project.cassandra.entity.EmployeePrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CassandraRepository<Employee, EmployeePrimaryKey> {

    @Query("SELECT * FROM employee WHERE location = ?0 AND department = ?1")
    List<Employee> findByLocationAndDepartment(String location, String department);
}

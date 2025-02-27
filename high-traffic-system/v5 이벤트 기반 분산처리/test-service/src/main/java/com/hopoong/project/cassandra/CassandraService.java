package com.hopoong.project.cassandra;

import com.hopoong.project.cassandra.entity.Employee;
import com.hopoong.project.cassandra.entity.EmployeePrimaryKey;
import com.hopoong.project.cassandra.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CassandraService {

    private final EmployeeRepository employeeRepository;

    public void casTest() {
        Employee employee1 = new Employee(new EmployeePrimaryKey("seoul", "business", "key"), "010-1111-2222");
        employeeRepository.save(employee1);

        Employee employee2 = new Employee(new EmployeePrimaryKey("seoul", "business", "joy"), "010-3333-4444");
        employeeRepository.save(employee2);

        List<Employee> byKeyLocationAndKeyDepartment = employeeRepository.findByLocationAndDepartment("seoul", "business");
        byKeyLocationAndKeyDepartment.forEach(data -> {
            System.out.println("%s ::: %s".formatted(data.getPhoneNumber(), data.getEmployeePrimaryKey().getName()));
        });
    }

}

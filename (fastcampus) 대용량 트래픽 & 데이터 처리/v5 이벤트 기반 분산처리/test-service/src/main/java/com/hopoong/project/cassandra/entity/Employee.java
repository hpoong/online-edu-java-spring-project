package com.hopoong.project.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

    @PrimaryKey
    private EmployeePrimaryKey employeePrimaryKey;

    @Column
    private String phoneNumber;
}

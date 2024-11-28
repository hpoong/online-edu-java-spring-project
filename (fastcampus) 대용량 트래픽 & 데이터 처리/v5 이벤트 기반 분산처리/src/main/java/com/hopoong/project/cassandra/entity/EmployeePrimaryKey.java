package com.hopoong.project.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePrimaryKey {

    @PrimaryKeyColumn(name = "location", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String location;

    @PrimaryKeyColumn(name = "department", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String department;

    @PrimaryKeyColumn(name = "name", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private String name;
}

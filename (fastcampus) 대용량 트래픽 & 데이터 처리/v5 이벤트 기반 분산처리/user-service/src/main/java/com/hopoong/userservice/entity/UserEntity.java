package com.hopoong.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "user_id")
    private String userId;

    @Column(unique = true, name = "user_name")
    private String userName;

    public UserEntity(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}

package com.david.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "systemuser")
@Data
public class SystemUser {

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String account;
    private String password;
    private String name;

}

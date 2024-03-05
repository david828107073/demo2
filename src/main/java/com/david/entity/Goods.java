package com.david.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "goods")
@Data
public class Goods {
    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "cr_user")
    private SystemUser createUser;
    @Column(name = "cr_datetime")
    private Timestamp createDateTime;
    @ManyToOne
    @JoinColumn(name = "up_user")
    private SystemUser updateUser;
    @Column(name = "up_datetime")
    private Timestamp updateDateTime;
}

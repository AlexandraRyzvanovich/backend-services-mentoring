package com.epam.ryzvanovich.backendservicesmentoring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    private long id;

    @Column(nullable = false)
    private String name;

    private String surname;

    private int age;
}

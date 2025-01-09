package com.backend.fortunetoss.shape;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Shape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shape_id")
    private Long id;


    private String shape;
}

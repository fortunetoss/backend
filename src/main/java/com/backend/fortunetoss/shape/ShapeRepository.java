package com.backend.fortunetoss.shape;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShapeRepository extends JpaRepository<Shape, Long> {
    Optional<Shape> findByShape(String shape);
}

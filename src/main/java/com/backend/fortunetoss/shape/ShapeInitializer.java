package com.backend.fortunetoss.shape;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShapeInitializer {

    private final ShapeRepository shapeRepository;


    @PostConstruct
    public void init() {
        if (shapeRepository.count() == 0) {
//            List<String> shapes = List.of("Circle", "Square", "Triangle", "Rectangle", "Hexagon", "Octagon", "Pentagon", "Ellipse");
            List<String> shapes = List.of("A", "B", "C", "D", "E", "F", "G", "H");
            shapes.forEach(shapeName -> shapeRepository.save(Shape.builder().shape(shapeName).build()));
            System.out.println("Shape 데이터가 성공적으로 초기화되었습니다! 🌟");
        }
    }
}

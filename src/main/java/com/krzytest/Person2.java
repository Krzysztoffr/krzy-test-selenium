package com.krzytest;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Person2 {

    private String name;
    private int age;
    private final String surname;

    public String getName() {
        System.out.println("Jestem w getterze");
        return name;
    }

    @Override
    public String toString() {
        return "Person2{" +
                "age=" + age +
                '}';
    }
}

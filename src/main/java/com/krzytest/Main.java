package com.krzytest;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Person2 p = new Person2("Adam", 20, "Nowak");
        Person2 p2 = new Person2("nowak");

        System.out.println(p.getName());
        System.out.println(p.getAge());
        p.setName("Michał");
        p.setAge(100);

        System.out.println(p);
    }
}
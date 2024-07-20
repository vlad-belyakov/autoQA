package org.example;

public class Calculator {

    public static void main(String[] args) {
    }

    public int sum(int a, int b){
        return a+b;
    }

    public int div(int a, int b){
        return a/b;
    }

    public String uprgradedDiv(int a, int b){
        if (b != 0){
            return String.valueOf(a/b);
        } else {
            return "невозможно поделить на ноль";
        }
    }

}

package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class HandleInOut {
    private Scanner scanner;

    public HandleInOut() {
        resetSystemIn();
    }

    public void resetSystemIn() {
        this.scanner = new Scanner(System.in);
    }

    public String in() {
        return this.scanner.nextLine().trim();
    }

    public void out(String s){
        System.out.print(s);
    }

    public void outAndCr(String s){
        System.out.println(s);
    }
}

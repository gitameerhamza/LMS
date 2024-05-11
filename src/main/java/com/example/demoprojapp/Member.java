package com.example.demoprojapp;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private int id;
    private String name;
    private List<Integer> borrowedBooks;

    public Member(int id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(int bookId) {
        borrowedBooks.add(bookId);
    }

    public void returnBook(int bookId) {
        borrowedBooks.remove(Integer.valueOf(bookId));
    }
}

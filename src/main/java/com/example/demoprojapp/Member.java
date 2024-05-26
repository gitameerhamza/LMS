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
        List<Integer> borrowedBooks = getBorrowedBooks(); // Assuming this returns a list of borrowed book IDs
        int index = borrowedBooks.indexOf(bookId);
        if (index != -1) {
            borrowedBooks.remove(index);
        } else {
            System.out.println("Book ID not found in borrowed books list.");
        }
    }
}

package com.example.demoprojapp;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private int id;
    private String name;
    final private List<Integer> borrowedBooks;
    public Member(){
        borrowedBooks = new ArrayList<>();
    }
    public Member(int id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Book> getStringBorrowedBooks() {
        List<Book> borrowedBooks1 = new ArrayList<>();
        Library library = new Library();
        if(!borrowedBooks.isEmpty()) {
            System.out.println(borrowedBooks.size());
            for (Integer id : borrowedBooks) {
                borrowedBooks1.add(library.findBook(String.valueOf(id)));
            }
            return borrowedBooks1;
        }
        return null;
    }
    public void borrowBook(int bookId) {
        borrowedBooks.add(bookId);
    }

    public void returnBook(int bookId) {
        int index = borrowedBooks.indexOf(bookId);
        if (index != -1) {
            borrowedBooks.remove(index);
        }
    }
}

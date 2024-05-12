package com.example.demoprojapp;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<Member> members;
    private static final String BOOKS_FILE = "books.txt";
    private static final String MEMBERS_FILE = "members.txt";

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        loadBooks();
        loadMembers();
    }

    private void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    try {
                        String id = String.valueOf(Integer.parseInt(parts[0]));
                        String title = parts[1];
                        String author = parts[2];
                        boolean available = Boolean.parseBoolean(parts[3]);
                        Book book = new Book(id, title, author);
                        book.setAvailable(available);
                        books.add(book);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid book entry: " + line + ". Skipping...");
                    }
                } else {
                    System.out.println("Invalid book entry: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }

    private void loadMembers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                Member member = new Member(id, name);
                for (int i = 2; i < parts.length; i++) {
                    member.borrowBook(Integer.parseInt(parts[i]));
                }
                members.add(member);
            }
        } catch (IOException e) {
            System.out.println("Error loading members: " + e.getMessage());
        }
    }

    private void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.isAvailable());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private void saveMembers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member member : members) {
                StringBuilder sb = new StringBuilder();
                sb.append(member.getId()).append(",").append(member.getName());
                for (int bookId : member.getBorrowedBooks()) {
                    sb.append(",").append(bookId);
                }
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving members: " + e.getMessage());
        }
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooks();
    }

    public void addMember(Member member) {
        members.add(member);
        saveMembers();
    }

    public Book findBook(String bookIdOrName) {
        for (Book book : books) {
            if (book.getId().equals(bookIdOrName)) {
                return book;
            }
            if (book.getTitle().equals(bookIdOrName)) {
                return book;
            }
        }
        return null;
    }

    public Member findMember(int memberId) {
        for (Member member : members) {
            if (member.getId() == memberId) {
                return member;
            }
        }
        return null;
    }

    public boolean lendBook(int memberId, String bookId) {
        Member member = findMember(memberId);
        Book book = findBook(bookId);

        if (member != null && book != null && book.isAvailable()) {
            member.borrowBook(Integer.parseInt(bookId));
            book.setAvailable(false);
            saveBooks();
            saveMembers();
            return true;
        } else {
            return false;
        }
    }

    public void returnBook(int memberId, int bookId) {
        Member member = findMember(memberId);
        Book book = findBook(String.valueOf(bookId));

        if (member != null && book != null && !book.isAvailable()) {
            member.returnBook(bookId);
            book.setAvailable(true);
            System.out.println("Book '" + book.getTitle() + "' has been successfully returned by " + member.getName());
            saveBooks();
            saveMembers();
        } else {
            System.out.println("Book cannot be returned. Check member ID or book availability.");
        }
    }

    public void showMemberInfo(int memberId) {
        Member member = findMember(memberId);
        if (member != null) {
            System.out.println("Member ID: " + member.getId());
            System.out.println("Member Name: " + member.getName());
            List<Integer> borrowedBooks = member.getBorrowedBooks();
            if (!borrowedBooks.isEmpty()) {
                System.out.println("Books Borrowed:");
                for (int bookId : borrowedBooks) {
                    Book book = findBook(String.valueOf(bookId));
                    if (book != null) {
                        System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor());
                    }
                }
            } else {
                System.out.println("No books borrowed by this member.");
            }
        } else {
            System.out.println("Member with ID " + memberId + " not found.");
        }
    }
}

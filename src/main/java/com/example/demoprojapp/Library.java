package com.example.demoprojapp;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
public class Library {
    String url="jdbc:mysql://localhost:3306/Library";
    String username="root";
    String password="";
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
    private void savebook(){

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

    public Boolean addBook(Book book) {
        if (book == null || book.getTitle() == null || book.getAuthor() == null) {
            System.out.println("Book or book properties cannot be null");
            return false;
        }

        books.add(book);
        String sqlQuery = "INSERT INTO Books (ID, `Book Name`, `Author Name`, Availability) VALUES (?, ?, ?, TRUE)";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = con.prepareStatement(sqlQuery)) {

            pstmt.setInt(1, Integer.parseInt(book.getId()));
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());

            pstmt.executeUpdate();
            saveBooks();
            return true;
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        return false;
    }

    //understanding of this part is undergoing
public void addMember(Member member) {
//    members.add(member);
    String sql = "INSERT INTO Members (Name, ID) VALUES (?, ?)";
    try (Connection con = DriverManager.getConnection(url, username, password);
         //prepared statement is liye use ki ha k user koi query enter na kry
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, member.getName());
        ps.setInt(2, member.getId());
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Member added successfully.");
        } else {
            System.out.println("Failed to add member.");
        }
    } catch (SQLException e) {
        System.out.println("Error adding member: " + e.getMessage());
    }
    saveMembers();
}
// everything working absolutely fine
    public Book findBook(String bookIdOrName) {
        String sql = "select *from Books WHERE ID="+bookIdOrName+";";
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            try {
                Statement st= con.createStatement();
                ResultSet sr=st.executeQuery(sql);
                    if (sr.next()) {
                        String id = sr.getString("ID");
                        String title = sr.getString("Book Name");
                        String author = sr.getString("Author name");
                        boolean availability = sr.getBoolean("Availability");
                        Book book =new Book(id,title,author,availability);
                        return book;
                    }

            }catch (Exception e){
                System.out.println("Query issue"+e.getMessage());
            }
        }catch(Exception e){
            System.out.println("Connection Error");
        }
        return null;
    }

    public Member findMember(int memberId) {
        String sql = "select *from Members WHERE ID="+memberId+";";
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            try {
                Statement st= con.createStatement();
                ResultSet sr=st.executeQuery(sql);
                if (sr.next()) {
                    int id = sr.getInt("ID");
                    String name= sr.getString("Name");
                    return new Member(id,name);//suggested by intellij
                }

            }catch (Exception e){
                System.out.println("Query issue"+e.getMessage());
            }
        }catch(Exception e){
            System.out.println("Connection Error");
        }
        return null;
    }
    public boolean lendBook(int memberId, String bookId) {
        Member member = findMember(memberId);
        Book book = findBook(bookId);
        if (member != null && book != null && book.isAvailable()) {
            String updateMemberSQL = "UPDATE Members SET `Borrowed Books` = CONCAT(`Borrowed Books`, ?) WHERE ID = ?";
            String updateBookSQL = "UPDATE Books SET Availability = false WHERE ID = ?";
            try (Connection con = DriverManager.getConnection(url, username, password)) {
                try (PreparedStatement pstMember = con.prepareStatement(updateMemberSQL);
                     PreparedStatement pstBook = con.prepareStatement(updateBookSQL)) {
                    pstMember.setString(1, "," + book.getId());
                    pstMember.setInt(2, member.getId());
                    pstMember.executeUpdate();
                    pstBook.setString(1, book.getId());
                    pstBook.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Query issue: " + e.getMessage());
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Connection Error: " + e.getMessage());
                return false;
            }
            member.borrowBook(Integer.parseInt(bookId));
            book.setAvailable(false);
            saveBooks();
            saveMembers();
            return true;
        }
            return false;
    }
    public boolean returnBook(int memberId, int bookId) {
        Member member = findMember(memberId);
        Book book = findBook(String.valueOf(bookId));
        if (member != null && book != null && !book.isAvailable()) {
            String getMemSQL = "SELECT `Borrowed Books` FROM MEMBERS WHERE ID = " + member.getId();
            String updateBookSQL = "UPDATE Books SET Availability = true WHERE ID = " + book.getId();
            try {
                Connection con = DriverManager.getConnection(url, username, password);
                try (Statement st = con.createStatement()) {
                    ResultSet sr = st.executeQuery(getMemSQL);
                    if (sr.next()) {
                        String borrowedBooks = sr.getString("Borrowed Books");
                        String[] parts = borrowedBooks.split(",");

                        StringBuilder updatedData = new StringBuilder();
                        for (String part : parts) {
                            if (!part.trim().equals(String.valueOf(bookId))) {
                                if (updatedData.length() > 0) {
                                    updatedData.append(",");
                                }
                                updatedData.append(part);
                            }
                        }

                        String updateMemberSQL = "UPDATE Members SET `Borrowed Books` = '" + updatedData.toString() + "' WHERE ID = " + member.getId();
                        try {
                            st.executeUpdate(updateMemberSQL);
                            st.executeUpdate(updateBookSQL);
                        } catch (SQLException e) {
                            System.out.println("Confirming " + e.getMessage());
                            return false;
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Query Issue " + e.getMessage());
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Connection Issue " + e.getMessage());
                return false;
            }
            member.returnBook(bookId);
            book.setAvailable(true);
            saveBooks();
            saveMembers();
            System.out.println("Book '" + book.getTitle() + "' has been successfully returned by " + member.getName());
            return true;
        } else {
            System.out.println("Book cannot be returned. Check member ID or book availability.");
        }
        return false;
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
//checking is git working
}

package com.example.demoprojapp;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
@SuppressWarnings("ALL")
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
    }

    public Boolean addBook(Book book) {
        if (book == null || book.getTitle() == null || book.getAuthor() == null) {
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
            return true;
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        return false;
    }

    //understanding of this part is undergoing
public Boolean addMember(Member member) {
    String sql = "INSERT INTO Members (Name, ID) VALUES (?, ?)";
    try (Connection con = DriverManager.getConnection(url, username, password);
         //prepared statement is liye use ki ha k user koi query enter na kry
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, member.getName());
        ps.setInt(2, member.getId());
        int rowsAffected = ps.executeUpdate();
        return true;
    } catch (SQLException e) {
        System.out.println("Error adding member: " + e.getMessage());
        return false;
    }
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
        String booksSql = "SELECT `Borrowed Books` FROM Members WHERE ID=" + memberId + ";";
        Member member = new Member();
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            try {
                Statement st= con.createStatement();
                ResultSet sr=st.executeQuery(sql);
                if (sr.next()) {
                    int id = sr.getInt("ID");
                    String name= sr.getString("Name");
                    ResultSet booksRs = st.executeQuery(booksSql);
                    if (booksRs.next()) {
                        String borrowedBooksStr = booksRs.getString("Borrowed Books");
                        if (borrowedBooksStr != null && !borrowedBooksStr.trim().isEmpty()) {
                            String[] bookIds = borrowedBooksStr.split(",");
//                        List<Integer> borrowedBooks = new ArrayList<>();
                            for (String bookId : bookIds) {
                                member.borrowBook(Integer.parseInt(bookId));
//                            borrowedBooks.add(Integer.parseInt(bookId.trim()));
                            }
//                        if (member != null) {
//                            member.setBorrowedBooks(borrowedBooks);
//                        }
                        }
                    }
                    member.setId(id);
                    member.setName(name);
                    return member;
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
            String updateMemberSQL = "UPDATE Members SET `Borrowed Books` = " +
                    "CASE WHEN `Borrowed Books` IS NULL OR `Borrowed Books` = '' THEN ? " +
                    "ELSE CONCAT(`Borrowed Books`, ?, ?) END WHERE ID = ?";
            String updateBookSQL = "UPDATE Books SET Availability = false WHERE ID = ?";

            try (Connection con = DriverManager.getConnection(url, username, password);
                 PreparedStatement pstMember = con.prepareStatement(updateMemberSQL);
                 PreparedStatement pstBook = con.prepareStatement(updateBookSQL)) {

                // For new entries where the `Borrowed Books` column might be null or empty
                pstMember.setString(1, book.getId());
                pstMember.setString(2, ",");
                pstMember.setString(3, book.getId());
                pstMember.setInt(4, member.getId());

                pstMember.executeUpdate();

                pstBook.setString(1, book.getId());
                pstBook.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Query issue: " + e.getMessage());
                return false;
            }

            member.borrowBook(Integer.parseInt(bookId));
            book.setAvailable(false);
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
            System.out.println("Book '" + book.getTitle() + "' has been successfully returned by " + member.getName());
            return true;
        } else {
            System.out.println("Book cannot be returned. Check member ID or book availability.");
        }
        return false;
    }
    public Member showMemberInfo(int memberId) {
        Member member = findMember(memberId);
        if (member != null) {
            return member;
        } else {
            return member;
        }
    }
}

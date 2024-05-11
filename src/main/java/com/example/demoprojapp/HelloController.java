package com.example.demoprojapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label labl;
    @FXML
    private Label labl_2;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private TextField bookid_in;
    HelloApplication chk;
    public String id_in;
    @FXML
    public void switchscene3(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("scene3.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchscene2(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("scene2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void enterclicked(){
        labl_2.setText(bookid_in.getText());
        bookid_in.clear();
    }
    @FXML
    protected void mylabelviewer(){ labl.setText("just checking");

    }
    static Library library = new Library();
    static Scanner scanner = new Scanner(System.in);
    private static void addBook() {
        System.out.print("Enter Book ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();

        Book book = new Book(id, title, author);
        library.addBook(book);
        System.out.println("Book added successfully.");
    }

    private static void addMember() {
        System.out.print("Enter Member ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Member Name: ");
        String name = scanner.nextLine();

        Member member = new Member(id, name);
        library.addMember(member);
        System.out.println("Member added successfully.");
    }

    private static void searchBook() {
        System.out.print("Enter Book ID: ");
        int id = scanner.nextInt();

        Book book = library.findBook(id);
        if (book != null) {
            System.out.println("Book found:");
            System.out.println("ID: "+book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Availability: " + (book.isAvailable() ? "Available" : "Not Available"));
        } else {
            System.out.println("Book with ID " + id + " not found.");
        }
    }

    private static void borrowBook() {
        System.out.print("Enter Member ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();

        library.lendBook(memberId, bookId);
    }

    private static void returnBook() {
        System.out.print("Enter Member ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();

        library.returnBook(memberId, bookId);
    }

    private static void showMemberInfo() {
        System.out.print("Enter Member ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();

        library.showMemberInfo(memberId);
    }
}
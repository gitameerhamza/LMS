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
import java.util.Objects;
import java.util.Scanner;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label labl;
    @FXML
    private Label label_2;
    @FXML
    private Label bookname_i;
    @FXML
    private Label chk_label;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private TextField bookid_in;
    @FXML
    private TextField book_in2;
    @FXML
    private TextField book_in3;

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
    protected void mylabelviewer(){
        labl.setText("just checking");
    }
    Library library = new Library();
    Scanner scanner = new Scanner(System.in);
    @FXML
    private  void addBook() {

        String id = bookid_in.getText();
        String title = book_in2.getText();
        String author = book_in3.getText();
        if (!(id.isBlank() || title.isBlank() || author.isBlank())) {
            Book book = new Book(id, title, author);
            library.addBook(book);
            label_2.setText("Book added successfully.");
            return;
        }
        label_2.setText("Something Not Right!\nTry Again");


    }

    private void addMember() {
        System.out.print("Enter Member ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Member Name: ");
        String name = scanner.nextLine();

        Member member = new Member(id, name);
        library.addMember(member);
        System.out.println("Member added successfully.");
    }

    private  void searchBook() {
        System.out.print("Enter Book ID: ");
        int id = scanner.nextInt();

        Book book = library.findBook(String.valueOf(id));
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

    private  void borrowBook() {
        System.out.print("Enter Member ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();

        library.lendBook(memberId, String.valueOf(bookId));
    }

    private  void returnBook() {
        System.out.print("Enter Member ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();

        library.returnBook(memberId, bookId);
    }

    private  void showMemberInfo() {
        System.out.print("Enter Member ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();

        library.showMemberInfo(memberId);
    }
}
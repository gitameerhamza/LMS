package com.example.demoprojapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scene1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("LMS");
        stage.setScene(scene);
        stage.show();
    }
    public String bookid;

    public static void main(String[] args) {
        launch();
//        while (true) {
//            System.out.println("===== Library Management System Menu =====");
//            System.out.println("1. Add Book");
//            System.out.println("2. Add Member");
//            System.out.println("3. Search Book");
//            System.out.println("4. Borrow Book");
//            System.out.println("5. Return Book");
//            System.out.println("6. Show Member Info");
//            System.out.println("7. Exit");
//            System.out.print("Enter your choice: ");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1:
//                    addBook();
//                    break;
//                case 2:
//                    addMember();
//                    break;
//                case 3:
//                    searchBook();
//                    break;
//                case 4:
//                    borrowBook();
//                    break;
//                case 5:
//                    returnBook();
//                    break;
//                case 6:
//                    showMemberInfo();
//                    break;
//                case 7:
//                    System.out.println("Exiting...");
//                    System.exit(0);
//                default:
//                    System.out.println("Invalid choice. Please enter a number from 1 to 7.");
//            }
//        }
    }

}
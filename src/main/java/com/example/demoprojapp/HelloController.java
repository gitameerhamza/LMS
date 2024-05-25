package com.example.demoprojapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Scanner;
public class HelloController  {

    @FXML
    private Label label_2;
    @FXML
    private Label l1;
    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private Label l4;
    @FXML
    private Label bookflabel;
    @FXML
    private Label idbox;
    @FXML
    private Stage stage;
    @FXML
    private TextField Memid_in;
    @FXML
    private TextField Memname_in;
    @FXML
    private Button Borow_btn;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private TextField bookid_in,bookid_in2;
    @FXML
    private TextField book_in2;
    @FXML
    private TextField book_in3;
    @FXML
    private TextField idname_btn;
    @FXML
    private TextField memberid,mem2;
    @FXML
    private Label label_b;
    @FXML
    private Label label_c;
    @FXML
    private Label label_c1;

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
    public void switchscene4(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("scene4.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }public void switchscene5(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("scene5.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchscene6(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("scene6.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchscene7(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("scene7.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchscene8(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("scene8.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    Library library = new Library();
    Scanner scanner = new Scanner(System.in);
    @FXML
    private  void addBook() {
        String id = bookid_in.getText();
        String title = book_in2.getText();
        String author = book_in3.getText();
        //user can't enter number in id field
        if(!(id.matches("\\d+"))){
            label_2.setText("ID is Incorrect");
            return;
        }
        if (!(id.isBlank() || title.isBlank() || author.isBlank())) {
            Book book = new Book(id, title, author);
            library.addBook(book);
            label_2.setText("Book added successfully.");
            return;
        }
        label_2.setText("Something Not Right!\nTry Again");
    }

    @FXML
    private void addMember() {
        if(!(Memid_in.getText().matches("\\d+"))){
            label_2.setText("ID not in numbers");
            return;
        }
        if(Memname_in.getText().matches("\\d+")){
            label_2.setText("Member Name in numbers");
            return;
        }
        if(Memid_in.getText().isBlank()){
            label_2.setText("Bro Look at the Id ");
            return;
        }
        int id = Integer.parseInt(Memid_in.getText());
        String name = Memname_in.getText();
        if (!( name.isBlank())) {
            Member member = new Member(id, name);
            library.addMember(member);
            label_2.setText("Member added successfully.");
            return;
        }
    }
    @FXML
    private  void searchBook() {

        String id = idname_btn.getText();
        Book book = library.findBook(id);
        if (book != null) {

            Borow_btn.setOpacity(1);
            label_b.setOpacity(1);
            memberid.setOpacity(1);
            label_c.setOpacity(1);
            bookflabel.setText("BOOK FOUND");
            l1.setText(book.getId());
            l2.setText(book.getTitle());
            l3.setText(book.getAuthor());
            l4.setText((book.isAvailable() ? "Available" : "Not Available"));
        } else {
            l1.setText("");
            l2.setText("");
            l3.setText("");
            l4.setText("");
            Borow_btn.setOpacity(0);
            label_b.setOpacity(0);
            memberid.setOpacity(0);
            label_c.setOpacity(0);
            bookflabel.setText("Book Not Found");
        }
    }
    @FXML
    private  void borrowBook() {
        int memberId = Integer.parseInt(Memid_in.getText());
        int bookId = Integer.parseInt(bookid_in.getText());
        boolean chk=library.lendBook(memberId, String.valueOf(bookId));
        idbox.setText((chk)?"BOOK Borrowed":"BOOK NOT AVAILABLE");
    }
    @FXML
    private  void borrowBook2() {
        int memberId = Integer.parseInt(memberid.getText());
        int bookId = Integer.parseInt(l1.getText());
        boolean chk2=library.lendBook(memberId, String.valueOf(bookId));
        label_c.setText((chk2)?"BOOK Borrowed":"Check ID");
    }
    @FXML
    private  void returnBook() {
        int memberId = Integer.parseInt(mem2.getText());
        int bookId = Integer.parseInt(bookid_in2.getText());
        boolean chk2=library.returnBook(memberId, bookId);
        label_c1.setText((chk2)?"BOOK Returned":"Check ID");
    }
    @FXML
    private  void showMemberInfo() {
        System.out.print("Enter Member ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();
        library.showMemberInfo(memberId);
    }
    @FXML
    private void endit(){
        System.exit(0);
    }

}
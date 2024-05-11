module com.example.demoprojapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.demoprojapp to javafx.fxml;
    exports com.example.demoprojapp;
}
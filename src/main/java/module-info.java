module com.example.remigoapp {
    requires java.sql;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;

    opens com.example.remigoapp to javafx.fxml;
    exports com.example.remigoapp;
}
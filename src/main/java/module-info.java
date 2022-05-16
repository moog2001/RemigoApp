module com.example.remigoapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.remigoapp to javafx.fxml;
    exports com.example.remigoapp;
}
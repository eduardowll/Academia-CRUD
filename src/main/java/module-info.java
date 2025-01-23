module org.app.academia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;


    opens org.app.academia to javafx.fxml;
    opens org.app.academia.model to javafx.base;
    opens org.app.academia.controller to javafx.fxml;
    exports org.app.academia;
    exports org.app.academia.model;
    exports org.app.academia.controller;

}
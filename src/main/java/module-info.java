module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.server to javafx.fxml;
    exports org.server;
}

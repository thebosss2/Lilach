module org.client {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.client to javafx.fxml;
    exports org.client;
}

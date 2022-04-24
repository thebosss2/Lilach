module org.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.entities;

    uses org.entities.Product;

    opens org.client to javafx.fxml;
    exports org.client;

}

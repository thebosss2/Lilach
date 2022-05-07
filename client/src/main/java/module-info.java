module org.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.entities;
    requires org.server;
    requires javafx.graphics;
    uses org.entities.Product;
    uses org.entities.PreMadeProduct;

    opens org.client to javafx.fxml;
    exports org.client;

}

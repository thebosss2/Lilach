package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GuestMenuController {

    @FXML
    private Button cartBtn;

    @FXML
    private Button catalogBtn;

    @FXML
    void goToCart(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button)event.getTarget());
    }

    @FXML
    void goToCatalog(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button)event.getTarget());

    }

    private void coolButtonClick(Button button) throws InterruptedException{
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            button.setStyle("-fx-background-color: #62a74d");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            button.setStyle("-fx-background-color:  #9bc98c");
        });
    }

}
package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GuestMenuController extends Controller{

    @FXML
    protected Button cartBtn;

    @FXML
    protected Button catalogBtn;

    @FXML
    void goToCart(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button)event.getTarget());
    }

    @FXML
    void goToCatalog(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button)event.getTarget());
        this.getSkeleton().changeCenter("Catalog");

    }

    @FXML
    void mouseOffBtn(MouseEvent event) {
        //TODO
    }

    @FXML
    void mouseOnBtn(MouseEvent event) {
        //TODO
    }

    protected void coolButtonClick(Button button) throws InterruptedException{
        cartBtn.setStyle("-fx-background-color: #9bc98c");
        catalogBtn.setStyle("-fx-background-color: #9bc98c");
        button.setStyle("-fx-background-color: #62a74d");
    }

}
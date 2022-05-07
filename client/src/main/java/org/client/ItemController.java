package org.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class ItemController extends Controller {

    @FXML
    protected Pane pane;

    @FXML // fx:id="image"
    protected ImageView image; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    protected Text name; // Value injected by FXMLLoader

    protected void coolButtonClick(Button button) throws InterruptedException {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            button.setStyle("-fx-background-color: #8c73ea");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            button.setStyle("-fx-background-color: #c6acef");
        });
    }

    @FXML
    protected void mouseOnProduct(MouseEvent event) {
        pane.setStyle("-fx-background-color: #e5dcff");
    }

    @FXML
    protected void mouseOffProduct(MouseEvent event) {
        pane.setStyle("-fx-background-color: #ffffff");
    }

    protected void clickOnProductEffect(MouseEvent event) throws InterruptedException {

        pane.setStyle("-fx-background-color: #a8a1d5");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pane.setStyle("-fx-background-color: #ffffff");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

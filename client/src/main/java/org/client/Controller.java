package org.client;

import javafx.scene.control.Button;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class Controller {

    protected StoreSkeleton globalSkeleton;

    public void setSkeleton(StoreSkeleton skeleton) {
        this.globalSkeleton = skeleton;
    } //set skeleton

    public StoreSkeleton getSkeleton() {
        return this.globalSkeleton;
    } //returns skeleton information

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

}

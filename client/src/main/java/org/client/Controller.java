package org.client;

import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
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

    public void displayDates(DatePicker dp, LocalDate day, boolean past) {
        // this function gets a datePicker and display only the dates before "day" if we chose past==true (or after if past==false)
        dp.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if(past)
                    setDisable(empty || date.compareTo(day) > 0);
                else // display future
                    setDisable(empty || date.compareTo(day) < 0);
            }
        });
    }

    public void displayDates(DatePicker dp, LocalDate fromDay, LocalDate toDay) {
        // this function gets a datePicker and shows only the dates between "fromDay" to "toDay"
        dp.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(toDay) > 0 || date.compareTo(fromDay) < 0);
            }
        });
    }
}

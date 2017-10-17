package com.module.controllers;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ProgressController {

    public void showProgressBar(Stage primaryStage, Task task) throws IOException {
        AnchorPane progressBarPane = new AnchorPane();
        progressBarPane.setBackground(Background.EMPTY);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = new Scene(progressBarPane);

        Label label = new Label();
        label.setText("Пожалуйста подождите");
        label.setLayoutX(47.0);
        label.setLayoutY(14.0);
        AnchorPane.setTopAnchor(label, 10.0);
        AnchorPane.setBottomAnchor(label, 50.0);
        AnchorPane.setLeftAnchor(label, 45.0);
        AnchorPane.setRightAnchor(label, 45.0);

        final ProgressBar progressBar = new ProgressBar();
        progressBar.setLayoutY(10.0);
        progressBar.setLayoutY(47.0);
        AnchorPane.setTopAnchor(progressBar, 40.0);
        AnchorPane.setBottomAnchor(progressBar, 10.0);
        AnchorPane.setLeftAnchor(progressBar, 10.0);
        AnchorPane.setRightAnchor(progressBar, 10.0);

        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        stage.close();
                    }
                });

        progressBarPane.getChildren().addAll(label, progressBar);
        stage.setScene(scene);
        stage.show();
    }

    public void showProgressIndicator(Stage primaryStage, Task task) throws IOException {
        HBox progressBarPane = new HBox();
        progressBarPane.setBackground(Background.EMPTY);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = new Scene(progressBarPane);

        final ProgressIndicator progressIndicator = new ProgressIndicator();
        AnchorPane.setTopAnchor(progressIndicator, 10.0);
        AnchorPane.setBottomAnchor(progressIndicator, 10.0);
        AnchorPane.setLeftAnchor(progressIndicator, 10.0);
        AnchorPane.setRightAnchor(progressIndicator, 10.0);

        progressIndicator.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        stage.close();
                    }
                });

        progressBarPane.setAlignment(Pos.CENTER);
        progressBarPane.getChildren().addAll(progressIndicator);
        stage.setScene(scene);
        stage.show();
    }
}

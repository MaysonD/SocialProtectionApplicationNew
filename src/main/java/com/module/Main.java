package com.module;

import com.module.controllers.DatabasePropertiesController;
import com.module.controllers.LoginDialogController;
import com.module.controllers.RootManager;
import com.module.auth.SecurityConfiguration;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@SpringBootApplication
@Import(SecurityConfiguration.class)
public class Main extends Application {

    private Stage primaryStage;
    private ConfigurableApplicationContext springContext;
    private boolean noDatabase = false;

    public static void main(final String[] args) {
        Application.launch(args);
    }

    private static void initSecurity() {
        SecurityContextHolder.setStrategyName("MODE_GLOBAL");
    }

    @Bean("getPrimaryStage")
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    @Override
    public void init() throws Exception {

        try {
            springContext = SpringApplication.run(Main.class);
            initSecurity();
        } catch (RuntimeException ex) {
            noDatabase = true;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        if (noDatabase) {
            Platform.runLater(() -> {

                FXMLLoader fxmlLoginLoader = new FXMLLoader(getClass().getResource("/view/DatabaseProperties.fxml"));
                Parent rootNode = null;
                try {
                    rootNode = fxmlLoginLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage propertiesStage = new Stage();
                propertiesStage.setResizable(false);
                propertiesStage.initModality(Modality.WINDOW_MODAL);
                propertiesStage.initStyle(StageStyle.DECORATED);
                propertiesStage.getIcons().add(new Image("file:///" + System.getProperty("user.dir") + "/icon.jpg"));
                propertiesStage.setScene(new Scene(rootNode));

                DatabasePropertiesController controller = fxmlLoginLoader.getController();
                controller.setStage(propertiesStage);
                propertiesStage.show();
            });
        } else {
            this.primaryStage = stage;
            this.primaryStage.getIcons().add(new Image("file:///" + System.getProperty("user.dir") + "/icon.jpg"));
            FXMLLoader fxmlLoginLoader = new FXMLLoader(getClass().getResource("/view/LoginDialog.fxml"));
            fxmlLoginLoader.setControllerFactory(springContext::getBean);
            Parent rootNode = fxmlLoginLoader.load();
            Stage loginStage = new Stage();
            loginStage.setResizable(false);
            loginStage.initModality(Modality.WINDOW_MODAL);
            loginStage.initStyle(StageStyle.DECORATED);
            loginStage.getIcons().add(new Image("file:///" + System.getProperty("user.dir") + "/icon.jpg"));
            loginStage.setScene(new Scene(rootNode));
            LoginDialogController loginDialogController = fxmlLoginLoader.getController();
            loginDialogController.setStage(loginStage);
            loginStage.showAndWait();
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Root.fxml"));
                fxmlLoader.setControllerFactory(springContext::getBean);
                RootManager rootManager = springContext.getBean(RootManager.class);
                rootManager.setPrimaryStage(primaryStage);
                rootNode = fxmlLoader.load();
                primaryStage.setScene(new Scene(rootNode));
                primaryStage.show();
            }
        }
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }
}
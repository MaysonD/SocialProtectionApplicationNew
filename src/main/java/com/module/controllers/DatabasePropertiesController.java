package com.module.controllers;

import com.module.helpers.ScriptRunner;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Data;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Data
public class DatabasePropertiesController {

    @FXML
    private TextField serverURLField;
    @FXML
    private TextField serverUserNameField;
    @FXML
    private TextField serverUserPasswordField;
    @FXML
    private Button savePropertiesButton;

    private Stage stage;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleSavePropertiesButton() throws IOException {
        String url = serverURLField.getText();
        String userName = serverUserNameField.getText();
        String password = serverUserPasswordField.getText();
        final Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                setServerSettings(url, userName, password);
                return null;
            }
        };
        ProgressController progressController = new ProgressController();
        progressController.showProgressBar(stage, task);
    }

    private void setServerSettings(String url, String login, String password) {
        String systemFolder = System.getProperty("user.home") + "/Social Protection Module";
        File configFile = new File(systemFolder);
        if (!configFile.exists()) {
            configFile.mkdir();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(configFile.getAbsolutePath() + "/database.properties"))) {
            String content = "spring.datasource.url=jdbc:mysql://" + url + "/social_protection?useSSL=false\n" +
                    "spring.datasource.username=" + login + "\n" +
                    "spring.datasource.password=" + password + "\n" +
                    "spring.datasource.driver-class-name=com.mysql.jdbc.Driver\n";

            bw.write(content);
            bw.close();
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + url + "?useSSL=false", login, password);
                stmt = conn.createStatement();
                try {
                    String db = "CREATE DATABASE social_protection";
                    stmt.executeUpdate(db);
                } catch (SQLException sqlEx) {
                    System.exit(0);
                }
                ScriptRunner sr = new ScriptRunner(conn, false, false);
                sr.runScript(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/social_protection.sql"), "UTF-8")));
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null)
                        stmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}

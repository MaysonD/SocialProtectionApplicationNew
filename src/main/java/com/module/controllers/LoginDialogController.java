package com.module.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class LoginDialogController {

    private final AuthenticationManager authManager;
    @FXML
    private TextField inputDistrictField;
    @FXML
    private PasswordField inputPasswordField;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private Label errorLoginLabel;
    private Stage stage;

    @Autowired
    public LoginDialogController(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    public void setLoginAction() throws Exception {
        try {
            final String userName = inputDistrictField.getText().trim();
            final String password = inputPasswordField.getText().trim();

            Authentication request = new UsernamePasswordAuthenticationToken(userName, password);
            Authentication result = authManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            stage.close();

        } catch (AuthenticationException e) {
            inputPasswordField.clear();
            inputDistrictField.clear();
            errorLoginLabel.setVisible(true);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        loginPane.maxHeight(350);
        loginPane.maxWidth(250);
    }
}

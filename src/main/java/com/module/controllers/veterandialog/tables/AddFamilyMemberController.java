package com.module.controllers.veterandialog.tables;

import com.module.model.entity.FamilyMemberEntity;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class AddFamilyMemberController {

    @FXML
    private TextField addressField;
    @FXML
    private DatePicker dateOfBirthField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField relationDegreeField;

    private Stage dialogStage;

    private FamilyMemberEntity familyMemberEntity;

    private boolean saveClicked = false;

    @FXML
    public void initialize() {
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setFamilyMemberEntity(FamilyMemberEntity familyMemberEntity) {
        this.familyMemberEntity = familyMemberEntity;
        relationDegreeField.setText(this.familyMemberEntity.getRelationDegree());
        fullNameField.setText(this.familyMemberEntity.getFullName());
        addressField.setText(this.familyMemberEntity.getAddress());
        dateOfBirthField.setValue(this.familyMemberEntity.getDateOfBirth());
        phoneNumberField.setText(this.familyMemberEntity.getPhoneNumber());
    }

    @FXML
    private void cancelButtonClick() {
        dialogStage.close();
    }

    @FXML
    private void saveButtonClick() {
        setNewFamilyMemberEntity();
        saveClicked = true;
        dialogStage.close();
    }

    private void setNewFamilyMemberEntity() {
        this.familyMemberEntity.setRelationDegree(relationDegreeField.getText());
        this.familyMemberEntity.setFullName(fullNameField.getText());
        this.familyMemberEntity.setAddress(addressField.getText());
        this.familyMemberEntity.setDateOfBirth(dateOfBirthField.getValue());
        this.familyMemberEntity.setPhoneNumber(phoneNumberField.getText());
    }
}

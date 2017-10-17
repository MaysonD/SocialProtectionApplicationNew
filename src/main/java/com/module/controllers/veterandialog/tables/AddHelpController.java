package com.module.controllers.veterandialog.tables;


import com.module.model.entity.HelpEntity;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class AddHelpController {

    @FXML
    private TextField organizationName;
    @FXML
    private TextField helpType;
    @FXML
    private DatePicker helpDate;
    @FXML
    private TextField baseToHelp;

    private Stage dialogStage;

    private HelpEntity helpEntity;

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

    public void setHelpEntity(HelpEntity helpEntity) {
        this.helpEntity = helpEntity;
        organizationName.setText(this.helpEntity.getOrganizationName());
        helpType.setText(this.helpEntity.getHelpType());
        helpDate.setValue(this.helpEntity.getHelpDate());
        baseToHelp.setText(this.helpEntity.getBaseToHelp());
    }

    @FXML
    private void cancelButtonClick() {
        dialogStage.close();
    }

    @FXML
    private void saveButtonClick() {
        setNewHelpEntity();
        saveClicked = true;
        dialogStage.close();
    }

    private void setNewHelpEntity() {
        this.helpEntity.setOrganizationName(organizationName.getText());
        this.helpEntity.setHelpType(helpType.getText());
        this.helpEntity.setHelpDate(helpDate.getValue());
        this.helpEntity.setBaseToHelp(baseToHelp.getText());
    }
}

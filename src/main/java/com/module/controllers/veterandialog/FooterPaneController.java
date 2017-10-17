package com.module.controllers.veterandialog;

import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.VeteranEntity;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class FooterPaneController {

    @Autowired
    private VeteranDialogDataManager veteranDialogDataManager;

    private boolean saveClicked = false;

    private Stage dialogStage;

    private MainDataPaneController mainDataPaneController;

    public boolean isSaveClicked() {
        return saveClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainDataPaneController(MainDataPaneController mainDataPaneController) {
        this.mainDataPaneController = mainDataPaneController;
    }

    public void setVeteranData(VeteranEntity veteranEntity) {
        veteranDialogDataManager.setVeteranEntity(veteranEntity);
    }

    @FXML
    private void cancelButtonClick() {
        CustomAlertCreator customAlertCreator = new CustomAlertCreator();
        Optional<ButtonType> result = customAlertCreator.createNotSaveDataConfirmationAlert().showAndWait();

        if (result.get() == customAlertCreator.getButtonTypeOk()) {
            saveButtonClick();
        } else if (result.get() == customAlertCreator.getButtonTypeNo()) {
            dialogStage.close();
        }
    }

    @FXML
    private void saveButtonClick() {
        mainDataPaneController.getValidationSupport().setErrorDecorationEnabled(true);
        if (mainDataPaneController.getValidationSupport().getValidationResult().getErrors().size() == 0) {
            saveClicked = true;
            veteranDialogDataManager.setNewVeteran();
            dialogStage.close();
        }
    }
}

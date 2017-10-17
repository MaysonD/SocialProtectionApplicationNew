package com.module.controllers.veterandialog.tables;


import com.module.model.entity.DocumentEntity;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class AddDocumentController {

    @FXML
    private DatePicker dateOfIssueField;
    @FXML
    private TextField issuedByField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField seriesField;

    private Stage dialogStage;

    private DocumentEntity documentEntity;

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

    public void setDocumentEntity(DocumentEntity documentEntity) {
        this.documentEntity = documentEntity;
        nameField.setText(this.documentEntity.getName());
        seriesField.setText(this.documentEntity.getSeries());
        numberField.setText(this.documentEntity.getNumber());
        issuedByField.setText(this.documentEntity.getIssuedBy());
        dateOfIssueField.setValue(this.documentEntity.getDate());
    }

    @FXML
    private void cancelButtonClick() {
        dialogStage.close();
    }

    @FXML
    private void saveButtonClick() {
        setNewDocumentEntity();
        saveClicked = true;
        dialogStage.close();
    }

    private void setNewDocumentEntity() {
        this.documentEntity.setName(nameField.getText());
        this.documentEntity.setSeries(seriesField.getText());
        this.documentEntity.setNumber(numberField.getText());
        this.documentEntity.setIssuedBy(issuedByField.getText());
        this.documentEntity.setDate(dateOfIssueField.getValue());
    }
}

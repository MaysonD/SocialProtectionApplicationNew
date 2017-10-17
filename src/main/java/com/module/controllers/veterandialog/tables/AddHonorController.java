package com.module.controllers.veterandialog.tables;

import com.module.model.entity.HonorEntity;
import com.module.model.entity.VeteranHonorEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AddHonorController {

    @FXML
    private DatePicker dateOfReceivingField;
    @FXML
    private ComboBox<HonorEntity> nameField;
    @FXML
    private TextField signedOrder;

    private Stage dialogStage;

    private ObservableList<HonorEntity> honorEntities = FXCollections.observableArrayList();

    private boolean saveClicked = false;

    private VeteranHonorEntity veteranHonorEntity;

    @FXML
    public void initialize() {
        honorEntities.clear();
        nameField.setItems(honorEntities);
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setHonorEntity(VeteranHonorEntity veteranHonorEntity) {
        this.veteranHonorEntity = veteranHonorEntity;
        nameField.setValue(this.veteranHonorEntity.getHonor());
        dateOfReceivingField.setValue(this.veteranHonorEntity.getDateOfReceiving());
        signedOrder.setText(this.veteranHonorEntity.getDecree());
    }

    @FXML
    private void cancelButtonClick() {
        dialogStage.close();
    }

    @FXML
    private void saveButtonClick() {
        setNewHonorEntity();
        saveClicked = true;
        dialogStage.close();
    }

    private void setNewHonorEntity() {
        this.veteranHonorEntity.setHonor(nameField.getValue());
        this.veteranHonorEntity.setDateOfReceiving(dateOfReceivingField.getValue());
        this.veteranHonorEntity.setDecree(signedOrder.getText());
    }
}

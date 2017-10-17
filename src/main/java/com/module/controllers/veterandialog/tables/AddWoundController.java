package com.module.controllers.veterandialog.tables;

import com.module.model.entity.VeteranWoundEntity;
import com.module.model.entity.WoundDisabilityEntity;
import com.module.model.entity.WoundTypeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AddWoundController {

    @FXML
    private DatePicker dateField;
    @FXML
    private ComboBox<WoundDisabilityEntity> disabilityField;
    @FXML
    private ComboBox<WoundTypeEntity> typeField;

    private ObservableList<WoundDisabilityEntity> disabilityEntities = FXCollections.observableArrayList();
    private ObservableList<WoundTypeEntity> typeEntities = FXCollections.observableArrayList();

    private Stage dialogStage;

    private boolean saveClicked = false;

    private VeteranWoundEntity woundEntity;

    @FXML
    public void initialize() {
        typeField.setItems(typeEntities);
        disabilityField.setItems(disabilityEntities);
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setWoundEntity(VeteranWoundEntity veteranWoundEntity) {
        this.woundEntity = veteranWoundEntity;
        typeField.setValue(this.woundEntity.getWoundType());
        disabilityField.setValue(this.woundEntity.getWoundDisability());
        dateField.setValue(this.woundEntity.getDate());
    }

    @FXML
    private void cancelButtonClick() {
        dialogStage.close();
    }

    @FXML
    private void saveButtonClick() {
        setNewWoundEntity();
        saveClicked = true;
        dialogStage.close();
    }

    private void setNewWoundEntity() {
        this.woundEntity.setWoundType(typeField.getValue());
        this.woundEntity.setDate(dateField.getValue());
        this.woundEntity.setWoundDisability(disabilityField.getValue());
    }
}

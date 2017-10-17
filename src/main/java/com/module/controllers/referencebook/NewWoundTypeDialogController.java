package com.module.controllers.referencebook;

import com.module.database.DatabaseWorker;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.WoundTypeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@Component
public class NewWoundTypeDialogController {

    @FXML
    private TableView<WoundTypeEntity> existsWoundTypesTable;
    @FXML
    private TableColumn<WoundTypeEntity, String> woundTypesColumn;
    @FXML
    private TextField newWoundTypeTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private DatabaseWorker databaseWorker;

    private ObservableList<WoundTypeEntity> woundTypeData = FXCollections.observableArrayList();

    private Stage dialogStage;

    private WoundTypeEntity selectedItem;
    private int selectedIndex;

    @FXML
    public void initialize() {
        woundTypesColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        existsWoundTypesTable.setItems(woundTypeData);

        existsWoundTypesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            } else {
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });
        newWoundTypeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty())
                saveButton.setDisable(false);
            else saveButton.setDisable(true);
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleCloseButton() {
        dialogStage.close();
    }

    @FXML
    private void handleDeleteButton() {
        int selectedIndex = existsWoundTypesTable.getSelectionModel().getSelectedIndex();
        WoundTypeEntity selectedItem = existsWoundTypesTable.getSelectionModel().getSelectedItem();

        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                woundTypeData.remove(selectedIndex);
                databaseWorker.deleteWoundType(selectedItem);
                existsWoundTypesTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditButton() {
        this.selectedIndex = existsWoundTypesTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            this.selectedItem = existsWoundTypesTable.getSelectionModel().getSelectedItem();
            newWoundTypeTextField.setText(selectedItem.getType());
        }
    }

    @FXML
    private void handleSaveButton() {
        if (this.selectedItem == null) {
            selectedItem = new WoundTypeEntity(newWoundTypeTextField.getText());
            woundTypeData.add(selectedItem);
        } else {
            selectedItem.setType(newWoundTypeTextField.getText());
            woundTypeData.set(selectedIndex, selectedItem);
        }
        databaseWorker.saveWoundType(selectedItem);
        selectedItem = null;
        newWoundTypeTextField.clear();
        existsWoundTypesTable.refresh();
    }
}


package com.module.controllers.referencebook;

import com.module.database.DatabaseWorker;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.WoundDisabilityEntity;
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
public class NewWoundDisabilityDialogController {

    @FXML
    private TableView<WoundDisabilityEntity> existsWoundDisabilitiesTable;
    @FXML
    private TableColumn<WoundDisabilityEntity, String> woundDisabilitiesColumn;
    @FXML
    private TextField newWoundDisabilityTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private DatabaseWorker databaseWorker;

    private ObservableList<WoundDisabilityEntity> woundDisabilityData = FXCollections.observableArrayList();

    private Stage dialogStage;

    private WoundDisabilityEntity selectedItem;
    private int selectedIndex;

    @FXML
    public void initialize() {
        woundDisabilitiesColumn.setCellValueFactory(new PropertyValueFactory<>("disability"));
        existsWoundDisabilitiesTable.setItems(woundDisabilityData);

        existsWoundDisabilitiesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            } else {
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });
        newWoundDisabilityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
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
        int selectedIndex = existsWoundDisabilitiesTable.getSelectionModel().getSelectedIndex();
        WoundDisabilityEntity selectedItem = existsWoundDisabilitiesTable.getSelectionModel().getSelectedItem();

        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                woundDisabilityData.remove(selectedIndex);
                databaseWorker.deleteWoundDisability(selectedItem);
                existsWoundDisabilitiesTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditButton() {
        this.selectedIndex = existsWoundDisabilitiesTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            this.selectedItem = existsWoundDisabilitiesTable.getSelectionModel().getSelectedItem();
            newWoundDisabilityTextField.setText(selectedItem.getDisability());
        }
    }

    @FXML
    private void handleSaveButton() {
        if (this.selectedItem == null) {
            selectedItem = new WoundDisabilityEntity(newWoundDisabilityTextField.getText());
            woundDisabilityData.add(selectedItem);
        } else {
            selectedItem.setDisability(newWoundDisabilityTextField.getText());
            woundDisabilityData.set(selectedIndex, selectedItem);
        }
        databaseWorker.saveWoundDisability(selectedItem);
        selectedItem = null;
        newWoundDisabilityTextField.clear();
        existsWoundDisabilitiesTable.refresh();
    }
}


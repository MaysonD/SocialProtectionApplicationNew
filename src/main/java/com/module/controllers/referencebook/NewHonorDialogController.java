package com.module.controllers.referencebook;

import com.module.database.DatabaseWorker;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.HonorEntity;
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
public class NewHonorDialogController {

    @FXML
    private TableView<HonorEntity> existsHonorsTable;
    @FXML
    private TableColumn<HonorEntity, String> honorsColumn;
    @FXML
    private TextField newHonorTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private DatabaseWorker databaseWorker;

    private ObservableList<HonorEntity> honorData = FXCollections.observableArrayList();

    private Stage dialogStage;

    private HonorEntity selectedItem;
    private int selectedIndex;

    @FXML
    public void initialize() {
        honorsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        existsHonorsTable.setItems(honorData);

        existsHonorsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            } else {
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });
        newHonorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty())
                saveButton.setDisable(false);
            else saveButton.setDisable(true);
        });
    }

    @FXML
    private void handleCloseButton() {
        dialogStage.close();
    }

    @FXML
    private void handleDeleteButton() {
        int selectedIndex = existsHonorsTable.getSelectionModel().getSelectedIndex();
        HonorEntity selectedItem = existsHonorsTable.getSelectionModel().getSelectedItem();

        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                honorData.remove(selectedIndex);
                databaseWorker.deleteHonor(selectedItem);
                existsHonorsTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditButton() {
        this.selectedIndex = existsHonorsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            this.selectedItem = existsHonorsTable.getSelectionModel().getSelectedItem();
            newHonorTextField.setText(selectedItem.getName());
        }
    }

    @FXML
    private void handleSaveButton() {
        if (this.selectedItem == null) {
            selectedItem = new HonorEntity(newHonorTextField.getText());
            honorData.add(selectedItem);
        } else {
            selectedItem.setName(newHonorTextField.getText());
            honorData.set(selectedIndex, selectedItem);
        }
        databaseWorker.saveHonor(selectedItem);
        selectedItem = null;
        newHonorTextField.clear();
        existsHonorsTable.refresh();
    }
}


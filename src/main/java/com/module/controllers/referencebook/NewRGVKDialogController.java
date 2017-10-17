package com.module.controllers.referencebook;

import com.module.database.DatabaseWorker;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.RgvkEntity;
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
public class NewRGVKDialogController {

    @FXML
    private TableView<RgvkEntity> existsRgvkTable;
    @FXML
    private TableColumn<RgvkEntity, String> rgvkColumn;
    @FXML
    private TextField newRgvkTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;


    private DatabaseWorker databaseWorker;

    private ObservableList<RgvkEntity> rgvkData = FXCollections.observableArrayList();

    private Stage dialogStage;

    private RgvkEntity selectedItem;
    private int selectedIndex;

    @FXML
    public void initialize() {
        rgvkColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        existsRgvkTable.setItems(rgvkData);

        existsRgvkTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            } else {
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });
        newRgvkTextField.textProperty().addListener((observable, oldValue, newValue) -> {
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
        int selectedIndex = existsRgvkTable.getSelectionModel().getSelectedIndex();
        RgvkEntity selectedItem = existsRgvkTable.getSelectionModel().getSelectedItem();

        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                rgvkData.remove(selectedIndex);
                databaseWorker.deleteRgvk(selectedItem);
                existsRgvkTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditButton() {
        this.selectedIndex = existsRgvkTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            this.selectedItem = existsRgvkTable.getSelectionModel().getSelectedItem();
            newRgvkTextField.setText(selectedItem.getName());
        }
    }

    @FXML
    private void handleSaveButton() {
        if (this.selectedItem == null) {
            selectedItem = new RgvkEntity(newRgvkTextField.getText());
            rgvkData.add(selectedItem);
        } else {
            selectedItem.setName(newRgvkTextField.getText());
            rgvkData.set(selectedIndex, selectedItem);
        }
        databaseWorker.saveRgvk(selectedItem);
        selectedItem = null;
        existsRgvkTable.refresh();
        newRgvkTextField.clear();
    }
}


package com.module.controllers.referencebook;

import com.module.database.DatabaseWorker;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.RankEntity;
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
public class NewRankDialogController {

    @FXML
    private TableView<RankEntity> existsRanksTable;
    @FXML
    private TableColumn<RankEntity, String> ranksColumn;
    @FXML
    private TextField newRankTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private DatabaseWorker databaseWorker;

    private ObservableList<RankEntity> rankData = FXCollections.observableArrayList();

    private Stage dialogStage;
    private RankEntity selectedItem;
    private int selectedIndex;

    @FXML
    public void initialize() {
        ranksColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        existsRanksTable.setItems(rankData);

        existsRanksTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            } else {
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });
        newRankTextField.textProperty().addListener((observable, oldValue, newValue) -> {
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
        int selectedIndex = existsRanksTable.getSelectionModel().getSelectedIndex();
        RankEntity selectedItem = existsRanksTable.getSelectionModel().getSelectedItem();

        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                rankData.remove(selectedIndex);
                databaseWorker.deleteRank(selectedItem);
                existsRanksTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditButton() {
        this.selectedIndex = existsRanksTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            this.selectedItem = existsRanksTable.getSelectionModel().getSelectedItem();
            newRankTextField.setText(selectedItem.getName());
        }
    }

    @FXML
    private void handleSaveButton() {
        if (this.selectedItem == null) {
            selectedItem = new RankEntity(newRankTextField.getText());
            rankData.add(selectedItem);
        } else {
            selectedItem.setName(newRankTextField.getText());
            rankData.set(selectedIndex, selectedItem);
        }
        databaseWorker.saveRank(selectedItem);
        selectedItem = null;
        newRankTextField.clear();
        existsRanksTable.refresh();
    }
}


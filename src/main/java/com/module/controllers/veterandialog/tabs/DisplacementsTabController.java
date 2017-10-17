package com.module.controllers.veterandialog.tabs;

import com.module.controllers.veterandialog.tables.AddDisplacementController;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.DisplacementEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@Component
public class DisplacementsTabController {

    @FXML
    private Tab displacementsTab;
    @FXML
    private TableView<DisplacementEntity> displacementTable;
    @FXML
    private TableColumn<DisplacementEntity, LocalDate> arrivedDateColumn;
    @FXML
    private TableColumn<DisplacementEntity, String> arrivedPlaceColumn;
    @FXML
    private TableColumn<DisplacementEntity, LocalDate> decreasedDateColumn;
    @FXML
    private TableColumn<DisplacementEntity, String> decreasedPlaceColumn;
    @FXML
    private Button addDisplacementButton;
    @FXML
    private Button editDisplacementButton;
    @FXML
    private Button deleteDisplacementButton;

    private Stage dialogStage;

    private ObservableList<DisplacementEntity> displacementData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        arrivedDateColumn.setCellValueFactory(new PropertyValueFactory<>("arrivedDate"));
        arrivedPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("arrivedPlace"));
        decreasedDateColumn.setCellValueFactory(new PropertyValueFactory<>("decreasedDate"));
        decreasedPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("decreasedPlace"));
        arrivedDateColumn.setCellFactory(getTableColumnTableCellCallback());
        displacementData.clear();
    }

    private Callback<TableColumn<DisplacementEntity, LocalDate>, TableCell<DisplacementEntity, LocalDate>> getTableColumnTableCellCallback() {
        return (TableColumn<DisplacementEntity, LocalDate> column) -> new TableCell<DisplacementEntity, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                }
            }
        };
    }

    @FXML
    private void handleAddDisplacement() {
        DisplacementEntity displacementEntity = new DisplacementEntity();
        boolean addButtonClicked = showAddDisplacementDialog(displacementEntity);
        if (addButtonClicked) {
            displacementData.add(displacementEntity);
            displacementTable.refresh();
        }
    }

    @FXML
    private void handleDeleteDisplacement() {
        int selectedIndex = displacementTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                displacementData.remove(selectedIndex);
                displacementTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditDisplacement() {
        DisplacementEntity displacementEntity = displacementTable.getSelectionModel().getSelectedItem();
        int selectedIndex = displacementTable.getSelectionModel().getSelectedIndex();
        if (displacementEntity != null) {
            boolean addButtonClicked = showAddDisplacementDialog(displacementEntity);
            if (addButtonClicked) {
                displacementData.set(selectedIndex, displacementEntity);
                displacementTable.refresh();
            }
        }
    }

    private boolean showAddDisplacementDialog(DisplacementEntity displacementEntity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/veterandialog/veterantabledialogs/AddDisplacementDialog.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(dialogStage);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            AddDisplacementController controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setDisplacementEntity(displacementEntity);
            stage.showAndWait();
            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

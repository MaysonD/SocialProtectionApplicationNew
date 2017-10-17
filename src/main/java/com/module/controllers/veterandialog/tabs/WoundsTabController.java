package com.module.controllers.veterandialog.tabs;

import com.module.controllers.veterandialog.tables.AddWoundController;
import com.module.database.DatabaseWorker;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.VeteranWoundEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@Component
public class WoundsTabController {

    @Autowired
    private ConfigurableApplicationContext springContext;

    @FXML
    private Tab woundsTab;
    @FXML
    private TableView<VeteranWoundEntity> woundsTable;
    @FXML
    private TableColumn<VeteranWoundEntity, String> typeOfWoundColumn;
    @FXML
    private TableColumn<VeteranWoundEntity, LocalDate> dateOfWoundColumn;
    @FXML
    private TableColumn<VeteranWoundEntity, String> disabilityColumn;
    @FXML
    private Button addWoundButton;
    @FXML
    private Button editWoundButton;
    @FXML
    private Button deleteWoundButton;

    private Stage dialogStage;

    private ObservableList<VeteranWoundEntity> woundsData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        typeOfWoundColumn.setCellValueFactory(new PropertyValueFactory<>("woundType"));
        dateOfWoundColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        disabilityColumn.setCellValueFactory(new PropertyValueFactory<>("woundDisability"));
        dateOfWoundColumn.setCellFactory(getTableColumnTableCellCallback());
        woundsData.clear();
    }

    private Callback<TableColumn<VeteranWoundEntity, LocalDate>, TableCell<VeteranWoundEntity, LocalDate>> getTableColumnTableCellCallback() {
        return (TableColumn<VeteranWoundEntity, LocalDate> column) -> new TableCell<VeteranWoundEntity, LocalDate>() {
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
    private void handleAddWound() {
        VeteranWoundEntity woundEntity = new VeteranWoundEntity();
        boolean addButtonClicked = showAddWoundDialog(woundEntity);
        if (addButtonClicked) {
            woundsData.add(woundEntity);
            woundsTable.refresh();
        }
    }

    @FXML
    private void handleDeleteWound() {
        int selectedIndex = woundsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                woundsData.remove(selectedIndex);
                woundsTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditWound() {
        VeteranWoundEntity woundEntity = woundsTable.getSelectionModel().getSelectedItem();
        int selectedIndex = woundsTable.getSelectionModel().getSelectedIndex();
        if (woundEntity != null) {
            boolean addButtonClicked = showAddWoundDialog(woundEntity);
            if (addButtonClicked) {
                woundsData.set(selectedIndex, woundEntity);
                woundsTable.refresh();
            }
        }
    }

    private boolean showAddWoundDialog(VeteranWoundEntity woundEntity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/veterandialog/veterantabledialogs/AddWoundDialog.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(dialogStage);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AddWoundController controller = loader.getController();
            DatabaseWorker databaseWorker = springContext.getBean(DatabaseWorker.class);
            controller.getTypeEntities().addAll(databaseWorker.getWoundTypes());
            controller.getDisabilityEntities().addAll(databaseWorker.getWoundDisabilities());
            controller.setDialogStage(stage);
            controller.setWoundEntity(woundEntity);
            stage.showAndWait();
            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

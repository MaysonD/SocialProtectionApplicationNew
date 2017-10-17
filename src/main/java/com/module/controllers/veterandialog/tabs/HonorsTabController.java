package com.module.controllers.veterandialog.tabs;

import com.module.controllers.veterandialog.tables.AddHonorController;
import com.module.database.DatabaseWorker;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.VeteranHonorEntity;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@Component
public class HonorsTabController {

    @Autowired
    private ConfigurableApplicationContext springContext;

    @FXML
    private Tab honorsTab;
    @FXML
    private TableView<VeteranHonorEntity> honorsTable;
    @FXML
    private TableColumn<VeteranHonorEntity, String> nameOfHonorColumn;
    @FXML
    private TableColumn<VeteranHonorEntity, LocalDate> dateOfReceivingColumn;
    @FXML
    private TableColumn<VeteranHonorEntity, String> orderColumn;
    @FXML
    private Button addHonorButton;
    @FXML
    private Button editHonorButton;
    @FXML
    private Button deleteHonorButton;

    private Stage dialogStage;

    private ObservableList<VeteranHonorEntity> honorsData = FXCollections.observableArrayList();

    private Callback<TableColumn<VeteranHonorEntity, LocalDate>, TableCell<VeteranHonorEntity, LocalDate>> getTableColumnTableCellCallback() {
        return (TableColumn<VeteranHonorEntity, LocalDate> column) -> new TableCell<VeteranHonorEntity, LocalDate>() {
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
    private void handleAddHonor() {
        VeteranHonorEntity veteranHonorEntity = new VeteranHonorEntity();
        boolean addButtonClicked = showAddHonorDialog(veteranHonorEntity);
        if (addButtonClicked) {
            honorsData.add(veteranHonorEntity);
            honorsTable.refresh();
        }
    }

    @FXML
    private void handleDeleteHonor() {
        int selectedIndex = honorsTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                honorsData.remove(selectedIndex);
                honorsTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditHonor() {
        VeteranHonorEntity veteranHonorEntity = honorsTable.getSelectionModel().getSelectedItem();
        int selectedIndex = honorsTable.getSelectionModel().getSelectedIndex();
        if (veteranHonorEntity != null) {
            boolean addButtonClicked = showAddHonorDialog(veteranHonorEntity);
            if (addButtonClicked) {
                honorsData.set(selectedIndex, veteranHonorEntity);
                honorsTable.refresh();
            }
        }
    }

    @FXML
    private void initialize() {
        nameOfHonorColumn.setCellValueFactory(new PropertyValueFactory<>("honor"));
        dateOfReceivingColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfReceiving"));
        orderColumn.setCellValueFactory(new PropertyValueFactory<>("decree"));
        dateOfReceivingColumn.setCellFactory(getTableColumnTableCellCallback());
        honorsData.clear();
    }

    private boolean showAddHonorDialog(VeteranHonorEntity veteranHonorEntity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/veterandialog/veterantabledialogs/AddHonorDialog.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(dialogStage);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            AddHonorController controller = loader.getController();
            DatabaseWorker databaseWorker = springContext.getBean(DatabaseWorker.class);
            controller.getHonorEntities().addAll(databaseWorker.getHonors());
            controller.setDialogStage(stage);
            controller.setHonorEntity(veteranHonorEntity);
            stage.showAndWait();
            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

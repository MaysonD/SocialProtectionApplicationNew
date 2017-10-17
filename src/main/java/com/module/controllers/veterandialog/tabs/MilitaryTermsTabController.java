package com.module.controllers.veterandialog.tabs;

import com.module.controllers.veterandialog.tables.AddMilitaryTermController;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.MilitaryTermEntity;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@Component
public class MilitaryTermsTabController {

    @FXML
    private Tab militaryTermsTab;
    @FXML
    private TableView<MilitaryTermEntity> militaryTermTable;
    @FXML
    private TableColumn<MilitaryTermEntity, String> militaryUnitColumn;
    @FXML
    private TableColumn<MilitaryTermEntity, String> countryOfMilitaryServiceColumn;
    @FXML
    private TableColumn<MilitaryTermEntity, String> localityOfMilitaryServiceColumn;
    @FXML
    private TableColumn<MilitaryTermEntity, LocalDate> startOfMilitaryServiceColumn;
    @FXML
    private TableColumn<MilitaryTermEntity, LocalDate> endOfMilitaryServiceColumn;
    @FXML
    private Button addMilitaryTermButton;
    @FXML
    private Button editMilitaryTermButton;
    @FXML
    private Button deleteMilitaryTermButton;

    private Stage dialogStage;

    private ObservableList<MilitaryTermEntity> militaryTermData = FXCollections.observableArrayList();

    private Callback<TableColumn<MilitaryTermEntity, LocalDate>, TableCell<MilitaryTermEntity, LocalDate>> getTableColumnTableCellCallback() {
        return (TableColumn<MilitaryTermEntity, LocalDate> column) -> new TableCell<MilitaryTermEntity, LocalDate>() {
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
    private void handleAddMilitaryTerm() {
        MilitaryTermEntity militaryTermEntity = new MilitaryTermEntity();
        boolean addButtonClicked = showAddMilitaryTermDialog(militaryTermEntity);
        if (addButtonClicked) {
            militaryTermData.add(militaryTermEntity);
            militaryTermTable.refresh();
        }
    }

    @FXML
    private void handleDeleteMilitaryTerm() {
        int selectedIndex = militaryTermTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                militaryTermData.remove(selectedIndex);
                militaryTermTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditMilitaryTerm() {
        MilitaryTermEntity militaryTermEntity = militaryTermTable.getSelectionModel().getSelectedItem();
        int selectedIndex = militaryTermTable.getSelectionModel().getSelectedIndex();
        if (militaryTermEntity != null) {
            boolean addButtonClicked = showAddMilitaryTermDialog(militaryTermEntity);
            if (addButtonClicked) {
                militaryTermData.set(selectedIndex, militaryTermEntity);
                militaryTermTable.refresh();
            }
        }
    }

    @FXML
    private void initialize() {
        militaryUnitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        countryOfMilitaryServiceColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        localityOfMilitaryServiceColumn.setCellValueFactory(new PropertyValueFactory<>("locality"));
        startOfMilitaryServiceColumn.setCellValueFactory(new PropertyValueFactory<>("startOfMilitaryService"));
        endOfMilitaryServiceColumn.setCellValueFactory(new PropertyValueFactory<>("endOfMilitaryService"));
        startOfMilitaryServiceColumn.setCellFactory(getTableColumnTableCellCallback());
        endOfMilitaryServiceColumn.setCellFactory(getTableColumnTableCellCallback());
        militaryTermData.clear();
    }

    private boolean showAddMilitaryTermDialog(MilitaryTermEntity militaryTermEntity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/veterandialog/veterantabledialogs/AddMilitaryTermDialog.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(dialogStage);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            AddMilitaryTermController controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setMilitaryTermEntity(militaryTermEntity);
            stage.showAndWait();
            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

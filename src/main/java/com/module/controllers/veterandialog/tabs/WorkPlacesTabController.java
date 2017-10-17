package com.module.controllers.veterandialog.tabs;

import com.module.controllers.veterandialog.tables.AddWorkPlaceController;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.WorkPlaceEntity;
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
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Data
@Component
public class WorkPlacesTabController {

    @FXML
    private Tab workPlacesTab;
    @FXML
    private TableView<WorkPlaceEntity> workPlaceTable;
    @FXML
    private TableColumn<WorkPlaceEntity, String> localityOfWorkColumn;
    @FXML
    private TableColumn<WorkPlaceEntity, String> workOrganizationColumn;
    @FXML
    private TableColumn<WorkPlaceEntity, String> workPositionColumn;
    @FXML
    private TableColumn<WorkPlaceEntity, String> numberOfDepartmentColumn;
    @FXML
    private Button addWorkPlaceButton;
    @FXML
    private Button editWorkPlaceButton;
    @FXML
    private Button deleteWorkPlaceButton;

    private Stage dialogStage;

    private ObservableList<WorkPlaceEntity> workPlacesData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        localityOfWorkColumn.setCellValueFactory(new PropertyValueFactory<>("locality"));
        workOrganizationColumn.setCellValueFactory(new PropertyValueFactory<>("organization"));
        workPositionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        numberOfDepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("hrNumber"));
        workPlacesData.clear();
    }

    @FXML
    private void handleAddWorkPlace() {
        WorkPlaceEntity workPlaceEntity = new WorkPlaceEntity();
        boolean addButtonClicked = showAddWorkPlaceDialog(workPlaceEntity);
        if (addButtonClicked) {
            workPlacesData.add(workPlaceEntity);
            workPlaceTable.refresh();
        }
    }

    @FXML
    private void handleDeleteWorkPlace() {
        int selectedIndex = workPlaceTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                workPlacesData.remove(selectedIndex);
                workPlaceTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditWorkPlace() {
        WorkPlaceEntity workPlaceEntity = workPlaceTable.getSelectionModel().getSelectedItem();
        int selectedIndex = workPlaceTable.getSelectionModel().getSelectedIndex();
        if (workPlaceEntity != null) {
            boolean addButtonClicked = showAddWorkPlaceDialog(workPlaceEntity);
            if (addButtonClicked) {
                workPlacesData.set(selectedIndex, workPlaceEntity);
                workPlaceTable.refresh();
            }
        }
    }

    private boolean showAddWorkPlaceDialog(WorkPlaceEntity workPlaceEntity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/veterandialog/veterantabledialogs/AddWorkPlaceDialog.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(dialogStage);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            AddWorkPlaceController controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setWorkPlaceEntity(workPlaceEntity);
            stage.showAndWait();
            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

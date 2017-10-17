package com.module.controllers.veterandialog.tabs;

import com.module.controllers.veterandialog.tables.AddHelpController;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.HelpEntity;
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
public class HelpTabController {

    @FXML
    private Tab helpTab;
    @FXML
    private TableView<HelpEntity> helpTable;
    @FXML
    private TableColumn<HelpEntity, String> organizationNameColumn;
    @FXML
    private TableColumn<HelpEntity, String> typeOfHelpColumn;
    @FXML
    private TableColumn<HelpEntity, LocalDate> helpDateColumn;
    @FXML
    private TableColumn<HelpEntity, String> baseToHelpColumn;
    @FXML
    private Button addHelpButton;
    @FXML
    private Button editHelpButton;
    @FXML
    private Button deleteHelpButton;

    private Stage dialogStage;

    private ObservableList<HelpEntity> helpData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        organizationNameColumn.setCellValueFactory(new PropertyValueFactory<>("organizationName"));
        typeOfHelpColumn.setCellValueFactory(new PropertyValueFactory<>("helpType"));
        helpDateColumn.setCellValueFactory(new PropertyValueFactory<>("helpDate"));
        baseToHelpColumn.setCellValueFactory(new PropertyValueFactory<>("baseToHelp"));
        helpDateColumn.setCellFactory(getTableColumnTableCellCallback());
        helpData.clear();
    }

    private Callback<TableColumn<HelpEntity, LocalDate>, TableCell<HelpEntity, LocalDate>> getTableColumnTableCellCallback() {
        return (TableColumn<HelpEntity, LocalDate> column) -> new TableCell<HelpEntity, LocalDate>() {
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
    private void handleAddHelp() {
        HelpEntity helpEntity = new HelpEntity();
        boolean addButtonClicked = showAddHelpDialog(helpEntity);
        if (addButtonClicked) {
            helpData.add(helpEntity);
            helpTable.refresh();
        }
    }

    @FXML
    private void handleDeleteHelp() {
        int selectedIndex = helpTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                helpData.remove(selectedIndex);
                helpTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditHelp() {
        HelpEntity helpEntity = helpTable.getSelectionModel().getSelectedItem();
        int selectedIndex = helpTable.getSelectionModel().getSelectedIndex();
        if (helpEntity != null) {
            boolean addButtonClicked = showAddHelpDialog(helpEntity);
            if (addButtonClicked) {
                helpData.set(selectedIndex, helpEntity);
                helpTable.refresh();
            }
        }
    }

    private boolean showAddHelpDialog(HelpEntity helpEntity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/veterandialog/veterantabledialogs/AddHelpDialog.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(dialogStage);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            AddHelpController controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setHelpEntity(helpEntity);
            stage.showAndWait();
            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

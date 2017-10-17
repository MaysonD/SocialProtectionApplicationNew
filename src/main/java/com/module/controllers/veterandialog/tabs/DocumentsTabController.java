package com.module.controllers.veterandialog.tabs;

import com.module.controllers.veterandialog.tables.AddDocumentController;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.DocumentEntity;
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
public class DocumentsTabController {

    @FXML
    private Tab documentsTab;
    @FXML
    private TableView<DocumentEntity> documentsTable;
    @FXML
    private TableColumn<DocumentEntity, String> documentNameColumn;
    @FXML
    private TableColumn<DocumentEntity, String> seriesOfDocumentColumn;
    @FXML
    private TableColumn<DocumentEntity, String> numberOfDocumentColumn;
    @FXML
    private TableColumn<DocumentEntity, String> documentIssuedByColumn;
    @FXML
    private TableColumn<DocumentEntity, LocalDate> dateOfIssueDocumentColumn;
    @FXML
    private Button addDocumentButton;
    @FXML
    private Button editDocumentButton;
    @FXML
    private Button deleteDocumentButton;

    private Stage dialogStage;

    private ObservableList<DocumentEntity> documentsData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        documentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        seriesOfDocumentColumn.setCellValueFactory(new PropertyValueFactory<>("series"));
        numberOfDocumentColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        documentIssuedByColumn.setCellValueFactory(new PropertyValueFactory<>("issuedBy"));
        dateOfIssueDocumentColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateOfIssueDocumentColumn.setCellFactory(getTableColumnTableCellCallback());
        documentsData.clear();
    }

    private Callback<TableColumn<DocumentEntity, LocalDate>, TableCell<DocumentEntity, LocalDate>> getTableColumnTableCellCallback() {
        return (TableColumn<DocumentEntity, LocalDate> column) -> new TableCell<DocumentEntity, LocalDate>() {
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
    private void handleAddDocument() {
        DocumentEntity documentEntity = new DocumentEntity();
        boolean addButtonClicked = showAddDocumentDialog(documentEntity);
        if (addButtonClicked) {
            documentsData.add(documentEntity);
            documentsTable.refresh();
        }
    }

    @FXML
    private void handleDeleteDocument() {
        int selectedIndex = documentsTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                documentsData.remove(selectedIndex);
                documentsTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditDocument() {
        DocumentEntity documentEntity = documentsTable.getSelectionModel().getSelectedItem();
        int selectedIndex = documentsTable.getSelectionModel().getSelectedIndex();
        if (documentEntity != null) {
            boolean addButtonClicked = showAddDocumentDialog(documentEntity);
            if (addButtonClicked) {
                documentsData.set(selectedIndex, documentEntity);
                documentsTable.refresh();
            }
        }
    }

    private boolean showAddDocumentDialog(DocumentEntity documentEntity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/veterandialog/veterantabledialogs/AddDocumentDialog.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(dialogStage);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AddDocumentController controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setDocumentEntity(documentEntity);
            stage.showAndWait();
            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

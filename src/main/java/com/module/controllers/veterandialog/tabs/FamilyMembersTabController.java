package com.module.controllers.veterandialog.tabs;

import com.module.controllers.veterandialog.tables.AddFamilyMemberController;
import com.module.helpers.CustomAlertCreator;
import com.module.model.entity.FamilyMemberEntity;
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
public class FamilyMembersTabController {

    @FXML
    private Tab familyMembersTab;
    @FXML
    private TableView<FamilyMemberEntity> familyMembersTable;
    @FXML
    private TableColumn<FamilyMemberEntity, String> familyMembersRelationDegreeColumn;
    @FXML
    private TableColumn<FamilyMemberEntity, String> familyMembersFullNameColumn;
    @FXML
    private TableColumn<FamilyMemberEntity, LocalDate> familyMembersDateOfBirthColumn;
    @FXML
    private TableColumn<FamilyMemberEntity, String> familyMembersAddressColumn;
    @FXML
    private TableColumn<FamilyMemberEntity, String> familyMembersPhoneNumberColumn;
    @FXML
    private Button addFamilyMemberButton;
    @FXML
    private Button editFamilyMemberButton;
    @FXML
    private Button deleteFamilyMemberButton;

    private Stage dialogStage;

    private ObservableList<FamilyMemberEntity> familyMembersData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        familyMembersRelationDegreeColumn.setCellValueFactory(new PropertyValueFactory<>("relationDegree"));
        familyMembersFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        familyMembersDateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        familyMembersAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        familyMembersPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        familyMembersDateOfBirthColumn.setCellFactory(getTableColumnTableCellCallback());
        familyMembersData.clear();
    }

    private Callback<TableColumn<FamilyMemberEntity, LocalDate>, TableCell<FamilyMemberEntity, LocalDate>> getTableColumnTableCellCallback() {
        return (TableColumn<FamilyMemberEntity, LocalDate> column) -> new TableCell<FamilyMemberEntity, LocalDate>() {
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
    private void handleAddFamilyMember() {
        FamilyMemberEntity familyMemberEntity = new FamilyMemberEntity();
        boolean addButtonClicked = showAddFamilyMemberDialog(familyMemberEntity);
        if (addButtonClicked) {
            familyMembersData.add(familyMemberEntity);
            familyMembersTable.refresh();
        }
    }

    @FXML
    private void handleDeleteFamilyMember() {
        int selectedIndex = familyMembersTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            CustomAlertCreator customAlertCreator = new CustomAlertCreator();
            Optional<ButtonType> result = customAlertCreator.createDeleteConfirmationAlert().showAndWait();

            if (result.get() == customAlertCreator.getButtonTypeOk()) {
                familyMembersData.remove(selectedIndex);
                familyMembersTable.refresh();
            }
        }
    }

    @FXML
    private void handleEditFamilyMember() {
        FamilyMemberEntity familyMemberEntity = familyMembersTable.getSelectionModel().getSelectedItem();
        int selectedIndex = familyMembersTable.getSelectionModel().getSelectedIndex();
        if (familyMemberEntity != null) {
            boolean addButtonClicked = showAddFamilyMemberDialog(familyMemberEntity);
            if (addButtonClicked) {
                familyMembersData.set(selectedIndex, familyMemberEntity);
                familyMembersTable.refresh();
            }
        }
    }

    private boolean showAddFamilyMemberDialog(FamilyMemberEntity familyMemberEntity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/veterandialog/veterantabledialogs/AddFamilyMemberDialog.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(dialogStage);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AddFamilyMemberController controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setFamilyMemberEntity(familyMemberEntity);
            stage.showAndWait();
            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

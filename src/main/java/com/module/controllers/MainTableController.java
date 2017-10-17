package com.module.controllers;

import com.module.model.entity.VeteranEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

@Data
@Component
public class MainTableController {

    @FXML
    private TableColumn<VeteranEntity, String> addressColumn;
    @FXML
    private TableColumn<VeteranEntity, String> caseNumberColumn;
    @FXML
    private TableColumn<VeteranEntity, String> categoryColumn;
    @FXML
    private TableColumn<VeteranEntity, LocalDate> dateOfBirthColumn;
    @FXML
    private TableColumn<VeteranEntity, String> districtColumn;
    @FXML
    private TableColumn<VeteranEntity, String> firstNameColumn;
    @FXML
    private TableColumn<VeteranEntity, String> lastNameColumn;
    @FXML
    private TableView<VeteranEntity> mainTable;
    @FXML
    private TableColumn<VeteranEntity, String> middleNameColumn;
    @FXML
    private TableColumn<VeteranEntity, String> militaryRankColumn;

    private ObservableList<VeteranEntity> veteransData = FXCollections.observableArrayList();

    public void addToTable(VeteranEntity veteranEntity) {
        veteransData.add(veteranEntity);
        mainTable.refresh();
    }

    public LinkedList<VeteranEntity> getSelectedVeterans() {
        return new LinkedList<>(mainTable.getSelectionModel().getSelectedItems());
    }

    public void refreshTable() {
        mainTable.refresh();
    }

    @FXML
    private void initialize() {
        caseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("caseNumber"));
        militaryRankColumn.setCellValueFactory(new PropertyValueFactory<>("militaryRank"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));


        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        dateOfBirthColumn.setCellFactory((TableColumn<VeteranEntity, LocalDate> column) -> new TableCell<VeteranEntity, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                }
            }
        });

        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        districtColumn.setCellValueFactory(new PropertyValueFactory<>("district"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        mainTable.setItems(veteransData);
        mainTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    VeteranEntity getSelectedVeteran() {
        return mainTable.getSelectionModel().getSelectedItem();
    }
}

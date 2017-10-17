package com.module.controllers;


import com.module.controllers.excel.ExportInExcelDialogController;
import com.module.controllers.referencebook.*;
import com.module.database.DatabaseWorker;
import com.module.database.QueryBuilder;
import com.module.helpers.FileLoader;
import com.module.helpers.WordDocumentGenerator;
import com.module.model.entity.VeteranEntity;
import com.module.xml.DatabaseChecker;
import com.module.xml.VeteransExchange;
import com.module.xml.XmlExporter;
import com.module.xml.XmlImporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class HeaderController {

    @Autowired
    private DatabaseWorker databaseWorker;
    @Autowired
    private DatabaseChecker databaseChecker;
    @Autowired
    private ConfigurableApplicationContext springContext;

    @FXML
    private ComboBox<String> selectFieldForSearch;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchTextField;

    private Stage getRootManagerPrimaryStage() {
        return springContext.getBean(RootManager.class).getPrimaryStage();
    }

    @FXML
    private void handleAddHonorMenuItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/menudialogs/referencebook/NewHonorDialog.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(getRootManagerPrimaryStage());
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            NewHonorDialogController controller = loader.getController();
            controller.getHonorData().addAll(databaseWorker.getHonors());
            controller.setDatabaseWorker(databaseWorker);
            controller.setDialogStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddRankMenuItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/menudialogs/referencebook/NewRankDialog.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(getRootManagerPrimaryStage());
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            NewRankDialogController controller = loader.getController();
            controller.setDatabaseWorker(databaseWorker);
            controller.getRankData().addAll(databaseWorker.getRanks());
            controller.setDialogStage(stage);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddRgvkMenuItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/menudialogs/referencebook/NewRgvkDialog.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(getRootManagerPrimaryStage());
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            NewRGVKDialogController controller = loader.getController();
            controller.setDatabaseWorker(databaseWorker);
            controller.getRgvkData().addAll(databaseWorker.getRgvk());
            controller.setDialogStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddWoundDisabilityMenuItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/menudialogs/referencebook/NewWoundDisabilityDialog.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(getRootManagerPrimaryStage());
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            NewWoundDisabilityDialogController controller = loader.getController();
            controller.setDatabaseWorker(databaseWorker);
            controller.getWoundDisabilityData().addAll(databaseWorker.getWoundDisabilities());
            controller.setDialogStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddWoundTypeMenuItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/menudialogs/referencebook/NewWoundTypeDialog.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(getRootManagerPrimaryStage());
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            NewWoundTypeDialogController controller = loader.getController();
            controller.setDatabaseWorker(databaseWorker);
            controller.getWoundTypeData().addAll(databaseWorker.getWoundTypes());
            controller.setDialogStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExportInExcelMenuItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/menudialogs/excel/ExportInExcelDialog.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(getRootManagerPrimaryStage());
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            ExportInExcelDialogController controller = loader.getController();
            controller.setDatabaseWorker(databaseWorker);
            controller.setFiltersEntities(databaseWorker.filterVeterans(
                    new QueryBuilder().prepareSelectQuery(springContext.getBean(FiltersController.class).getFiltersMap(), -1)));
            controller.setDialogStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExportInXML() throws IOException {
        File file = FileLoader.saveXML();
        if (file != null) {
            final Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    VeteransExchange veteransExchange = new VeteransExchange();
                    veteransExchange.setVeterans(databaseWorker.getVeterans());
                    XmlExporter.exportDatabaseData(veteransExchange, file);
                    return null;
                }
            };
            ProgressController progressController = new ProgressController();
            progressController.showProgressBar(getRootManagerPrimaryStage(), task);
        }
    }

    @FXML
    private void handleImportInXML() throws IOException {
        File file = FileLoader.openXML();
        if (file != null) {
            final Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    XmlImporter.importDatabaseData(databaseWorker, databaseChecker, file);
                    return null;
                }
            };
            ProgressController progressController = new ProgressController();
            progressController.showProgressBar(getRootManagerPrimaryStage(), task);
        }
        springContext.getBean(FooterController.class).setPaginationProperties();
    }

    @FXML
    private void handleSaveToWord() {
        VeteranEntity veteranEntity = springContext.getBean(RootManager.class).getMainTableController().getSelectedVeteran();
        if (veteranEntity != null)
            WordDocumentGenerator.generateWordReport(veteranEntity, FileLoader.saveWord());
    }

    @FXML
    private void handleSearchButton() {
        String search = searchTextField.getText();
        if (selectFieldForSearch.getValue().equals("Номер дела"))
            springContext.getBean(FiltersController.class).getFiltersMap().put("CaseNumber", search);
        if (selectFieldForSearch.getValue().equals("Фамилия"))
            springContext.getBean(FiltersController.class).getFiltersMap().put("SecondName", search);
        if (selectFieldForSearch.getValue().equals("Серия документа"))
            springContext.getBean(FiltersController.class).getFiltersMap().put("DocumentSeries", search);
        springContext.getBean(FooterController.class).setPaginationProperties();
    }

    @FXML
    private void initialize() {
        List<String> list = new LinkedList<>();
        list.add("Номер дела");
        list.add("Фамилия");
        list.add("Серия документа");
        ObservableList<String> checkboxData = FXCollections.observableArrayList(list);
        selectFieldForSearch.setItems(checkboxData);

        selectFieldForSearch.setOnAction((event) -> {
            if (!selectFieldForSearch.getSelectionModel().isEmpty())
                searchTextField.setDisable(false);
            else
                searchTextField.setDisable(true);
        });

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                selectFieldForSearch.getSelectionModel().clearSelection();
                searchButton.setDisable(true);
                springContext.getBean(FiltersController.class).getFiltersMap().remove("CaseNumber");
                springContext.getBean(FiltersController.class).getFiltersMap().remove("SecondName");
                springContext.getBean(FiltersController.class).getFiltersMap().remove("DocumentSeries");
                springContext.getBean(FooterController.class).setPaginationProperties();
            } else {
                searchButton.setDisable(false);
            }
        });
    }
}

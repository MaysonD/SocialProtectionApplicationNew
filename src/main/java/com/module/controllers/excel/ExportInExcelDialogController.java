package com.module.controllers.excel;

import com.module.controllers.ProgressController;
import com.module.database.DatabaseWorker;
import com.module.helpers.ExcelTableGenerator;
import com.module.helpers.FileLoader;
import com.module.model.entity.VeteranEntity;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@Component
public class ExportInExcelDialogController {

    @FXML
    private CheckBox caseNumber;
    @FXML
    private CheckBox militaryRank;
    @FXML
    private CheckBox firstName;
    @FXML
    private CheckBox secondName;
    @FXML
    private CheckBox middleName;
    @FXML
    private CheckBox dateOfBirth;
    @FXML
    private CheckBox category;
    @FXML
    private CheckBox subcategory;
    @FXML
    private CheckBox position;
    @FXML
    private CheckBox militarySubdivision;
    @FXML
    private CheckBox district;
    @FXML
    private CheckBox registrationAddress;
    @FXML
    private CheckBox address;
    @FXML
    private CheckBox phoneNumber;
    @FXML
    private CheckBox regionalExecutiveCommittee;
    @FXML
    private CheckBox villageExecutiveCommittee;
    @FXML
    private CheckBox marchingOrganization;
    @FXML
    private Button exportButton;
    @FXML
    private Button closeButton;
    @FXML
    private AnchorPane excelDialogRoot;

    private Stage dialogStage;

    private DatabaseWorker databaseWorker;

    private List<VeteranEntity> filtersEntities;

    private File file;

    @FXML
    public void handleExportButton() throws IOException {
        if (!getExportColumnNames().isEmpty()) {
            file = FileLoader.saveExcel();
            if (file != null) {
                final Task task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        ExcelTableGenerator tableGenerator = new ExcelTableGenerator();
                        List<List<String>> dataToExportInExcel = excelDateGenerator();
                        tableGenerator.writeIntoExcel(dataToExportInExcel, file);
                        return null;
                    }
                };
                ProgressController progressController = new ProgressController();
                progressController.showProgressBar(dialogStage, task);
            }
        }
    }

    @FXML
    public void initialize() {
    }

    private List<List<String>> excelDateGenerator() {
        List<List<String>> dataToExportInExcel = new LinkedList<>();
        if (!getExportColumnNames().isEmpty()) {
            dataToExportInExcel.add(getExportColumnNames());
            for (VeteranEntity entity : filtersEntities) {
                List<String> data = new ArrayList<>();
                if (caseNumber.isSelected())//
                    data.add(entity.getCaseNumber());
                if (militaryRank.isSelected())//
                    if (entity.getMilitaryRank() != null)
                        data.add(entity.getMilitaryRank().getName());
                    else data.add("-");
                if (firstName.isSelected())//
                    data.add(entity.getFirstName());
                if (secondName.isSelected())//
                    data.add(entity.getSecondName());
                if (middleName.isSelected())//
                    data.add(entity.getMiddleName());
                if (dateOfBirth.isSelected())
                    if (entity.getDateOfBirth() != null)
                        data.add(entity.getDateOfBirth().toString());
                    else data.add("-");
                if (category.isSelected())//
                    data.add(entity.getCategory().getName());
                if (subcategory.isSelected())
                    if (entity.getSubcategory() != null)
                        data.add(entity.getSubcategory().getName());
                    else data.add("-");
                if (position.isSelected())
                    if (entity.getPosition() != null)
                        data.add(entity.getPosition());
                    else data.add("-");
                if (militarySubdivision.isSelected())
                    if (entity.getSubdivision() != null)
                        data.add(entity.getSubdivision());
                    else data.add("-");
                if (district.isSelected())//
                    data.add(entity.getDistrict().getName());
                if (registrationAddress.isSelected())
                    if (entity.getRegistrationAddress() != null)
                        data.add(entity.getRegistrationAddress());
                    else data.add("-");
                if (address.isSelected())
                    if (entity.getAddress() != null)
                        data.add(entity.getAddress());
                    else data.add("-");
                if (phoneNumber.isSelected())
                    if (entity.getPhoneNumber() != null)
                        data.add(entity.getPhoneNumber());
                    else data.add("-");
                if (regionalExecutiveCommittee.isSelected())
                    if (entity.getRegionalExecutiveCommittee() != null)
                        data.add(entity.getRegionalExecutiveCommittee());
                    else data.add("-");
                if (villageExecutiveCommittee.isSelected())
                    if (entity.getVillageExecutiveCommittee() != null)
                        data.add(entity.getVillageExecutiveCommittee());
                if (marchingOrganization.isSelected())
                    if (entity.getMarchingOrganization() != null)
                        data.add(entity.getMarchingOrganization());
                    else data.add("-");
                dataToExportInExcel.add(data);
            }
        }
        return dataToExportInExcel;
    }

    private List<String> getExportColumnNames() {
        List<String> columnNames = new ArrayList<>();
        if (caseNumber.isSelected())//
            columnNames.add(caseNumber.getText());
        if (militaryRank.isSelected())//
            columnNames.add(militaryRank.getText());
        if (firstName.isSelected())//
            columnNames.add(firstName.getText());
        if (secondName.isSelected())//
            columnNames.add(secondName.getText());
        if (middleName.isSelected())//
            columnNames.add(middleName.getText());
        if (dateOfBirth.isSelected())
            columnNames.add(dateOfBirth.getText());
        if (category.isSelected())//
            columnNames.add(category.getText());
        if (subcategory.isSelected())
            columnNames.add(subcategory.getText());
        if (position.isSelected())
            columnNames.add(position.getText());
        if (militarySubdivision.isSelected())
            columnNames.add(militarySubdivision.getText());
        if (district.isSelected())//
            columnNames.add(district.getText());
        if (registrationAddress.isSelected())
            columnNames.add(registrationAddress.getText());
        if (address.isSelected())
            columnNames.add(address.getText());
        if (phoneNumber.isSelected())
            columnNames.add(phoneNumber.getText());
        if (regionalExecutiveCommittee.isSelected())
            columnNames.add(regionalExecutiveCommittee.getText());
        if (villageExecutiveCommittee.isSelected())
            columnNames.add(villageExecutiveCommittee.getText());
        if (marchingOrganization.isSelected())
            columnNames.add(marchingOrganization.getText());
        return columnNames;
    }

    @FXML
    private void handleCloseButton() {
        dialogStage.close();
    }

}

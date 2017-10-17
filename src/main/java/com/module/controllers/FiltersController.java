package com.module.controllers;

import com.module.database.DatabaseWorker;
import com.module.model.entity.CategoryEntity;
import com.module.model.entity.DistrictEntity;
import com.module.model.entity.HonorEntity;
import com.module.model.entity.SubcategoryEntity;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class FiltersController {

    @Autowired
    private DatabaseWorker databaseWorker;

    @FXML
    private CheckComboBox<CategoryEntity> categoryFilterField;
    @FXML
    private CheckComboBox<DistrictEntity> districtFilterField;
    @FXML
    private CheckComboBox<SubcategoryEntity> subcategoryFilterField;
    @FXML
    private CheckComboBox<HonorEntity> honorsFilterField;
    @FXML
    private TextField countryOfMilitaryServiceFilterField;
    @FXML
    private TextField localityOfMilitaryServiceFilterField;
    @FXML
    private TextField marchingOrganizationFilterField;
    @FXML
    private TextField militaryUnitFilterField;
    @FXML
    private TextField regionalExecutiveCommitteeFilterField;
    @FXML
    private TextField villageExecutiveCommitteeFilterField;
    @FXML
    private TextField woundTypeFilterField;
    @FXML
    private TextField woundDisabilityFilterField;
    @FXML
    private CheckBox isDeadCheckBox;
    @FXML
    private CheckBox aloneCheckBox;

    @Autowired
    private ConfigurableApplicationContext applicationContext;
    private Map<String, String> filtersMap = new LinkedHashMap<>();

    public void clearAllFilters() {
        districtFilterField.getCheckModel().clearChecks();
        subcategoryFilterField.getCheckModel().clearChecks();
        subcategoryFilterField.getItems().clear();
        honorsFilterField.getCheckModel().clearChecks();
        categoryFilterField.getCheckModel().clearChecks();
        militaryUnitFilterField.clear();
        countryOfMilitaryServiceFilterField.clear();
        localityOfMilitaryServiceFilterField.clear();
        woundTypeFilterField.clear();
        woundDisabilityFilterField.clear();
        marchingOrganizationFilterField.clear();
        villageExecutiveCommitteeFilterField.clear();
        regionalExecutiveCommitteeFilterField.clear();
        aloneCheckBox.setSelected(false);
        filtersMap.clear();

        if (isDeadCheckBox.isSelected())
            filtersMap.put("IsDead", String.valueOf(isDeadCheckBox.isSelected()));
    }

    public Map<String, String> getFiltersMap() {
        if (!districtFilterField.getCheckModel().getCheckedItems().isEmpty()) {
            List<String> districtNames = new LinkedList<>();
            for (DistrictEntity districtValue : districtFilterField.getCheckModel().getCheckedItems()) {
                districtNames.add(districtValue.getName());
            }
            String districtQuery = "district.name in ('" + String.join("', '", districtNames) + "')";
            filtersMap.put("DistrictName", districtQuery);
        }
        if (!honorsFilterField.getCheckModel().getCheckedItems().isEmpty()) {
            List<String> honorNames = new LinkedList<>();
            for (HonorEntity districtValue : honorsFilterField.getCheckModel().getCheckedItems()) {
                honorNames.add(districtValue.getName());
            }
            String honorsQuery = "hon.name in ('" + String.join("', '", honorNames) + "')";
            filtersMap.put("Honor", honorsQuery);
        }
        if (!categoryFilterField.getCheckModel().getCheckedItems().isEmpty()) {
            List<String> categoryNames = new LinkedList<>();
            for (CategoryEntity categoryValue : categoryFilterField.getCheckModel().getCheckedItems()) {
                categoryNames.add(categoryValue.getName());
            }
            String categoryQuery = "cat.name in ('" + String.join("', '", categoryNames) + "')";
            filtersMap.put("Category", categoryQuery);
        }
        if (!subcategoryFilterField.getCheckModel().getCheckedItems().isEmpty()) {
            List<String> subcategoryNames = new LinkedList<>();
            for (SubcategoryEntity subcategoryValue : subcategoryFilterField.getCheckModel().getCheckedItems()) {
                subcategoryNames.add(subcategoryValue.getName());
            }
            String subcategoryQuery = "sub.name in ('" + String.join("', '", subcategoryNames) + "')";
            filtersMap.put("Subcategory", subcategoryQuery);
        }
        if (!militaryUnitFilterField.getText().equals("")) {
            filtersMap.put("MilitaryUnit", militaryUnitFilterField.getText());
        }
        if (!countryOfMilitaryServiceFilterField.getText().equals("")) {
            filtersMap.put("MilitaryCountry", countryOfMilitaryServiceFilterField.getText());
        }
        if (!localityOfMilitaryServiceFilterField.getText().equals("")) {
            filtersMap.put("MilitaryLocality", localityOfMilitaryServiceFilterField.getText());
        }
        if (!woundTypeFilterField.getText().equals("")) {
            filtersMap.put("WoundType", woundTypeFilterField.getText());
        }
        if (!woundDisabilityFilterField.getText().equals("")) {
            filtersMap.put("WoundDisability", woundDisabilityFilterField.getText());
        }
        if (!marchingOrganizationFilterField.getText().equals("")) {
            filtersMap.put("MarchingOrganization", marchingOrganizationFilterField.getText());
        }
        if (!villageExecutiveCommitteeFilterField.getText().equals("")) {
            filtersMap.put("VillageExecutiveCommittee", villageExecutiveCommitteeFilterField.getText());
        }
        if (!regionalExecutiveCommitteeFilterField.getText().equals("")) {
            filtersMap.put("RegionalExecutiveCommittee", regionalExecutiveCommitteeFilterField.getText());
        }
        if (aloneCheckBox.isSelected()) {
            filtersMap.put("FamilyMembers", String.valueOf(aloneCheckBox.isSelected()));
        }

        return filtersMap;
    }

    @FXML
    private void handleIsDeadCheckbox() {
        filtersMap.put("IsDead", String.valueOf(isDeadCheckBox.isSelected()));
        applicationContext.getBean(FooterController.class).setPaginationProperties();
    }

    @FXML
    private void initialize() {
        TextFields.bindAutoCompletion(woundTypeFilterField,
                databaseWorker.getAutoCompleteList("wound_types", "type"));
        TextFields.bindAutoCompletion(woundDisabilityFilterField,
                databaseWorker.getAutoCompleteList("wound_disabilities", "disability"));
        TextFields.bindAutoCompletion(militaryUnitFilterField,
                databaseWorker.getAutoCompleteList("military_terms", "unit"));
        TextFields.bindAutoCompletion(countryOfMilitaryServiceFilterField,
                databaseWorker.getAutoCompleteList("military_terms", "country"));
        TextFields.bindAutoCompletion(localityOfMilitaryServiceFilterField,
                databaseWorker.getAutoCompleteList("military_terms", "locality"));
        TextFields.bindAutoCompletion(villageExecutiveCommitteeFilterField,
                databaseWorker.getAutoCompleteList("veterans", "village_executive_committee"));
        TextFields.bindAutoCompletion(regionalExecutiveCommitteeFilterField,
                databaseWorker.getAutoCompleteList("veterans", "regional_executive_committee"));
        TextFields.bindAutoCompletion(marchingOrganizationFilterField,
                databaseWorker.getAutoCompleteList("veterans", "marching_organization"));

        districtFilterField.getItems().setAll(databaseWorker.getDistricts());
        honorsFilterField.getItems().setAll(databaseWorker.getHonors());
        categoryFilterField.getItems().setAll(databaseWorker.getCategories());
        categoryFilterField.getCheckModel().getCheckedItems().addListener(new ListChangeListener<CategoryEntity>() {
            public void onChanged(Change<? extends CategoryEntity> c) {
                subcategoryFilterField.getItems().clear();
                if (!categoryFilterField.getCheckModel().getCheckedItems().isEmpty()) {
                    for (CategoryEntity categoryEntity : categoryFilterField.getCheckModel().getCheckedItems()) {
                        subcategoryFilterField.setDisable(false);
                        subcategoryFilterField.getItems().addAll(categoryEntity.getSubcategories());
                    }
                } else {
                    subcategoryFilterField.setDisable(true);
                    subcategoryFilterField.getCheckModel().clearChecks();
                    subcategoryFilterField.getItems().clear();
                }
            }
        });
    }
}

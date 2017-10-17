package com.module.controllers.veterandialog;

import com.module.database.DatabaseWorker;
import com.module.helpers.FileLoader;
import com.module.model.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class MainDataPaneController {

    @Autowired
    private DatabaseWorker databaseWorker;

    @FXML
    private AnchorPane mainDataPane;
    @FXML
    private Button loadPhotoImage;
    @FXML
    private TextField addressField;
    @FXML
    private TextField burialPlaceField;
    @FXML
    private Label burialPlaceLabel;
    @FXML
    private TextField caseNumberField;
    @FXML
    private ComboBox<CategoryEntity> categoryField;
    @FXML
    private DatePicker dateOfBirthField;
    @FXML
    private DatePicker dateOfDeathField;
    @FXML
    private Label dateOfDeathLabel;
    @FXML
    private ComboBox<DistrictEntity> districtField;
    @FXML
    private TextField firstNameField;
    @FXML
    private ImageView imagePhotoView;
    @FXML
    private CheckBox isDeadCheckBox;
    @FXML
    private TextField marchingOrganizationField;
    @FXML
    private TextField middleNameField;
    @FXML
    private ComboBox<RankEntity> militaryRankField;
    @FXML
    private TextField militarySubdivisionField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField regionalExecutiveCommitteeField;
    @FXML
    private TextField registrationAddressField;
    @FXML
    private TextField secondNameField;
    @FXML
    private ComboBox<SubcategoryEntity> subcategoryField;
    @FXML
    private TextField villageExecutiveCommitteeField;
    @FXML
    private ComboBox<RgvkEntity> rgvkField;

    private ObservableList<CategoryEntity> categoryData;

    private ObservableList<DistrictEntity> districtData;

    private ObservableList<RankEntity> rankData;

    private ObservableList<RgvkEntity> rgvkData;

    private ObservableList<SubcategoryEntity> subcategoryData;

    private VeteranEntity veteranEntity;

    private ValidationSupport validationSupport = new ValidationSupport();

    public void handleSelectCategory() {
        subcategoryField.setDisable(false);
        if (!categoryField.getSelectionModel().isEmpty()) {
            this.subcategoryData = FXCollections.observableArrayList(categoryField.getValue().getSubcategories());
            subcategoryField.setItems(subcategoryData);
        }
    }

    @FXML
    public void initialize() {
        validationSupport.setErrorDecorationEnabled(false);
        validationSupport.registerValidator(caseNumberField, Validator.createEmptyValidator("Text is required", Severity.ERROR));
        validationSupport.registerValidator(firstNameField, Validator.createEmptyValidator("Text is required", Severity.ERROR));
        validationSupport.registerValidator(middleNameField, Validator.createEmptyValidator("Text is required", Severity.ERROR));
        validationSupport.registerValidator(secondNameField, Validator.createEmptyValidator("Text is required", Severity.ERROR));
        validationSupport.registerValidator(districtField, Validator.createEmptyValidator("Text is required", Severity.ERROR));
        validationSupport.registerValidator(categoryField, Validator.createEmptyValidator("Text is rauired", Severity.ERROR));

        validationSupport.registerValidator(dateOfBirthField, Validator.createEmptyValidator("Text is rauired", Severity.WARNING));
        validationSupport.registerValidator(militaryRankField, Validator.createEmptyValidator("Text is rauired", Severity.WARNING));

        districtData = FXCollections.observableArrayList(databaseWorker.getDistricts());
        districtField.setItems(districtData);
        rgvkData = FXCollections.observableArrayList(databaseWorker.getRgvk());
        rgvkField.setItems(rgvkData);
        rankData = FXCollections.observableArrayList(databaseWorker.getRanks());
        militaryRankField.setItems(rankData);
        categoryData = FXCollections.observableArrayList(databaseWorker.getCategories());
        categoryField.setItems(categoryData);
        subcategoryData = FXCollections.observableArrayList();
        subcategoryField.setItems(subcategoryData);
    }

    public void setVisibleFields() {
        if (isDeadCheckBox.isSelected()) {
            dateOfDeathLabel.setVisible(true);
            burialPlaceLabel.setVisible(true);
            dateOfDeathField.setVisible(true);
            burialPlaceField.setVisible(true);
        } else {
            dateOfDeathLabel.setVisible(false);
            burialPlaceLabel.setVisible(false);
            dateOfDeathField.setVisible(false);
            burialPlaceField.setVisible(false);
        }
    }

    @FXML
    private void loadVeteranPhoto() throws NullPointerException {
        String photo = FileLoader.loadPhoto();
        if (!photo.isEmpty()) {
            Image image = new Image(photo);
            imagePhotoView.setImage(image);
        }
    }
}

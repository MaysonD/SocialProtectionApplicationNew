package com.module.controllers.veterandialog;

import com.module.helpers.ImageConverter;
import com.module.model.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class VeteranDialogDataManager {

    private VeteranDialogManager veteranDialogManager;
    private ObservableList<MilitaryTermEntity> militaryTermData = FXCollections.observableArrayList();
    private ObservableList<VeteranHonorEntity> honorsData = FXCollections.observableArrayList();
    private ObservableList<FamilyMemberEntity> familyMembersData = FXCollections.observableArrayList();
    private ObservableList<DocumentEntity> documentsData = FXCollections.observableArrayList();
    private ObservableList<DisplacementEntity> displacementData = FXCollections.observableArrayList();
    private ObservableList<WorkPlaceEntity> workPlacesData = FXCollections.observableArrayList();
    private ObservableList<VeteranWoundEntity> woundsData = FXCollections.observableArrayList();
    private ObservableList<SubcategoryEntity> subcategoryData = FXCollections.observableArrayList();
    private ObservableList<HelpEntity> helpData = FXCollections.observableArrayList();
    private VeteranEntity veteranEntity;

    public void setNewVeteran() {

        this.veteranEntity.setCaseNumber(veteranDialogManager.getMainDataPaneController().getCaseNumberField().getText());
        this.veteranEntity.setFirstName(veteranDialogManager.getMainDataPaneController().getFirstNameField().getText());
        this.veteranEntity.setSecondName(veteranDialogManager.getMainDataPaneController().getSecondNameField().getText());
        this.veteranEntity.setMiddleName(veteranDialogManager.getMainDataPaneController().getMiddleNameField().getText());
        this.veteranEntity.setDateOfBirth(veteranDialogManager.getMainDataPaneController().getDateOfBirthField().getValue());
        this.veteranEntity.setDistrict(veteranDialogManager.getMainDataPaneController().getDistrictField().getValue());

        this.veteranEntity.setCategory(veteranDialogManager.getMainDataPaneController().getCategoryField().getValue());
        this.veteranEntity.setSubcategory(veteranDialogManager.getMainDataPaneController().getSubcategoryField().getValue());
        this.veteranEntity.setMilitaryRank(veteranDialogManager.getMainDataPaneController().getMilitaryRankField().getValue());
        this.veteranEntity.setPosition(veteranDialogManager.getMainDataPaneController().getPositionField().getText());
        this.veteranEntity.setSubdivision(veteranDialogManager.getMainDataPaneController().getMilitarySubdivisionField().getText());

        this.veteranEntity.setMarchingOrganization(veteranDialogManager.getMainDataPaneController().getMarchingOrganizationField().getText());
        this.veteranEntity.setVillageExecutiveCommittee(veteranDialogManager.getMainDataPaneController().getVillageExecutiveCommitteeField().getText());
        this.veteranEntity.setRegionalExecutiveCommittee(veteranDialogManager.getMainDataPaneController().getRegionalExecutiveCommitteeField().getText());
        this.veteranEntity.setRgvk(veteranDialogManager.getMainDataPaneController().getRgvkField().getValue());

        this.veteranEntity.setAddress(veteranDialogManager.getMainDataPaneController().getAddressField().getText());
        this.veteranEntity.setRegistrationAddress(veteranDialogManager.getMainDataPaneController().getRegistrationAddressField().getText());
        this.veteranEntity.setPhoneNumber(veteranDialogManager.getMainDataPaneController().getPhoneNumberField().getText());

        this.veteranEntity.setIsDead(veteranDialogManager.getMainDataPaneController().getIsDeadCheckBox().isSelected());
        this.veteranEntity.setDateOfDeath(veteranDialogManager.getMainDataPaneController().getDateOfDeathField().getValue());
        this.veteranEntity.setBurialPlace(veteranDialogManager.getMainDataPaneController().getBurialPlaceField().getText());

        try {
            this.veteranEntity.setPhoto(ImageConverter.convertImageToBytes(veteranDialogManager.getMainDataPaneController().getImagePhotoView().getImage()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.veteranEntity.setVeteranHonors(honorsData);
        this.veteranEntity.setMilitaryTerms(militaryTermData);
        this.veteranEntity.setFamilyMembers(familyMembersData);
        this.veteranEntity.setDisplacements(displacementData);
        this.veteranEntity.setWorkPlaces(workPlacesData);
        this.veteranEntity.setDocuments(documentsData);
        this.veteranEntity.setWounds(woundsData);
        this.veteranEntity.setHelpList(helpData);

    }

    @Autowired
    public void setVeteranDialogManager(VeteranDialogManager veteranDialogManager) {
        this.veteranDialogManager = veteranDialogManager;
    }

    public void setVeteranEntity(VeteranEntity veteranEntity) {
        this.veteranEntity = veteranEntity;

        veteranDialogManager.getMainDataPaneController().getCaseNumberField().setText(this.veteranEntity.getCaseNumber());
        veteranDialogManager.getMainDataPaneController().getFirstNameField().setText(this.veteranEntity.getFirstName());
        veteranDialogManager.getMainDataPaneController().getSecondNameField().setText(this.veteranEntity.getSecondName());
        veteranDialogManager.getMainDataPaneController().getMiddleNameField().setText(this.veteranEntity.getMiddleName());
        veteranDialogManager.getMainDataPaneController().getDateOfBirthField().setValue(this.veteranEntity.getDateOfBirth());
        veteranDialogManager.getMainDataPaneController().getDistrictField().setValue(this.veteranEntity.getDistrict());

        veteranDialogManager.getMainDataPaneController().getCategoryField().setValue(this.veteranEntity.getCategory());
        if (this.veteranEntity.getCategory() != null) {
            subcategoryData.addAll(this.veteranEntity.getCategory().getSubcategories());
            veteranDialogManager.getMainDataPaneController().getSubcategoryField().setItems(subcategoryData);
        }
        veteranDialogManager.getMainDataPaneController().getSubcategoryField().setValue(this.veteranEntity.getSubcategory());
        veteranDialogManager.getMainDataPaneController().getMilitaryRankField().setValue(this.veteranEntity.getMilitaryRank());
        veteranDialogManager.getMainDataPaneController().getPositionField().setText(this.veteranEntity.getPosition());
        veteranDialogManager.getMainDataPaneController().getMilitarySubdivisionField().setText(this.veteranEntity.getSubdivision());

        veteranDialogManager.getMainDataPaneController().getMarchingOrganizationField().setText(this.veteranEntity.getMarchingOrganization());
        veteranDialogManager.getMainDataPaneController().getVillageExecutiveCommitteeField().setText(this.veteranEntity.getVillageExecutiveCommittee());
        veteranDialogManager.getMainDataPaneController().getRegionalExecutiveCommitteeField().setText(this.veteranEntity.getRegionalExecutiveCommittee());
        veteranDialogManager.getMainDataPaneController().getRgvkField().setValue(this.veteranEntity.getRgvk());

        veteranDialogManager.getMainDataPaneController().getAddressField().setText(this.veteranEntity.getAddress());
        veteranDialogManager.getMainDataPaneController().getRegistrationAddressField().setText(this.veteranEntity.getRegistrationAddress());
        veteranDialogManager.getMainDataPaneController().getPhoneNumberField().setText(this.veteranEntity.getPhoneNumber());
        veteranDialogManager.getMainDataPaneController().getIsDeadCheckBox().selectedProperty().setValue(this.veteranEntity.getIsDead());
        if (veteranDialogManager.getMainDataPaneController().getIsDeadCheckBox().isSelected()) {
            veteranDialogManager.getMainDataPaneController().getDateOfDeathLabel().setVisible(true);
            veteranDialogManager.getMainDataPaneController().getBurialPlaceField().setVisible(true);
            veteranDialogManager.getMainDataPaneController().getDateOfDeathField().setVisible(true);
            veteranDialogManager.getMainDataPaneController().getBurialPlaceField().setVisible(true);
            veteranDialogManager.getMainDataPaneController().getDateOfDeathField().setValue(this.veteranEntity.getDateOfDeath());
            veteranDialogManager.getMainDataPaneController().getBurialPlaceField().setText(this.veteranEntity.getBurialPlace());
        }

        veteranDialogManager.getMainDataPaneController().getImagePhotoView().setImage(ImageConverter.convertBytesToImage(this.veteranEntity.getPhoto()));

        honorsData = veteranDialogManager.getTabsPaneManager().getHonorsTabController().getHonorsData();
        honorsData.addAll(this.veteranEntity.getVeteranHonors());
        veteranDialogManager.getTabsPaneManager().getHonorsTabController().getHonorsTable().setItems(honorsData);

        militaryTermData = veteranDialogManager.getTabsPaneManager().getMilitaryTermsTabController().getMilitaryTermData();
        militaryTermData.addAll(this.veteranEntity.getMilitaryTerms());
        veteranDialogManager.getTabsPaneManager().getMilitaryTermsTabController().getMilitaryTermTable().setItems(militaryTermData);

        familyMembersData = veteranDialogManager.getTabsPaneManager().getFamilyMembersTabController().getFamilyMembersData();
        familyMembersData.addAll(this.veteranEntity.getFamilyMembers());
        veteranDialogManager.getTabsPaneManager().getFamilyMembersTabController().getFamilyMembersTable().setItems(familyMembersData);

        displacementData = veteranDialogManager.getTabsPaneManager().getDisplacementsTabController().getDisplacementData();
        displacementData.addAll(this.veteranEntity.getDisplacements());
        veteranDialogManager.getTabsPaneManager().getDisplacementsTabController().getDisplacementTable().setItems(displacementData);

        workPlacesData = veteranDialogManager.getTabsPaneManager().getWorkPlacesTabController().getWorkPlacesData();
        workPlacesData.addAll(this.veteranEntity.getWorkPlaces());
        veteranDialogManager.getTabsPaneManager().getWorkPlacesTabController().getWorkPlaceTable().setItems(workPlacesData);

        documentsData = veteranDialogManager.getTabsPaneManager().getDocumentsTabController().getDocumentsData();
        documentsData.addAll(this.veteranEntity.getDocuments());
        veteranDialogManager.getTabsPaneManager().getDocumentsTabController().getDocumentsTable().setItems(documentsData);

        woundsData = veteranDialogManager.getTabsPaneManager().getWoundsTabController().getWoundsData();
        woundsData.addAll(this.veteranEntity.getWounds());
        veteranDialogManager.getTabsPaneManager().getWoundsTabController().getWoundsTable().setItems(woundsData);

        helpData = veteranDialogManager.getTabsPaneManager().getHelpTabController().getHelpData();
        helpData.addAll(this.veteranEntity.getHelpList());
        veteranDialogManager.getTabsPaneManager().getHelpTabController().getHelpTable().setItems(helpData);
    }
}
package com.module.controllers.veterandialog;

import com.module.controllers.veterandialog.tabs.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class TabsPaneManager {

    private final DisplacementsTabController displacementsTabController;
    private final DocumentsTabController documentsTabController;
    private final FamilyMembersTabController familyMembersTabController;
    private final HonorsTabController honorsTabController;
    private final MilitaryTermsTabController militaryTermsTabController;
    private final WorkPlacesTabController workPlacesTabController;
    private final WoundsTabController woundsTabController;
    private final HelpTabController helpTabController;

    @Autowired
    public TabsPaneManager(DisplacementsTabController displacementsTabController,
                           DocumentsTabController documentsTabController,
                           FamilyMembersTabController familyMembersTabController,
                           HonorsTabController honorsTabController,
                           MilitaryTermsTabController militaryTermsTabController,
                           WorkPlacesTabController workPlacesTabController,
                           WoundsTabController woundsTabController,
                           HelpTabController helpTabController) {
        this.displacementsTabController = displacementsTabController;
        this.documentsTabController = documentsTabController;
        this.familyMembersTabController = familyMembersTabController;
        this.honorsTabController = honorsTabController;
        this.militaryTermsTabController = militaryTermsTabController;
        this.workPlacesTabController = workPlacesTabController;
        this.woundsTabController = woundsTabController;
        this.helpTabController = helpTabController;
    }
}

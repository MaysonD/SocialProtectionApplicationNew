package com.module.controllers.veterandialog;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class VeteranDialogManager {

    private final MainDataPaneController mainDataPaneController;
    private final TabsPaneManager tabsPaneManager;
    private final FooterPaneController footerPaneController;

    @Autowired
    public VeteranDialogManager(MainDataPaneController mainDataPaneController,
                                TabsPaneManager tabsPaneManager,
                                FooterPaneController footerPaneController) {
        this.mainDataPaneController = mainDataPaneController;
        this.tabsPaneManager = tabsPaneManager;
        this.footerPaneController = footerPaneController;
    }
}

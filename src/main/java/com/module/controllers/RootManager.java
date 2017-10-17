package com.module.controllers;

import com.module.database.DatabaseWorker;
import javafx.stage.Stage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class RootManager {

    private final FiltersController filtersController;
    private final HeaderController headerController;
    private final FooterController footerController;
    private final MainTableController mainTableController;
    private Stage primaryStage;
    private DatabaseWorker databaseWorker;

    @Autowired
    public RootManager(MainTableController mainTableController,
                       HeaderController headerController,
                       FiltersController filtersController,
                       FooterController footerController,
                       DatabaseWorker databaseWorker,
                       Stage primaryStage
    ) {
        this.headerController = headerController;
        this.mainTableController = mainTableController;
        this.filtersController = filtersController;
        this.footerController = footerController;
        this.databaseWorker = databaseWorker;
        this.primaryStage = primaryStage;
    }
}

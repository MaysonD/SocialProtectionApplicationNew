package com.module.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import lombok.Data;

@Data
public class CustomAlertCreator {

    private ButtonType buttonTypeOk;
    private ButtonType buttonTypeCancel;
    private ButtonType buttonTypeNo;

    public Alert createDeleteConfirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);

        alert.setTitle("Предупреждающее окно");
        alert.setHeaderText("Вы действительно хотите удалить запись?");
        alert.setContentText(" ");

        buttonTypeOk = new ButtonType("Ок");
        buttonTypeCancel = new ButtonType("Отмена");

        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);

        return alert;
    }

    public Alert createNotSaveDataConfirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);

        alert.setTitle("Предупреждающее окно");
        alert.setHeaderText("Возможно есть несохраненные данные");
        alert.setContentText("Сохранить данные ?");

        buttonTypeOk = new ButtonType("Да");
        buttonTypeNo = new ButtonType("Нет");
        buttonTypeCancel = new ButtonType("Отмена");


        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo, buttonTypeCancel);

        return alert;
    }
}

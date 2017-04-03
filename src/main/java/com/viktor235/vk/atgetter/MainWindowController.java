package com.viktor235.vk.atgetter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class MainWindowController {
    @FXML
    private TextField applicationID;

    private AccessTokenHandler accessTokenHandler = new AccessTokenHandler();

    public void getAccessTokenAction(ActionEvent actionEvent) {
        accessTokenHandler.setUserId(applicationID.getText());
        WebWindowController web = new WebWindowController(accessTokenHandler);
        web.show();
    }

    public void changeScope(ActionEvent actionEvent) {
        CheckBox checkBox = (CheckBox) actionEvent.getSource();
        if (checkBox.isSelected())
            accessTokenHandler.getScope().add(checkBox.getText());
        else
            accessTokenHandler.getScope().remove(checkBox.getText());
    }
}

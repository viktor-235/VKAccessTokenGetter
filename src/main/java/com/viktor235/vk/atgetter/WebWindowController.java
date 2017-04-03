package com.viktor235.vk.atgetter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Victor on 1/9/17.
 */
public class WebWindowController extends Stage implements Initializable {
    @FXML
    private WebView webView;

    private static AccessTokenHandler accessTokenHandler;

    public WebWindowController(AccessTokenHandler accessTokenHandler) {
        this.accessTokenHandler = accessTokenHandler;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/webWindow.fxml"));
        fxmlLoader.setController(this);
        setTitle("VK login");
        setHeight(500);
        setWidth(700);
        initModality(Modality.WINDOW_MODAL);
        //initOwner(((Node)actionEvent.getSource()).getScene().getWindow());

        // Nice to have this in a load() method instead of constructor, but this seems to be de-facto standard.
        try {
            setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebEngine engine = webView.getEngine();
        engine.load(accessTokenHandler.getConnectionUrl());

        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    String at = accessTokenHandler.parseResponseUrl(engine.getLocation());
                    if (at != null) {
                        String resultPage = generateResultPage(accessTokenHandler.getConnectionUrl(), at);
                        engine.loadContent(resultPage);
                    }
                }
            }
        });
    }

    private String generateResultPage(String connectionUrl, String accessToken) {
        InputStream inputStream = null;
        String html = null;
        try {
            inputStream = getClass().getResource("/result.html").openStream();
            html = IOUtils.toString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return String.format(html, connectionUrl, accessToken);
    }
}

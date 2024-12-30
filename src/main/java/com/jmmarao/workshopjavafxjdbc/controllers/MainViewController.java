package com.jmmarao.workshopjavafxjdbc.controllers;

import com.jmmarao.workshopjavafxjdbc.MainApplication;
import com.jmmarao.workshopjavafxjdbc.services.DepartmentService;
import com.jmmarao.workshopjavafxjdbc.services.SellerService;
import com.jmmarao.workshopjavafxjdbc.utils.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemSeller;

    @FXML
    private MenuItem menuItemDepartment;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction() {
        loadView("/com/jmmarao/workshopjavafxjdbc/SellerListView.fxml", (SellerListController controller) -> {
            controller.setSellerService(new SellerService());
            controller.updateSellerTableView();
        });
    }

    @FXML
    public void onMenuItemDepartmentAction() {
        loadView("/com/jmmarao/workshopjavafxjdbc/DepartmentListView.fxml", (DepartmentListController controller) -> {
            controller.setDepartmentService(new DepartmentService());
            controller.updateDepartmentTableView();
        });
    }

    @FXML
    public void onMenuItemAboutAction() {
        loadView("/com/jmmarao/workshopjavafxjdbc/AboutView.fxml", controller -> {
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();

            Scene mainScene = MainApplication.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());

            T controller = loader.getController();
            initializingAction.accept(controller);
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }
}

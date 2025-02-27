package com.jmmarao.workshopjavafxjdbc.controllers;

import com.jmmarao.workshopjavafxjdbc.MainApplication;
import com.jmmarao.workshopjavafxjdbc.exceptions.DbException;
import com.jmmarao.workshopjavafxjdbc.listeners.DataChangeListener;
import com.jmmarao.workshopjavafxjdbc.models.entities.Seller;
import com.jmmarao.workshopjavafxjdbc.services.DepartmentService;
import com.jmmarao.workshopjavafxjdbc.services.SellerService;
import com.jmmarao.workshopjavafxjdbc.utils.Alerts;
import com.jmmarao.workshopjavafxjdbc.utils.StageUtils;
import com.jmmarao.workshopjavafxjdbc.utils.TableColumnUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerListController implements Initializable, DataChangeListener {

    private SellerService sellerService;

    private ObservableList<Seller> sellerObservableList;

    @FXML
    private TableView<Seller> sellerTableView;

    @FXML
    private TableColumn<Seller, Integer> tableColumnId;

    @FXML
    private TableColumn<Seller, String> tableColumnName;

    @FXML
    private TableColumn<Seller, String> tableColumnEmail;

    @FXML
    private TableColumn<Seller, Date> tableColumnBirthDate;

    @FXML
    private TableColumn<Seller, Double> tableColumnBaseSalary;

    @FXML
    private TableColumn<Seller, Seller> tableColumnEdit;

    @FXML
    private TableColumn<Seller, Seller> tableColumnRemove;

    @FXML
    private Button btNew;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = StageUtils.currentStage(event);
        Seller seller = new Seller();

        createDialogForm(seller, "/com/jmmarao/workshopjavafxjdbc/SellerForm.fxml", parentStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public void onDataChanged() {
        updateSellerTableView();
    }

    private void initEditButton() {
        tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEdit.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Seller item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event ->
                        createDialogForm(item, "/com/jmmarao/workshopjavafxjdbc/SellerForm.fxml", StageUtils.currentStage(event)));
            }
        });
    }

    private void initRemoveButtons() {
        tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnRemove.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("remove");

            @Override
            protected void updateItem(Seller item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> removeEntity(item)
                );
            }
        });
    }

    public void updateSellerTableView() {
        if (sellerService == null) {
            throw new IllegalStateException("Seller service null");
        }

        List<Seller> sellerList = sellerService.findAll();
        sellerObservableList = FXCollections.observableArrayList(sellerList);
        sellerTableView.setItems(sellerObservableList);
        initEditButton();
        initRemoveButtons();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        TableColumnUtils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");

        tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
        TableColumnUtils.formatTableColumnDouble(tableColumnBaseSalary, 2);

        Stage stage = (Stage) MainApplication.getMainScene().getWindow();
        sellerTableView.prefHeightProperty().bind(stage.heightProperty());
    }

    private void createDialogForm(Seller seller, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            SellerFormController formController = loader.getController();
            formController.setSeller(seller);
            formController.setServices(new SellerService(), new DepartmentService());
            formController.loadAssociatedObjects();
            formController.subscribeDataChangeListener(this);
            formController.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Seller Data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void removeEntity(Seller seller) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

        if (result.get() == ButtonType.OK) {
            if (sellerService == null)
                throw new IllegalStateException("Service was null");

            try {
                sellerService.remove(seller);
                updateSellerTableView();
            } catch (DbException exception) {
                Alerts.showAlert("Error removing object", null, exception.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}

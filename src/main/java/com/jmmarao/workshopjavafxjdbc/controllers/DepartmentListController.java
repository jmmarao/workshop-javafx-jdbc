package com.jmmarao.workshopjavafxjdbc.controllers;

import com.jmmarao.workshopjavafxjdbc.MainApplication;
import com.jmmarao.workshopjavafxjdbc.models.Department;
import com.jmmarao.workshopjavafxjdbc.services.DepartmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable {

    private DepartmentService departmentService;

    private ObservableList<Department> departmentObservableList;

    @FXML
    private TableView<Department> departmentTableView;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button btNew;

    @FXML
    public void onBtNewAction() {
        System.out.println("onBtNewAction");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public void updateDepartmentTableView() {
        if (departmentService == null) {
            throw new IllegalStateException("Department service null");
        }

        List<Department> departmentList = departmentService.findAll();
        departmentObservableList = FXCollections.observableArrayList(departmentList);
        departmentTableView.setItems(departmentObservableList);
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = (Stage) MainApplication.getMainScene().getWindow();
        departmentTableView.prefHeightProperty().bind(stage.heightProperty());
    }
}

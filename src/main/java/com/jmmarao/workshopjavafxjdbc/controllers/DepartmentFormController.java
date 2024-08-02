package com.jmmarao.workshopjavafxjdbc.controllers;

import com.jmmarao.workshopjavafxjdbc.exceptions.DbException;
import com.jmmarao.workshopjavafxjdbc.models.entities.Department;
import com.jmmarao.workshopjavafxjdbc.services.DepartmentService;
import com.jmmarao.workshopjavafxjdbc.utils.Alerts;
import com.jmmarao.workshopjavafxjdbc.utils.Constraints;
import com.jmmarao.workshopjavafxjdbc.utils.ParseUtils;
import com.jmmarao.workshopjavafxjdbc.utils.StageUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {

    private Department department;

    private DepartmentService departmentService;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelNameError;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (department == null)
            throw new IllegalStateException("Null enitity");

        if (departmentService == null)
            throw new IllegalStateException("Null service");
        try {
            department = getFormData();
            departmentService.saveOrUpdate(department);
            StageUtils.currentStage(event).close();
        } catch (DbException e) {
            Alerts.showAlert("Error saving department", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        StageUtils.currentStage(event).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    public void updateFormData() {
        if (department == null)
            throw new IllegalStateException("Null enitity");

        txtId.setText(String.valueOf(department.getId()));
        txtName.setText(String.valueOf(department.getName()));
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    private Department getFormData() {
        Department departmentFormData = new Department();
        departmentFormData.setId(ParseUtils.tryParseStringToInteger(txtId.getText()));
        departmentFormData.setName(txtName.getText());
        return departmentFormData;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
}

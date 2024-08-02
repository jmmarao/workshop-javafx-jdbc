package com.jmmarao.workshopjavafxjdbc.controllers;

import com.jmmarao.workshopjavafxjdbc.models.entities.Department;
import com.jmmarao.workshopjavafxjdbc.utils.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {

    private Department department;

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
    public void onBtSaveAction() {
        System.out.println("onBtSaveAction");
    }

    @FXML
    public void onBtCancelAction() {
        System.out.println("onBtCancelAction");
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

    public void setDepartment(Department department) {
        this.department = department;
    }
}

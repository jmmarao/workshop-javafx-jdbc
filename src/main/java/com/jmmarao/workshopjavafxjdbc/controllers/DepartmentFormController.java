package com.jmmarao.workshopjavafxjdbc.controllers;

import com.jmmarao.workshopjavafxjdbc.exceptions.DbException;
import com.jmmarao.workshopjavafxjdbc.listeners.DataChangeListener;
import com.jmmarao.workshopjavafxjdbc.models.entities.Department;
import com.jmmarao.workshopjavafxjdbc.models.exceptions.ValidationException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class DepartmentFormController implements Initializable {

    private Department department;

    private DepartmentService departmentService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

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
            notifyDataChangeListeners();
            StageUtils.currentStage(event).close();
        } catch (DbException e) {
            Alerts.showAlert("Error saving department", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());
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

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    private Department getFormData() {
        Department departmentFormData = new Department();
        ValidationException exception = new ValidationException("Validation error");

        departmentFormData.setId(ParseUtils.tryParseStringToInteger(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addError("name", "Field can't be empty");
        }

        departmentFormData.setName(txtName.getText());

        if (exception.getErrors().size() > 0) {
            throw exception;
        }

        return departmentFormData;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        if (fields.contains("name")) {
            labelErrorName.setText(errors.get("name"));
        }
    }
}

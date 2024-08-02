module com.jmmarao.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires java.sql;

    opens com.jmmarao.workshopjavafxjdbc to javafx.fxml;
    exports com.jmmarao.workshopjavafxjdbc;
    opens com.jmmarao.workshopjavafxjdbc.controllers to javafx.fxml;
    exports com.jmmarao.workshopjavafxjdbc.controllers;
    opens com.jmmarao.workshopjavafxjdbc.services to javafx.fxml;
    exports com.jmmarao.workshopjavafxjdbc.services;
    opens com.jmmarao.workshopjavafxjdbc.models.entities to javafx.fxml;
    exports com.jmmarao.workshopjavafxjdbc.models.entities;
}
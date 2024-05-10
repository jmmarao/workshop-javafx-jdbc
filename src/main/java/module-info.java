module com.jmmarao.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jmmarao.workshopjavafxjdbc to javafx.fxml;
    exports com.jmmarao.workshopjavafxjdbc;
    exports com.jmmarao.workshopjavafxjdbc.controller;
    opens com.jmmarao.workshopjavafxjdbc.controller to javafx.fxml;
}
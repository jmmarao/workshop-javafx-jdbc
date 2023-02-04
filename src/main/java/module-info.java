module com.jmmarao.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jmmarao.workshopjavafxjdbc to javafx.fxml;
    exports com.jmmarao.workshopjavafxjdbc;
}
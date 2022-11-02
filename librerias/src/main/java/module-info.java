module com.mycompany.librerias {
    requires javafx.controls;
    requires javafx.fxml;
    requires  java.sql;
    opens com.mycompany.librerias to javafx.fxml;
    exports com.mycompany.librerias;
}

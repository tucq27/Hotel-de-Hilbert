module com.smarthotel {
    requires javafx.controls;
    requires javafx.fxml;

    requires transitive javafx.graphics;

    exports com.smarthotel.gui;
    opens com.smarthotel.gui to javafx.fxml;
    
    exports com.smarthotel.models;
    exports com.smarthotel.negocios;
    exports com.smarthotel.dados;

}
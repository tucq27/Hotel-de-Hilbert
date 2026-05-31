module com.smarthotel {
    requires javafx.controls;
    requires javafx.fxml;

    requires transitive javafx.graphics;

    exports com.smarthotel.gui.controllers;
    opens com.smarthotel.gui.controllers to javafx.fxml;

    exports com.smarthotel.gui.telas;
    opens com.smarthotel.gui.telas to javafx.fxml;
    
    exports com.smarthotel.models;
    exports com.smarthotel.negocios;
    exports com.smarthotel.dados;

}
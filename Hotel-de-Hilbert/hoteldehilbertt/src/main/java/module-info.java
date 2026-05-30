module com.smarthotel.hoteldehilbertt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.smarthotel.hoteldehilbertt to javafx.fxml;
    exports com.smarthotel.hoteldehilbertt;
}
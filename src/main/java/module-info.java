module com.georgiancollege.assignment1gc200605831 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.georgiancollege.assignment1gc200605831 to javafx.fxml;
    exports com.georgiancollege.assignment1gc200605831;
}
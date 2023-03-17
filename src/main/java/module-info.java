module com.example.gamecleaned {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gamecleaned to javafx.fxml;
    exports com.example.gamecleaned;
}
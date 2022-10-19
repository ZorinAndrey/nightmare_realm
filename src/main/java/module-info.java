module ru.azor.nightmare_realm {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.azor.nightmare to javafx.fxml;
    exports ru.azor.nightmare;
}
module com.poolgame.poolgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.poolgame.poolgame to javafx.fxml;
    exports com.poolgame.poolgame;
}
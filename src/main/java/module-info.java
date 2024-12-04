module org.example.duetrockers {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.duetrockers to javafx.fxml;
    exports org.example.duetrockers;
}
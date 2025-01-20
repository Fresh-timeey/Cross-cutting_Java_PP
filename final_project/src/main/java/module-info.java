module com.example.project_industrial_programming_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires json.simple;

    opens com.example.project_industrial_programming_app to javafx.fxml;
    exports com.example.project_industrial_programming_app;

    // Открытие других пакетов для рефлексии или доступа, если необходимо
    opens files_working to javafx.fxml;
    opens encryption to javafx.fxml;
    opens expression to javafx.fxml;

    // Включение модулей для тестирования
   // requires org.junit.jupiter.api; // если планируется использование JUnit
}

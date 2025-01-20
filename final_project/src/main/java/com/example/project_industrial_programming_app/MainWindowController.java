package com.example.project_industrial_programming_app;
import javafx.scene.control.Label;
import archive.ZIP_working;
import encryption.AESUtil;
import expression.Regular;
import files_working.JSON;
import files_working.TXT;
import files_working.XML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

public class MainWindowController {
    private ObservableList<String> formats = FXCollections.observableArrayList("txt", "xml", "json");

    private String inputFolderPath = "C:/Users/user/IdeaProjects/Cross-cutting_Java/final_project/";  // Путь к папке с входными файлами

    @FXML
    private RadioButton archive;

    @FXML
    private RadioButton archived;

    @FXML
    private RadioButton encrypt;

    @FXML
    private RadioButton encrypted;

    @FXML
    private Button end_button;

    @FXML
    private TextField filename_input;

    @FXML
    private TextField filename_output;

    @FXML
    private TextField zip_in;

    @FXML
    private TextField zip_out;

    @FXML
    private ChoiceBox<String> format_in;

    @FXML
    private ChoiceBox<String> format_out;
    @FXML
    private Label inputLabel;  // Лейбл для отображения входного файла

    @FXML
    private Label outputLabel; // Лейбл для отображения выходного файла
    public void setFilenameInput(String filename) {
        filename_input.setText(filename);
    }

    public void setFilenameOutput(String filename) {
        filename_output.setText(filename);
    }

    public void setFormatIn(String format) {
        this.format_in.setValue(format);
    }

    public void setFormatOut(String format) {
        this.format_out.setValue(format);
    }

    @FXML
    void work_with_input_file(ActionEvent event) throws IOException {
        String filename_inputText = inputFolderPath + filename_input.getText() + "." + format_in.getValue();
        String filename_outputText = filename_output.getText();
        String form_in = format_in.getValue();
        String form_out = format_out.getValue();

        // Декомпрессия архива, если выбран архив
        if (archived.isSelected()) {
            ZIP_working.decompress(filename_inputText, zip_in.getText());
        }

        String text = "";

        // Чтение данных из файла в зависимости от формата
        if (form_in.equals("txt")) {
            TXT txt = new TXT();
            text = txt.Read(filename_inputText);
        } else if (form_in.equals("xml")) {
            XML xml = new XML();
            text = xml.Read(filename_inputText);
        } else if (form_in.equals("json")) {
            JSON json = new JSON();
            text = json.Read(filename_inputText);
        }

        // Дешифрование, если выбрано
        if (encrypted.isSelected()) {
            text = AESUtil.decrypt(text);
        }

        // Обработка текста: поиск и решение математических выражений
        Regular reg_expression = new Regular();
        StringBuilder resultBuilder = new StringBuilder();
        String[] lines = text.split("\\n"); // Разделяем текст на строки

        for (String line : lines) {
            // Используем регулярное выражение для поиска математических выражений
            String[] expressions = line.split("[^0-9+\\-*/().]+"); // Ищем возможные выражения
            for (String expression : expressions) {
                if (!expression.isBlank() && expression.matches("[0-9+\\-*/().]+")) {
                    try {
                        String result = reg_expression.EvaluateExpression(expression);
                        resultBuilder.append(expression).append(" => ").append(result).append("\n");
                    } catch (Exception e) {
                        resultBuilder.append("Ошибка обработки: ").append(expression).append("\n");
                    }
                }
            }
        }

        String answer = resultBuilder.toString();

        // Отображение входного и выходного текста в интерфейсе
        inputLabel.setText(text);
        outputLabel.setText(answer);

        // Шифрование результата, если выбрано
        if (encrypt.isSelected()) {
            answer = AESUtil.encrypt(answer, filename_outputText + "." + form_out);
        }

        // Запись результата в файл в зависимости от формата
        if (form_out.equals("txt")) {
            TXT txt = new TXT();
            txt.Write(answer, filename_outputText + "." + form_out);
        } else if (form_out.equals("xml")) {
            XML xml = new XML();
            xml.Write(answer, filename_outputText + "." + form_out);
        } else if (form_out.equals("json")) {
            JSON json = new JSON();
            json.Write(answer, filename_outputText + "." + form_out);
        }

        // Архивирование, если выбрано
        if (archive.isSelected()) {
            ZIP_working.compress(filename_outputText + "." + form_out, zip_out.getText());
        }

        // Уведомление об успешной операции
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Успех!");
        alert.setHeaderText(null);
        alert.setContentText("Операция выполнена успешно!");
        alert.showAndWait();
    }


    @FXML
    void initialize() {
        // Инициализация форматов
        format_in.setItems(formats);
        format_out.setItems(formats);
        format_in.setValue("txt");
        format_out.setValue("txt");

        // Установка значений для полей ввода
        filename_input.setText("in1");
        filename_output.setText("out1");
        zip_in.setText("in");
        zip_out.setText("out");
    }
}

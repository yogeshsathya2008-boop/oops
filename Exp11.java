import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.*;

public class StudentManagementApp extends Application {

    private Connection connection;

    private TextField idField = new TextField();
    private TextField nameField = new TextField();
    private TextField ageField = new TextField();
    private TextField courseField = new TextField();
    private TextArea displayArea = new TextArea();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Font font = new Font("Arial", 14);

        idField.setFont(font);
        idField.setPromptText("ID (for Update/Delete)");

        nameField.setFont(font);
        nameField.setPromptText("Name");

        ageField.setFont(font);
        ageField.setPromptText("Age");

        courseField.setFont(font);
        courseField.setPromptText("Course");

        Button createButton = new Button("Create");
        createButton.setFont(font);
        createButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        createButton.setOnAction(e -> createStudent());

        Button readButton = new Button("Display");
        readButton.setFont(font);
        readButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        readButton.setOnAction(e -> readStudents());

        Button updateButton = new Button("Update");
        updateButton.setFont(font);
        updateButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        updateButton.setOnAction(e -> updateStudent());

        Button deleteButton = new Button("Delete");
        deleteButton.setFont(font);
        deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> deleteStudent());

        displayArea.setFont(font);
        displayArea.setWrapText(true);
        displayArea.setEditable(false);

        gridPane.add(new Label("ID:"), 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(new Label("Name:"), 0, 1);
        gridPane.add(nameField, 1, 1);
        gridPane.add(new Label("Age:"), 0, 2);
        gridPane.add(ageField, 1, 2);
        gridPane.add(new Label("Course:"), 0, 3);
        gridPane.add(courseField, 1, 3);

        gridPane.add(createButton, 0, 4);
        gridPane.add(readButton, 1, 4);
        gridPane.add(updateButton, 0, 5);
        gridPane.add(deleteButton, 1, 5);

        gridPane.add(displayArea, 0, 6, 2, 1);

        connectToDatabase();

        primaryStage.setTitle("Student Management");
        primaryStage.setScene(new Scene(gridPane, 400, 500));
        primaryStage.show();
    }

    public void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "1234");
            displayArea.setText("Database connection successful!");
        } catch (Exception e) {
            displayArea.setText("Database connection failed!");
        }
    }

    private void createStudent() {
        try {
            String sql = "INSERT INTO students (name, age, course) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, nameField.getText());
                pstmt.setInt(2, Integer.parseInt(ageField.getText()));
                pstmt.setString(3, courseField.getText());
                pstmt.executeUpdate();
                displayArea.setText("Student created successfully.");
            }
        } catch (Exception e) {
            displayArea.setText("Error creating student.");
        }
    }

    private void readStudents() {
        String sql = "SELECT * FROM students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                  .append(", Name: ").append(rs.getString("name"))
                  .append(", Age: ").append(rs.getInt("age"))
                  .append(", Course: ").append(rs.getString("course"))
                  .append("\n");
            }
            displayArea.setText(sb.length() == 0 ? "No records found." : sb.toString());
        } catch (Exception e) {
            displayArea.setText("Error reading students.");
        }
    }

    private void updateStudent() {
        try {
            String sql = "UPDATE students SET name = ?, age = ?, course = ? WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, nameField.getText());
                pstmt.setInt(2, Integer.parseInt(ageField.getText()));
                pstmt.setString(3, courseField.getText());
                pstmt.setInt(4, Integer.parseInt(idField.getText()));
                pstmt.executeUpdate();
                displayArea.setText("Student updated successfully.");
            }
        } catch (Exception e) {
            displayArea.setText("Error updating student.");
        }
    }

    private void deleteStudent() {
        try {
            String sql = "DELETE FROM students WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(idField.getText()));
                pstmt.executeUpdate();
                displayArea.setText("Student deleted successfully.");
            }
        } catch (Exception e) {
            displayArea.setText("Error deleting student.");
        }
    }

    @Override
    public void stop() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        super.stop();
    }
}

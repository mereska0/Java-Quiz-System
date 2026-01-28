module com.example.javaquizsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.base;

    opens com.example.javaquizsystem to javafx.fxml;
    exports com.example.javaquizsystem.Cards;
    exports com.example.javaquizsystem.Gui;
}
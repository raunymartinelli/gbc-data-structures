module gbc.ds.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens gbc.ds.assignment2 to javafx.fxml;
    exports gbc.ds.assignment2;
}
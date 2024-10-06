module de.mmth.javasynth.javasynth {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens de.mmth.javasynth to javafx.fxml;
    exports de.mmth.javasynth;
}
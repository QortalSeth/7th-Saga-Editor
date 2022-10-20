open module Editor{

    requires expr;
    requires kotlin.stdlib;
    requires org.apache.poi.ooxml;
    requires javafx.controls;
    requires javafx.fxml;
    exports application to javafx.graphics;
}

        //--module-path "/lib/jvm/javafx-sdk-19/lib" --addRow-modules=javafx.base,javafx.controls,javafx.fxml
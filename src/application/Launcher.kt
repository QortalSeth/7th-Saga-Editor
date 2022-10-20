package application;

import javafx.application.Application

class Launcher {
            //MyApplication.launch(MyApplication.javaClass.getResource)
            //MyApplication.launch(args);
            //Application.launch(MyApplication::class.java, *args)


 /*           WARNING: Unsupported JavaFX configuration: classes were loaded from 'unnamed module @2c0070e0'
            Graphics Device initialization failed for :  es2, sw
            Error initializing QuantumRenderer: no suitable pipeline found
            java.lang.RuntimeException: java.lang.RuntimeException: Error initializing QuantumRenderer: no suitable pipeline found
*/


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(MyApplication::class.java, *args)
            // Error: Error: JavaFX runtime components are missing, and are required to run this application
        }
    }
    }

/*
 To create .exe use the following command
 jpackage --type exe --input ./Executable/JAR --dest ./Executable --main-jar ./7th-Saga-Editor.jar --main-class application.Launcher --module-path "C:\Program Files\BellSoft\LibericaJDK-19-Full\jmods" --add-modules javafx.controls,javafx.fxml --icon Icon.ico --name "7th-Saga-Editor"
 jpackage --type deb --input ./Executable/JAR --dest ./Executable --main-jar ./7th-Saga-Editor.jar --main-class application.Launcher --module-path "/lib/jvm/javafx-sdk-19" --add-modules javafx.controls,javafx.fxml --icon Icon.png --name "Seventh-Saga-Editor"

 add --jlink-options --bind-services to include java runtime into app
 jpackage --type exe --input ./Executable/JAR --dest ./Executable --main-jar ./7th-Saga-Editor.jar --main-class application.Launcher --module-path "C:\Program Files\BellSoft\LibericaJDK-19-Full\jmods" --add-modules javafx.controls,javafx.fxml --icon Icon.ico --name "7th-Saga-Editor" --jlink-options --bind-services
 jpackage --type deb --input ./Executable/JAR --dest ./Executable --main-jar ./7th-Saga-Editor.jar --main-class application.Launcher --module-path "/lib/jvm/javafx-sdk-19" --add-modules javafx.controls,javafx.fxml --icon Icon.png --name "Seventh-Saga-Editor" --jlink-options --bind-services

Add --type app-image to make an exe that does not install itself
jpackage --type exe --input ./Executable/JAR --dest ./Executable --main-jar ./7th-Saga-Editor.jar --main-class application.Launcher --module-path "C:\Program Files\BellSoft\LibericaJDK-19-Full\jmods" --add-modules javafx.controls,javafx.fxml --icon Icon.ico --name "7th-Saga-Editor" --jlink-options --bind-services --type app-image


 Sizes

 standalone exe: 201 MB


* */

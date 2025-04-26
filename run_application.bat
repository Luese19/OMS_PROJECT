@echo off
REM Set the path to the JavaFX SDK lib folder
set JAVAFX_LIB="C:\path\to\javafx-sdk-22\lib"

REM Run the application with the correct module path and modules
java --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml -jar target\system_project_sad-1.0-SNAPSHOT-shaded.jar

pause
module project_system.org {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;
    requires javafx.web;
    requires com.jfoenix;
    requires java.logging;
    requires java.xml;
    requires java.management;
    requires java.naming;
    requires java.rmi;
    requires java.security.jgss;
    requires java.sql.rowset;
    requires java.transaction.xa;
    requires javafx.media;
    requires javafx.swing;
    requires com.dlsc.pdfviewfx;
    requires javax.websocket.api;
  

    exports project_system.org to javafx.graphics;
    opens project_system.org to javafx.fxml, javafx.base;
}
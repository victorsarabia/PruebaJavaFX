module org.example.pruebafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.mariadb.jdbc;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens org.example.pruebafx to javafx.fxml, org.hibernate.orm.core;
    exports org.example.pruebafx;
    exports org.example.pruebafx.dao;
    opens org.example.pruebafx.dao to javafx.fxml, org.hibernate.orm.core;
    exports org.example.pruebafx.controller;
    opens org.example.pruebafx.controller to javafx.fxml, org.hibernate.orm.core;
    exports org.example.pruebafx.util;
    opens org.example.pruebafx.util to javafx.fxml, org.hibernate.orm.core;
    exports org.example.pruebafx.model;
    opens org.example.pruebafx.model to javafx.fxml, org.hibernate.orm.core;
    exports org.example.pruebafx.service;
    opens org.example.pruebafx.service to javafx.fxml, org.hibernate.orm.core;
}
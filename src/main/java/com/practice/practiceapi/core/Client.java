package com.practice.practiceapi.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Cliente de conexión a la base de datos.
 */
@Component
public class Client {
    private Logger logger = LoggerFactory.getLogger(Client.class);
    public Connection con;

    public Client() {
        logger.info("Instanciando la conexión a la base de datos");
        try {
            Class.forName("org.postgresql.Driver");
            this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/url_db",
                    "postgres",
                    "root");
        } catch (SQLException e) {
            logger.error("Error al realizar la conexión a la base de datos.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error("Error al obtener el driver solicitado.");

        }
    }
}

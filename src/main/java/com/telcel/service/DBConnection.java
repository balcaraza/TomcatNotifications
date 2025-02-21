package com.telcel.service;

import com.telcel.config.DataBaseConfig;

import java.sql.*;

public class DBConnection {

    private static Connection connection;

    public static void conectar() {
        try {
            // Cargar credenciales desde DataBaseConfig
            String dbHost = DataBaseConfig.getDbHost();
            String dbPort = DataBaseConfig.getDbPort();
            String dbServiceName = DataBaseConfig.getDbServiceName();
            String dbUsername = DataBaseConfig.getDbUsername();
            String dbPassword = DataBaseConfig.getDbPassword();

            String url = "jdbc:oracle:thin:@" + dbHost + ":" + dbPort + "/" + dbServiceName;
            Class.forName("oracle.jdbc.OracleDriver");

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            System.out.println("✅ Conexión establecida con éxito a la base de datos.");

        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de Oracle.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error de conexión con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            conectar();  // Solo se conecta si no hay una conexión activa
        }
        return connection;
    }

    public static void cerrarConexion() {
        try {
            if (connection != null) {
                if (connection.isClosed()) {
                    System.out.println("⚠️ La conexión ya estaba cerrada.");
                    return;
                }

                connection.close();
                System.out.println("✅ Conexión cerrada correctamente.");
                connection = null; // Evita reutilizar una conexión cerrada
            } else {
                System.out.println("⚠️ No hay conexión activa para cerrar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}

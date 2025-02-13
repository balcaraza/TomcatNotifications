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

            // Construcción de la URL de conexión para Oracle
            String url = "jdbc:oracle:thin:@//" + dbHost + ":" + dbPort + "/" + dbServiceName;



            // Cargar el driver de Oracle
            Class.forName("oracle.jdbc.OracleDriver");

            // Establecer conexión
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
        return connection;
    }

    public static void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}

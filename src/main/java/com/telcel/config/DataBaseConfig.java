package com.telcel.config;

import com.telcel.service.DBConnection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


//1 Obtener credenciales del properties para poder conectarse a la base de datos

public class DataBaseConfig {
    private static String dbHost;
    private static String dbPort;
    private static String dbServiceName;
    private static String dbUsername;
    private static String dbPassword;

    public static void cargarCredenciales(String ambiente) {
        Properties properties = new Properties();
        String archivo = "";

        switch (ambiente) {
            case "DEV":
                archivo = "db-dev.properties";
                break;
            case "QA":
                archivo = "db-qa.properties";
                break;
            case "PROD":
                archivo = "db-prod.properties";
                break;
            default:
                throw new IllegalArgumentException("Ambiente desconocido: " + ambiente);
        }

        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(archivo)) {
            if (input == null) {
                throw new IOException("No se encontró el archivo .properties del ambiente: "+ archivo);
            }
            properties.load(input);

            dbHost = properties.getProperty("HOST");
            dbPort = properties.getProperty("PORT");
            dbServiceName= properties.getProperty("SERVICENAME");
            dbUsername=properties.getProperty("USERNAME");
            dbPassword=properties.getProperty("PASSWORD");

        } catch (IOException e) {
            System.err.println("Error al cargar las credenciales de la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getDbHost() {
        return dbHost;
    }

    public static String getDbPort() {
        return dbPort;
    }

    public static String getDbServiceName() {
        return dbServiceName;
    }

    public static String getDbUsername() {
        return dbUsername;
    }

    public static String getDbPassword() {
        return dbPassword;
    }
}

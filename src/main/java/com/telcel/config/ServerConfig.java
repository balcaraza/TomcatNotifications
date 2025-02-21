/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.telcel.config;

import com.telcel.service.DBConnection;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author CONSULTOR
 */
public class ServerConfig {
     private static String SVIP;
    private static String SVPort;
    private static String SVUser;
    private static String SVPass;


    public static void ConectarServidor(String ambiente) {
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

            SVIP = properties.getProperty("RC-IP");
            SVPort = properties.getProperty("RC-PORT");
            SVUser= properties.getProperty("RC-USER");
            SVPass= properties.getProperty("RC-PASS");

        } catch (IOException e) {
            System.err.println("Error al cargar las credenciales de la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getSVIP() {
        return SVIP;
    }

    public static String getSVPort() {
        return SVPort;
    }

    public static String getSVUser() {
        return SVUser;
    }
    
    public static String getSVPass() {
        return SVPass;
    }
}

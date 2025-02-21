/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.telcel.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ObtenerUsers {

    private static String SVIP;
    private static String SVPort;
    private static String SVUser;
    private static String SVPass;
    private static String rutas;
    static List<String> usernamesList;

    public static List<String> ObtenerListaUsers(String ambiente) {
        Properties properties = new Properties();
        List<String> usernames;
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

        try (InputStream input = ObtenerUsers.class.getClassLoader().getResourceAsStream(archivo)) {
            if (input == null) {
                System.out.println("No se pudo encontrar el archivo properties");
                return null;
            }
            properties.load(input);

            rutas = properties.getProperty("FILE-TOMCAT");
            TomcatUsersXML tomcatUsersXML = new TomcatUsersXML();

            String[] rutasArray = rutas.split(",");
            Set<String> uniqueSet = new HashSet<>();

            for (String ruta : rutasArray) {
                String rutaArchivo = tomcatUsersXML.searchFile(ruta, "tomcat-users.xml");
                usernames = tomcatUsersXML.extractUsernames(rutaArchivo);
                uniqueSet.addAll(usernames);
            }

            usernamesList = new ArrayList<>(uniqueSet);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return usernamesList;

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

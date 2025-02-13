package com.telcel.config;

import com.telcel.service.Logs;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class DeployEnvironment {
    private static String devIp;
    private static String qaIp;
    private static String prodIp;

    static {
        cargarPropiedades();
    }

    private static void cargarPropiedades() {
        Properties properties = new Properties();
        try (InputStream input = DeployEnvironment.class.getClassLoader().getResourceAsStream("TomcatNotifications.properties")) {
            if (input == null) {
                System.err.println("No se encontró el archivo TomcatNotifications.properties");
                throw new IOException("No se pudo cargar el archivo de propiedades");
            }
            // Carga el archivo de propiedades
            properties.load(input);
            devIp = properties.getProperty("DEV_IP");
            qaIp = properties.getProperty("QA_IP");
            prodIp = properties.getProperty("PROD_IP");
        } catch (IOException ex) {
            ex.printStackTrace();
            Logs.logException("Error al cargar el archivo de propiedades", ex);
        }
    }

    public static String detectarAmbiente() {
        try {
            String ipLocal = InetAddress.getLocalHost().getHostAddress();

            if (ipLocal.equals(devIp)) {
                return "DEV";
            } else if (ipLocal.equals(qaIp)) {
                return "QA";
            } else if (ipLocal.equals(prodIp)) {
                return "PROD";
            } else {
                return "DEV";
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}

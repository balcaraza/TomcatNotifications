package com.telcel;

import com.telcel.config.*;
import com.telcel.model.ServerCredentials;
import com.telcel.service.*;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String ambiente = "DEV";
        System.out.println("Ambiente detectado: " + ambiente);
        // Cargar las credenciales del ambiente detectado
        DataBaseConfig.cargarCredenciales(ambiente);

        // Imprimir las credenciales
        System.out.println("HOST: " + DataBaseConfig.getDbHost());
        System.out.println("PORT: " + DataBaseConfig.getDbPort());
        System.out.println("SERVICENAME: " + DataBaseConfig.getDbServiceName());
        System.out.println("Usuario: " + DataBaseConfig.getDbUsername());
        System.out.println("Contraseña: " + DataBaseConfig.getDbPassword());

/// Iniciar la conexión
        DBConnection.conectar();

        // Obtener la conexión para verificar si es válida
        Connection connection = DBConnection.getConnection();
        if (connection != null) {
            System.out.println("Conexión obtenida correctamente.");
        } else {
            System.out.println("No se pudo obtener la conexión.");
            return;
        }

        // Obtener las credenciales del servidor desde la BD
        ServerCredentials credentials = ServerCredentialsManager.obtenerCredencialesServidor(ambiente);
        if (credentials != null) {
            System.out.println("Contraseña del servidor obtenida: " + credentials.getPassword());
        } else {
            System.out.println("No se pudieron obtener las credenciales del servidor.");
        }

        String host = "10.191.205.236";
        int port = 22; // Puerto SSH
        String user = "remedy"; // Usuario SSH
        String password = "rcROOT#2025"; // Contraseña SSH


        // Probar conexión SSH
        SSHConnection.conectarSSH(host, port, user, password);

        TomcatUsersXML tomcatUsersXML = new TomcatUsersXML();

        String rutaArchivo = tomcatUsersXML.searchFile("/tomcat/apache-tomcat-7.0.99/conf/","tomcat-users.xml");

        if (rutaArchivo != null && !rutaArchivo.isEmpty()) {
            System.out.println("✅ Archivo encontrado en: " + rutaArchivo);

            // Extraer los usernames del archivo XML
            List<String> usernames = tomcatUsersXML.extractUsernames(rutaArchivo);

            if (usernames.isEmpty()) {
                System.out.println("❌ No se encontraron usernames.");
            } else {
                UsersProperties usuariosProperties = new UsersProperties();

                for (String username : usernames) {
                    System.out.println("\nUsuario encontrado: " + username);

                    List<String> propiedadesUsuario = usuariosProperties.obtenerPropiedades(username);

                    if (!propiedadesUsuario.isEmpty()) {
                        System.out.println("Usuario " + username + ": " + propiedadesUsuario);
                    } else {
                        System.out.println("❌ No se encontraron propiedades para el usuario: " + username);
                    }
                }
            }
        } else {
            System.out.println("❌ No se encontró el archivo tomcat-users.xml.");
        }




        SSHConnection.cerrarConexion();
    }
}
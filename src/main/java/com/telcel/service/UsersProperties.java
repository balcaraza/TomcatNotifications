package com.telcel.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UsersProperties {
    private Properties properties;

    public UsersProperties() {
        properties = new Properties();

        // Usamos el classloader para cargar el archivo de propiedades desde el classpath
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Users.properties")) {
            if (inputStream == null) {
                System.err.println("❌ No se encontró el archivo de propiedades: Users.properties");
                throw new IOException("No se pudo cargar el archivo de propiedades");
            }
            // Cargar el archivo de propiedades
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("❌ Error al cargar el archivo de propiedades: " + e.getMessage());
        }
    }

    public Map<String, String> obtenerPropiedades(String username) {
        Map<String, String> userProperties = new HashMap<>();

        // Suponemos que las propiedades del usuario son como 'username.password', 'username.role', etc.
        String password = properties.getProperty(username + ".password");
        String role = properties.getProperty(username + ".role");

        // Agregar las propiedades al mapa si existen
        if (password != null) userProperties.put("password", password);
        if (role != null) userProperties.put("role", role);

        // Si no se encuentran propiedades para el usuario, imprimimos advertencias
        if (userProperties.isEmpty()) {
            System.err.println("❌ No se encontraron propiedades para el usuario: " + username);
        }

        return userProperties;
    }
}

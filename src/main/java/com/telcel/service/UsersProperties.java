package com.telcel.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class UsersProperties{
    private Properties properties;

    public UsersProperties() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("users.properties")) {
            if (inputStream == null) {
                throw new IOException("No se encontró el archivo de propiedades: users.properties");
            }
            properties.load(inputStream);
            System.out.println("✅ Archivo de propiedades cargado correctamente.");
        } catch (IOException e) {
            System.err.println("❌ Error al cargar el archivo de propiedades: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<String> obtenerPropiedades(String username) {
        username = username.toLowerCase();  // 🔹 Convertir a minúsculas para buscar correctamente


        System.out.println("Buscando propiedades para: " + username);
        String propiedades = properties.getProperty(username);

        if (propiedades == null || propiedades.trim().isEmpty()) {
            System.err.println("❌ No se encontraron propiedades para el usuario: " + username);
            return Collections.emptyList();
        }

        System.out.println("✅ Propiedades encontradas para " + username + ": " + propiedades);
        return Arrays.asList(propiedades.split("\\|"));
    }
}

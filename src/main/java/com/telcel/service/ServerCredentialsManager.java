package com.telcel.service;

import com.telcel.model.ServerCredentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerCredentialsManager {
    public static ServerCredentials obtenerCredencialesServidor(String ambiente) {
        ServerCredentials credentials = null;
        String query = "SELECT CSR_PWD FROM CTG_SERVIDORES WHERE CSR_CLAVE = 12";

        // Obtenemos la conexión desde DBConnection (que ya tiene las credenciales)
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Error: La conexión a la base de datos falló.");
                return null;
            }

            System.out.println("✅ Conexión a la base de datos establecida correctamente.");

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String password = resultSet.getString("CSR_PWD");
                if (password != null && !password.isEmpty()) {
                    credentials = new ServerCredentials(password);
                    System.out.println("✅ Contraseña recuperada correctamente.");
                } else {
                    System.err.println("Error: La contraseña está vacía o no se recuperó correctamente.");
                }
            } else {
                System.err.println("Error: No se encontraron registros con CSR_CLAVE = 12.");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener las credenciales del servidor: " + e.getMessage());
            e.printStackTrace();
        }

        return credentials;
    }
}

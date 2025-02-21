package com.telcel.service;

import com.telcel.model.ServerCredentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerCredentialsManager {

    public static ServerCredentials obtenerCredencialesServidor() {
        ServerCredentials credentials = null;
        String query = "SELECT CSR_PWD FROM CTG_SERVIDORES WHERE CSR_NOMBRE = 'RemedyControl'";
        DBConnection.conectar();
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Error: La conexión a la base de datos falló.");
                return null;
            }

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String password = resultSet.getString("CSR_PWD");
                if (password != null && !password.isEmpty()) {
                    credentials = new ServerCredentials(password);
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

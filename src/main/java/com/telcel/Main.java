package com.telcel;

import com.telcel.config.*;
import com.telcel.model.ServerCredentials;
import com.telcel.service.*;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
//        String directorio = "C:/Users/CONSULTOR/Documents/Descargas/F-00.62.00.00.00-028 Registro de Cuentas Activas_RemedyControl.xlsx";
        String directorio = "/home/remedy/archivos/correoRCA/F-00.62.00.00.00-028 Registro de Cuentas Activas_RemedyControl.xlsx";
        String ambiente = DeployEnvironment.detectarAmbiente();

        DataBaseConfig.cargarCredenciales(ambiente);

        System.out.println("HOST: " + DataBaseConfig.getDbHost());
        System.out.println("PORT: " + DataBaseConfig.getDbPort());
        System.out.println("SERVICENAME: " + DataBaseConfig.getDbServiceName());

        ServerCredentials credentials = ServerCredentialsManager.obtenerCredencialesServidor();

        ServerConfig.ConectarServidor(ambiente); //CAMBIAR A AMBIENTE

        String host = ServerConfig.getSVIP();
        String port = ServerConfig.getSVPort();
        String user = ServerConfig.getSVUser();
        String password = credentials.getPassword();
DBConnection.cerrarConexion();
        SSHConnection.conectarSSH(host, port, user, password);

        List<String> usernames = ObtenerUsers.ObtenerListaUsers(ambiente); //CAMBIAR A AMBIENTE
        System.out.println("usuarios - " + usernames.toString());

        Map<String, List<String>> datosUsuarios = new HashMap<>();
        if (usernames.isEmpty()) {
            System.out.println("❌ No se encontraron usernames.");
        } else {
            UsersProperties usuariosProperties = new UsersProperties();
            for (String username : usernames) {
                List<String> propiedadesUsuario = usuariosProperties.obtenerPropiedades(username);

                if (!propiedadesUsuario.isEmpty()) {
                    datosUsuarios.put(username, propiedadesUsuario);
                }
            }
            System.out.println("Termina el recopilado de datos");
            if (!datosUsuarios.isEmpty()) {
                System.out.println("Generando el excel Main");
                ExcelGenerator.generarExcel(directorio, datosUsuarios);

                EmailSender.Email(ambiente, host, directorio); //CAMBIAR A AMBIENTE
            } else {
                System.out.println("❌ No hay datos para generar el Excel.");
            }
        }
        
        SSHConnection.cerrarConexion();
        
    }
}

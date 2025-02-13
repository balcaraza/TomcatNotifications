package com.telcel.service;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SSHConnection {

    private static final int TIMEOUT = 10000; // 10 segundos
    private static Session session;

    public static void conectarSSH(String host, int port, String user, String password) {
        JSch jsch = new JSch();
        session = null;
        try {
            // Crear la sesión SSH
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no"); // Evita la verificación de claves del host
            session.connect(TIMEOUT); // Conectar con timeout

            System.out.println("✅Conexión SSH establecida exitosamente con " + host);

        } catch (JSchException e) {
            System.err.println("Error de conexión SSH: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void cerrarConexion() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            System.out.println("✅ Conexión SSH cerrada.");
        }
    }

    public static Session getSession() {
        return session;
    }
}


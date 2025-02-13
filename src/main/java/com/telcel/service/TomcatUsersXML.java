package com.telcel.service;

import com.jcraft.jsch.ChannelExec;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class TomcatUsersXML {
    public String searchFile(String directory, String fileName) {
        StringBuilder result = new StringBuilder();
        ChannelExec channel = null;

        try {

            // Ejecutar el comando 'find' para buscar el archivo
            channel = (ChannelExec) SSHConnection.getSession().openChannel("exec");
            channel.setCommand("find " + directory + " -name " + fileName);
            channel.setInputStream(null);
            channel.setErrStream(System.err);
            channel.connect();

            // Leer la salida del comando
            InputStream in = channel.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                result.append(new String(buffer, 0, bytesRead));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
        }

        return result.toString().trim();  // Retorna la ruta completa del archivo encontrado
    }

    // Metodo para extraer los usernames de un archivo XML de usuarios
    public List<String> extractUsernames(String filePath) {
        List<String> usernames = new ArrayList<>();
        ChannelExec channel = null;

        try {

            // Ejecutar el comando 'cat' para leer el contenido del archivo XML
            channel = (ChannelExec) SSHConnection.getSession().openChannel("exec");
            channel.setCommand("cat " + filePath);
            channel.setInputStream(null);
            channel.setErrStream(System.err);
            channel.connect();

            // Leer el contenido del archivo XML
            InputStream in = channel.getInputStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(in);
            document.getDocumentElement().normalize();

            // Buscar los elementos de usuario y extraer el atributo 'username'
            NodeList userNodes = document.getElementsByTagName("user");
            for (int i = 0; i < userNodes.getLength(); i++) {
                Node node = userNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String username = element.getAttribute("username");
                    if (!username.isEmpty()) {
                        usernames.add(username);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();  // Manejo de excepciones
        } finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
        }

        return usernames;  // Retorna la lista de usernames
    }
}
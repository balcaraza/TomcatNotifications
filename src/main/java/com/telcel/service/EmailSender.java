/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.telcel.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import okhttp3.*;

/**
 *
 * @author CONSULTOR
 */
public class EmailSender {

    private static String TO;
    private static String CC;
    private static String PORT;

    public static void Email(String ambiente, String ip, String ruta) throws IOException {
        Properties properties = new Properties();
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
        try (InputStream input = EmailSender.class.getClassLoader().getResourceAsStream(archivo)) {
            if (input == null) {
                System.out.println("No se pudo encontrar el archivo properties");
                return;
            }
            properties.load(input);

            PORT = properties.getProperty("PORT_RC");
            TO = properties.getProperty("RECIPIENTS");
            CC = properties.getProperty("CC");

            String nombre = "F-00.62.00.00.00-028 Registro de Cuentas Activas_RemedyControl.xlsx";
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS) 
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("cc", CC.replace("\"", "").trim())
                    .addFormDataPart("to", TO.replace("\"", "").trim())
                    .addFormDataPart("file", nombre,
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(ruta)))
                    .addFormDataPart("subject", "Registro de Cuentas Activas - Remedy Control")
                    .addFormDataPart("message", "Se envia el adjunto con la lista de cuentas activas en los Tomcat de Remedy Control")
                    .build();
            String URL = "http://" + ip + ":" + PORT + "/email-sender/email/send-with-attachment";
            System.out.println("URL: " + URL);
            Request request = new Request.Builder()
                    .url(URL)
                    .method("POST", body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    System.out.println("Correo enviado correctamente");
                } else {
                    System.out.println("Error al enviar correo");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al ejecutar la petición: " + e.getMessage());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

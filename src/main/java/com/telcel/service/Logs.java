package com.telcel.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Logs {
    private static final Logger LOGGER = Logger.getLogger(Logs.class.getName());

    static {
        configureLogger();
    }

    private static void configureLogger() {
        try {
            // Generamos el nombre del archivo con la fecha actual
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(new Date());
            String pattern = "/home/remedy/tomcat/logs/Apagado_servicios_ant_" + date + ".log";
            //String pattern = "C:\\Users\\CONSULTOR\\Documents\\BMC2\\Apagado_servicios_ant_" + date + ".log";

            FileHandler fileHandler = new FileHandler(pattern, true); // true para anexar datos al archivo si ya existe
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setUseParentHandlers(false); // No usar el manejador superior
        } catch (IOException e) {
            System.err.println("ERROR configurando el sistema de logs: " + e.getMessage());
        }
    }

    public static void log(String message, Level level) {
        LOGGER.log(level, message);
    }

    public static void logException(String message, Throwable exception) {
        LOGGER.log(Level.SEVERE, message, exception);
    }

    public static void cerrarManejador() {
        for (Handler handler : LOGGER.getHandlers()) {
            handler.close();
        }
    }
}

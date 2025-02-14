package com.telcel.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelGenerator {
    public static void generarExcel(String nombreArchivo, Map<String, List<String>> datosUsuarios) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Usuarios");

            // Crear encabezados
            String[] encabezados = {"Cuenta", "Num de Empleado", "Responsable", "Puesto", "Num de Empleado del Jefe Inmediato",
                    "Nombre del Jefe Inmediato", "Tipo de Cuenta", "Perfil en el Sistema", "Fecha de Creación", "ADM"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < encabezados.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(encabezados[i]);
                cell.setCellStyle(getHeaderCellStyle(workbook));
            }

            // Llenar datos de usuarios
            int rowNum = 1;
            for (Map.Entry<String, List<String>> entry : datosUsuarios.entrySet()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey()); // Cuenta
                List<String> valores = entry.getValue();
                for (int i = 0; i < valores.size(); i++) {
                    row.createCell(i + 1).setCellValue(valores.get(i));
                }
            }

            // Autoajustar columnas
            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escribir archivo
            try (FileOutputStream fileOut = new FileOutputStream(nombreArchivo)) {
                workbook.write(fileOut);
                System.out.println("✅ Archivo Excel generado exitosamente: " + nombreArchivo);
            }
        } catch (IOException e) {
            System.err.println("❌ Error al generar el archivo Excel: " + e.getMessage());
        }
    }

    private static CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}

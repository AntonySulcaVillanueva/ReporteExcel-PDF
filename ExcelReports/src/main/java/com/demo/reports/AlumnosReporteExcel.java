package com.demo.reports;

import com.demo.entity.AlumnosNotas;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Clase para generar reportes en formato Excel de los datos de la entidad AlumnosNotas.
 */
public class AlumnosReporteExcel {

    private final XSSFWorkbook workbook; // Representa el archivo Excel
    private Sheet sheet; // Representa la hoja dentro del archivo Excel
    private final List<AlumnosNotas> listarAlumnosNotas; // Lista de alumnos a incluir en el reporte

    /**
     * Constructor que inicializa la lista de alumnos y el archivo Excel.
     *
     * @param listarAlumnosNotas Lista de alumnos a incluir en el reporte.
     */
    public AlumnosReporteExcel(List<AlumnosNotas> listarAlumnosNotas) {
        this.listarAlumnosNotas = listarAlumnosNotas;
        this.workbook = new XSSFWorkbook(); // Inicializa el archivo Excel vacío-CREA
    }

    /**
     * Crea y devuelve un estilo de celda.
     *
     * @param fontSize Tamaño de la fuente.
     * @param bold Indica si la fuente es en negrita.
     * @return Estilo de celda.
     */
    private CellStyle crearEstiloCelda(int fontSize, boolean bold) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(fontSize);
        font.setBold(bold);
        style.setFont(font);
        return style;
    }


    /**
     * Método para crear una celda en la hoja de Excel.
     *
     * @param row   Fila en la que se creará la celda.
     * @param column Índice de la columna.
     * @param value Valor de la celda.
     * @param style Estilo de la celda.
     */
    private void crearCelda(Row row, int column, Object value, CellStyle style) {
        Cell cell = row.createCell(column);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue(value != null ? value.toString() : ""); // Manejo de valores nulos
        }

        cell.setCellStyle(style);
    }

    /**
     * Método para crear el encabezado de la hoja de Excel.
     */
    private void escribirCabeceraDeTabla() {
        sheet = workbook.createSheet("Alumnos"); // Crea la hoja con nombre "Alumnos"
        Row row = sheet.createRow(0); // Primera fila para el encabezado

        // Estilo para el encabezado
        CellStyle style = crearEstiloCelda(14, true);

        // Encabezados de las columnas
        String[] encabezados = {"ID", "Nombre", "Apellido", "Salón", "Número Aula",
                "Fecha Nacimiento", "Nota 1", "Nota 2", "Nota 3",
                "Nota 4", "Nota Final"};

        for (int i = 0; i < encabezados.length; i++) {
            crearCelda(row, i, encabezados[i], style);
        }
    }

    /**
     * Método para escribir los datos de los alumnos en la hoja de Excel.
     */
    private void escribirDatosDeTabla() {
        int rowCount = 1; // Empieza en la segunda fila

        CellStyle style = crearEstiloCelda(12, false); // Estilo para los datos

        // Itera sobre la lista de alumnos y escribe sus datos en filas
        for (AlumnosNotas alumno : listarAlumnosNotas) {
            Row row = sheet.createRow(rowCount++);
            crearCelda(row, 0, alumno.getId(), style);
            crearCelda(row, 1, alumno.getNombre(), style);
            crearCelda(row, 2, alumno.getApellido(), style);
            crearCelda(row, 3, alumno.getSalon(), style);
            crearCelda(row, 4, alumno.getNumeroAula(), style);
            crearCelda(row, 5, alumno.getFechaNacimiento().toString(), style);
            crearCelda(row, 6, alumno.getNota1(), style);
            crearCelda(row, 7, alumno.getNota2(), style);
            crearCelda(row, 8, alumno.getNota3(), style);
            crearCelda(row, 9, alumno.getNota4(), style);
            crearCelda(row, 10, alumno.getNotafinal(), style);
        }
    }


    /**
     * Método para exportar el reporte a un archivo Excel.
     *
     * @param response Flujo de salida para escribir el archivo Excel.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public void export(HttpServletResponse response) throws IOException {
        escribirCabeceraDeTabla();
        escribirDatosDeTabla();

        // Ajustar automáticamente el tamaño de las columnas
        for (int i = 0; i < 11; i++) {
            sheet.autoSizeColumn(i);
        }

        // Escribir el archivo Excel en la respuesta HTTP
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
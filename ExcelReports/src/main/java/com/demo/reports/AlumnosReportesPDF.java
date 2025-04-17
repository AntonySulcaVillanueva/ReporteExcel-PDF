package com.demo.reports;

import com.demo.entity.AlumnosNotas;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Clase para generar reportes en formato PDF de los datos de la entidad AlumnosNotas.
 */
public class AlumnosReportesPDF {

    // Lista de alumnos que se incluirán en el reporte
    private final List<AlumnosNotas> listarAlumnosNotas;

    /**
     * Constructor que inicializa la lista de alumnos.
     *
     * @param listarAlumnosNotas Lista de alumnos a incluir en el reporte.
     */
    public AlumnosReportesPDF(List<AlumnosNotas> listarAlumnosNotas) {
        this.listarAlumnosNotas = listarAlumnosNotas;
    }

    /**
     * Escribe la cabecera de la tabla en el reporte PDF.
     *
     * @param table Tabla PDF a la que se agregarán las cabeceras.
     */
    private void escribirEncabezadoTabla(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE); // Color de fondo del encabezado
        cell.setPadding(5); // Espaciado interno de las celdas

        // Fuente para el texto del encabezado
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE); // Color del texto del encabezado

        // Encabezados de las columnas
        String[] encabezados = {"ID", "Nombre", "Apellido", "Salón", "Número Aula",
                "Fecha Nacimiento", "Nota 1", "Nota 2", "Nota 3",
                "Nota 4", "Nota Final"};

        for (String encabezado : encabezados) {
            cell.setPhrase(new Phrase(encabezado, fuente));
            table.addCell(cell);
        }
    }

    /**
     * Escribe los datos de los alumnos en la tabla del reporte PDF.
     *
     * @param table Tabla PDF a la que se agregarán los datos de los alumnos.
     */
    private void escribirDatosTabla(PdfPTable table) {
        for (AlumnosNotas alumno : listarAlumnosNotas) {
            table.addCell(String.valueOf(alumno.getId())); // ID del alumno
            table.addCell(alumno.getNombre()); // Nombre del alumno
            table.addCell(alumno.getApellido()); // Apellido del alumno
            table.addCell(alumno.getSalon()); // Salón del alumno
            table.addCell(alumno.getNumeroAula()); // Número del aula
            table.addCell(String.valueOf(alumno.getFechaNacimiento())); // Fecha de nacimiento
            table.addCell(String.valueOf(alumno.getNota1())); // Nota 1
            table.addCell(String.valueOf(alumno.getNota2())); // Nota 2
            table.addCell(String.valueOf(alumno.getNota3())); // Nota 3
            table.addCell(String.valueOf(alumno.getNota4())); // Nota 4
            table.addCell(String.valueOf(alumno.getNotafinal())); // Nota final calculada
        }
    }

    /**
     * Genera y exporta el reporte PDF con los datos de los alumnos.
     *
     * @param response Flujo de salida para escribir el archivo PDF.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public void generarYExportarReporte(HttpServletResponse response) throws IOException {
        Document documento = new Document(PageSize.A4); // Documento PDF con tamaño A4
        PdfWriter.getInstance(documento, response.getOutputStream()); // Inicializa el escritor
        documento.open(); // Abre el documento para escribir contenido

        // Título del reporte
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setSize(18);
        fuente.setColor(Color.BLUE);

        Paragraph titulo = new Paragraph("Reporte de Alumnos", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo); // Agrega el título al documento

        // Tabla con datos
        PdfPTable tabla = new PdfPTable(11); // 11 columnas
        tabla.setWidthPercentage(100f); // Usa el 100% del ancho
        tabla.setSpacingBefore(10); // Espaciado antes de la tabla

        escribirEncabezadoTabla(tabla); // Agrega el encabezado
        escribirDatosTabla(tabla); // Agrega los datos

        documento.add(tabla); // Agrega la tabla al documento
        documento.close(); // Cierra el documento
    }
}
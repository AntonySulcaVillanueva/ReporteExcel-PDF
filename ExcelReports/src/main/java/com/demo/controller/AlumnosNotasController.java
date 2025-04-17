package com.demo.controller;

import com.demo.entity.AlumnosNotas;
import com.demo.reports.AlumnosReporteExcel;
import com.demo.reports.AlumnosReportesPDF;
import com.demo.service.AlumnosService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/demo/gestion/alumnos")
public class AlumnosNotasController {

    @Autowired
    private AlumnosService alumnosService;

    // Listar todos los alumnos
    @GetMapping
    public String listarAlumnosNotas(Model model) {
        model.addAttribute("alumnos", alumnosService.listarAlumnosNotas());
        return "AlumnosNotas/listarAlumnos";
    }

    // Mostrar formulario para agregar un nuevo alumno
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoAlumno(Model model) {
        model.addAttribute("alumno", new AlumnosNotas());
        return "AlumnosNotas/nuevoAlumno";
    }

    // Guardar un nuevo alumno
    @PostMapping
    public String guardarAlumno(@ModelAttribute("alumno") AlumnosNotas alumnosNotas) {
        alumnosService.guardar(alumnosNotas);
        if(
                alumnosNotas.getNota1() != null &&
                alumnosNotas.getNota2() != null &&
                alumnosNotas.getNota3() != null &&
                alumnosNotas.getNota4() != null ){
            double notaFinal = (alumnosNotas.getNota1() +
                    alumnosNotas.getNota2() +
                    alumnosNotas.getNota3() +
                    alumnosNotas.getNota4()) / 4.0;
            alumnosNotas.setNotafinal(notaFinal);
        } else {
            alumnosNotas.setNotafinal(null); // Manejar el caso en que alguna nota sea nula
        }
        alumnosService.guardar(alumnosNotas);
        return "redirect:/demo/gestion/alumnos";
    }

    // Mostrar detalles de un alumno por ID
    @GetMapping("/{id}")
    public String mostrarDetalleAlumno(@PathVariable("id") Integer id, Model model) {
        Optional<AlumnosNotas> alumno = alumnosService.mostrarAlumnoPorId(id);
        if (alumno.isPresent()) {
            model.addAttribute("alumno", alumno.get());
            return "AlumnosNotas/detalleAlumno";
        } else {
            return "redirect:/demo/gestion/alumnos";
        }
    }

    // Mostrar formulario para editar un alumno
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarAlumno(@PathVariable("id") Integer id, Model model) {
        Optional<AlumnosNotas> alumno = alumnosService.mostrarAlumnoPorId(id);
        if (alumno.isPresent()) {
            model.addAttribute("alumno", alumno.get());
            return "AlumnosNotas/editarAlumno";
        } else {
            return "redirect:/demo/gestion/alumnos";
        }
    }

    // Actualizar un alumno
    @PostMapping("/editar/{id}")
    public String actualizarAlumno(@PathVariable("id") Integer id, @ModelAttribute("alumno") AlumnosNotas alumnosNotas) {
        // Establecer el ID del alumno
        alumnosNotas.setId(id);

        // Calcular la nota final antes de actualizar el alumno
        if (alumnosNotas.getNota1() != null &&
                alumnosNotas.getNota2() != null &&
                alumnosNotas.getNota3() != null &&
                alumnosNotas.getNota4() != null) {

            double notaFinal = (alumnosNotas.getNota1() +
                    alumnosNotas.getNota2() +
                    alumnosNotas.getNota3() +
                    alumnosNotas.getNota4()) / 4.0;
            alumnosNotas.setNotafinal(notaFinal);
        } else {
            alumnosNotas.setNotafinal(null); // Manejar el caso en que alguna nota sea nula
        }

        // Actualizar el alumno con la nota final calculada
        alumnosService.editarAlumnosNota(alumnosNotas);

        return "redirect:/demo/gestion/alumnos";
    }

    // Eliminar un alumno
    @GetMapping("/eliminar/{id}")
    public String eliminarAlumno(@PathVariable("id") Integer id) {
        alumnosService.eliminarAlumnosNota(id);
        return "redirect:/demo/gestion/alumnos";
    }

    @GetMapping("/export/pdf")
    public void exportPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_alumnos.pdf");

        List<AlumnosNotas> alumnosNotas = alumnosService.listarAlumnosNotas();
        new AlumnosReportesPDF(alumnosNotas).generarYExportarReporte(response);
    }

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_alumnos.xlsx");

        List<AlumnosNotas> alumnosNotas = alumnosService.listarAlumnosNotas();
        new AlumnosReporteExcel(alumnosNotas).export(response);
    }
}
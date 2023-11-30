package com.jh.test.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.jh.test.service.dto.AppUserDTO;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 * Service class for managing reports.
 * This service provides functionality to generate reports in PDF, Excel, and
 * Word formats.
 */
@Service
public class ReportService {

    private final DataSource dataSource;
    private final AppUserService appUserService;

    /**
     * Constructor for ReportService.
     * Initializes the service with the provided DataSource.
     *
     * @param dataSource the data source for database connection.
     */
    public ReportService(DataSource dataSource, AppUserService appUserService) {
        this.dataSource = dataSource;
        this.appUserService = appUserService;
    }

    /**
     * Generate a report in PDF format and encode it in Base64.
     * This method compiles a Jasper report template and fills it with data from the
     * database.
     *
     * @return a Base64 encoded string of the generated PDF report.
     * @throws Exception if there is an error during report generation.
     */
    public String generatePdfReport() throws Exception {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<AppUserDTO> appUserDTOs = appUserService.findAll(pageable).getContent();
        File file = ResourceUtils.getFile("classpath:reports/list_user.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream(file));
        Map<String, Object> parameters = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                    new JRBeanCollectionDataSource(appUserDTOs));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Configurar el exportador de PDF y la encriptación
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setEncrypted(true);
            configuration.set128BitKey(true);

            // formatos permitodos de rut
            // 12.345.678-9
            // 123456789
            // 12345678-9

            String userPassword = getLastDigitsOfRut("123456789", 5);
            System.out.println(userPassword);
            configuration.setUserPassword(userPassword); // Contraseña del usuario
            configuration.setOwnerPassword("ownerPassword"); // Contraseña del propietario

            exporter.setConfiguration(configuration);

            exporter.exportReport();

            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }
    }

    /**
     * Generate a report in Excel format and encode it in Base64.
     * This method compiles a Jasper report template, fills it with data, and
     * exports it to Excel format.
     *
     * @return a Base64 encoded string of the generated Excel report.
     * @throws Exception if there is an error during report generation.
     */
    public String generateExcelReport() throws Exception {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<AppUserDTO> appUserDTOs = appUserService.findAll(pageable).getContent();
        File file = ResourceUtils.getFile("classpath:reports/list_user.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream(file));
        Map<String, Object> parameters = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                    new JRBeanCollectionDataSource(appUserDTOs));

            JRXlsxExporter exporter = new JRXlsxExporter();
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true);
            exporter.setConfiguration(configuration);

            exporter.exportReport();
            return Base64.getEncoder().encodeToString(os.toByteArray());
        }
    }

    /**
     * Generate a report in Word format (DOCX) and encode it in Base64.
     * This method compiles a Jasper report template, fills it with data, and
     * exports it to Word format.
     *
     * @return a Base64 encoded string of the generated Word report.
     * @throws Exception if there is an error during report generation.
     */
    public String generateWordReport() throws Exception {
        File file = ResourceUtils.getFile("classpath:reports/list_user_query.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream(file));
        Map<String, Object> parameters = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            JRDocxExporter exporter = new JRDocxExporter();
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));

            SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
            exporter.setConfiguration(configuration);

            exporter.exportReport();
            return Base64.getEncoder().encodeToString(os.toByteArray());
        }
    }

    /**
     * Método para obtener los últimos dígitos del cuerpo del RUT.
     * 
     * @param rut        El RUT en formato chileno.
     * @param digitCount La cantidad de dígitos a extraer.
     * @return Los últimos 'digitCount' dígitos del cuerpo del RUT.
     */
    public static String getLastDigitsOfRut(String rut, int digitCount) {
        // Limpiar el RUT para conservar solo dígitos
        String cleanRut = rut.replaceAll("\\D+", "");

        // Asegurarse de que el RUT tenga suficiente longitud
        if (cleanRut.length() <= digitCount) {
            // Devolver el RUT completo (sin el dígito verificador) si es demasiado corto
            return cleanRut.substring(0, cleanRut.length() - 1);
        } else {
            // Devolver los últimos 'digitCount' dígitos del cuerpo del RUT
            return cleanRut.substring(cleanRut.length() - digitCount - 1, cleanRut.length() - 1);
        }
    }
}
package com.jh.test.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 * Service class for managing reports.
 * This service provides functionality to generate reports in PDF, Excel, and
 * Word formats.
 */
@Service
public class ReportService {

    private final DataSource dataSource;

    /**
     * Constructor for ReportService.
     * Initializes the service with the provided DataSource.
     *
     * @param dataSource the data source for database connection.
     */
    public ReportService(DataSource dataSource) {
        this.dataSource = dataSource;
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
        File file = ResourceUtils.getFile("classpath:reports/list_user.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream(file));
        Map<String, Object> parameters = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            byte[] pdfContent = JasperExportManager.exportReportToPdf(jasperPrint);
            return Base64.getEncoder().encodeToString(pdfContent);
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
        File file = ResourceUtils.getFile("classpath:reports/list_user.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream(file));
        Map<String, Object> parameters = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

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
        File file = ResourceUtils.getFile("classpath:reports/list_user.jrxml");
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
}

package com.jh.test.service;

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

/**
 * Service class for managing reports.
 */
@Service
public class ReportService {

    private final DataSource dataSource;

    /**
     * Constructor for ReportService.
     *
     * @param dataSource the data source for database connection.
     */
    public ReportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Generate a report in PDF format and encode it in Base64.
     *
     * @return a Base64 encoded string of the generated PDF report.
     * @throws Exception if there is an error during report generation.
     */
    public String generateReport() throws Exception {
        // Locate the .jrxml file
        File file = ResourceUtils.getFile("classpath:reports/list_user.jrxml");

        // Compile the report
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream(file));

        // Parameters for the report (if needed)
        Map<String, Object> parameters = new HashMap<>();

        // Connect to the database and fill the report
        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Export to PDF and encode in Base64
            byte[] pdfContent = JasperExportManager.exportReportToPdf(jasperPrint);
            return Base64.getEncoder().encodeToString(pdfContent);
        }
    }
}

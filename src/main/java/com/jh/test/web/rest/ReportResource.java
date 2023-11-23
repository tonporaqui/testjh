package com.jh.test.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jh.test.service.ReportService;

import tech.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing reports.
 * This controller provides endpoints to generate and retrieve reports in
 * different formats like PDF, Excel, and Word.
 */
@RestController
@RequestMapping("/api/report")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    private static final String ENTITY_NAME = "testjhReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportService reportService;

    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * {@code GET  /report/pdf} : Generate and get a report in PDF format.
     * This endpoint generates a report based on data and returns it in PDF format
     * encoded in Base64.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with the
     *         PDF report data,
     *         or with status {@code 500 (Internal Server Error)} if there was an
     *         error generating the PDF report.
     */
    @GetMapping("/pdf")
    public ResponseEntity<String> getPdfReport() {
        log.debug("REST request to get a PDF report");
        try {
            String report = reportService.generatePdfReport();
            return ResponseEntity.ok()
                    .headers(
                            HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
                                    "pdfReportGenerated"))
                    .body(report);
        } catch (Exception e) {
            log.error("REST request to get a PDF report failed: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .headers(HeaderUtil.createFailureAlert(applicationName, true, ENTITY_NAME, "pdfReportNotGenerated",
                            "There was an error generating the PDF report"))
                    .build();
        }
    }

    /**
     * {@code GET  /report/excel} : Generate and get a report in Excel format.
     * This endpoint generates a report based on data and returns it in Excel format
     * encoded in Base64.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with the
     *         Excel report data,
     *         or with status {@code 500 (Internal Server Error)} if there was an
     *         error generating the Excel report.
     */
    @GetMapping("/excel")
    public ResponseEntity<String> getExcelReport() {
        log.debug("REST request to get an Excel report");
        try {
            String report = reportService.generateExcelReport();
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
                            "excelReportGenerated"))
                    .body(report);
        } catch (Exception e) {
            log.error("REST request to get an Excel report failed: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .headers(
                            HeaderUtil.createFailureAlert(applicationName, true, ENTITY_NAME, "excelReportNotGenerated",
                                    "There was an error generating the Excel report"))
                    .build();
        }
    }

    /**
     * {@code GET  /report/word} : Generate and get a report in Word format.
     * This endpoint generates a report based on data and returns it in Word format
     * encoded in Base64.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with the
     *         Word report data,
     *         or with status {@code 500 (Internal Server Error)} if there was an
     *         error generating the Word report.
     */
    @GetMapping("/word")
    public ResponseEntity<String> getWordReport() {
        log.debug("REST request to get a Word report");
        try {
            String report = reportService.generateWordReport();
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
                            "wordReportGenerated"))
                    .body(report);
        } catch (Exception e) {
            log.error("REST request to get a Word report failed: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .headers(HeaderUtil.createFailureAlert(applicationName, true, ENTITY_NAME, "wordReportNotGenerated",
                            "There was an error generating the Word report"))
                    .build();
        }
    }
}

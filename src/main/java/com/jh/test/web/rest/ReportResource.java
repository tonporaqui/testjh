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
     * {@code GET  /report} : Generate and get a report.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with the
     *         report data,
     *         or with status {@code 500 (Internal Server Error)} if there was an
     *         error generating the report.
     */
    @GetMapping("")
    public ResponseEntity<String> getReport() {
        log.debug("REST request to get a report");
        try {
            String report = reportService.generateReport();
            return ResponseEntity.ok()
                    .headers(
                            HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, "reportGenerated"))
                    .body(report);
        } catch (Exception e) {
            log.error("REST request to get a report failed: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .headers(HeaderUtil.createFailureAlert(applicationName, true, ENTITY_NAME, "reportNotGenerated",
                            "There was an error generating the report"))
                    .build();
        }
    }
}

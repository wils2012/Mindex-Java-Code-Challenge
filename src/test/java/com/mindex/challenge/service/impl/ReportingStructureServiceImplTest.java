package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingStructureIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureIdUrl = "http://localhost:" + port + "/reportingStructure/{id}";
    }

    @Test
    public void testJohnReportingStructure() {
        final int JOHN_EXPECTED_REPORTS = 4;
        assertTrue(testReportingStructure("16a596ae-edd3-4847-99fe-c4518e82c86f", JOHN_EXPECTED_REPORTS));
    }

    @Test
    public void testRingoReportingStructure() {
        final int RINGO_EXPECTED_REPORTS = 2;
        assertTrue(testReportingStructure("03aa1462-ffa9-4978-901b-7c001562cf6f", RINGO_EXPECTED_REPORTS));
    }

    @Test
    public void testPeteReportingStructure() {
        final int PETE_EXPECTED_REPORTS = 0;
        assertTrue(testReportingStructure("62c1084e-6e34-4630-93fd-9153afb65309", PETE_EXPECTED_REPORTS));
    }

    public boolean testReportingStructure(String employeeId, int expectedNumberOfReports){
        ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructure.class, employeeId).getBody();
        return (reportingStructure.getNumberOfReports() == expectedNumberOfReports);
    }

    
}

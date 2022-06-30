package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Reading reportingStructure with id [{}]", id);

        //Get employee details from employeeService
        Employee employee = employeeService.read(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        //If employee exists, generate reportingStructure
        ReportingStructure reportingStructure = new ReportingStructure(employee);

        return reportingStructure;
    }

}

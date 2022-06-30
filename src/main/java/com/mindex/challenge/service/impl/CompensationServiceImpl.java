package com.mindex.challenge.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);
    private static final String COMPENSATION_DATASTORE_LOCATION = "src/main/resources/static/compensation_database.json";

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);

        Employee employee =  employeeService.read(compensation.getEmployeeId());

        //Do not add compensation if employee does not exist
        if(employee == null){ 
            throw new RuntimeException("Employee ID does not exist: " + compensation.getEmployeeId());
        }
        compensationRepository.insert(compensation);

        //After insert, persist DB state to resource JSON file
        String outString = "";
        try{
            outString = objectMapper.writeValueAsString(compensationRepository.findAll());
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        try{
            FileWriter fw = new FileWriter(COMPENSATION_DATASTORE_LOCATION);
            fw.write(outString);
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation with employeeId [{}]", id);

        Compensation compensation = compensationRepository.findCompensationByEmployeeId(id);

        if (compensation == null) {
            throw new RuntimeException("Invalid employeenId: " + id);
        }

        return compensation;
    }
}

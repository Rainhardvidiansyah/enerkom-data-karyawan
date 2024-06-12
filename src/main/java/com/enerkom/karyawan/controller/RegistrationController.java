package com.enerkom.karyawan.controller;

import com.enerkom.karyawan.dto.RegistrationDto;
import com.enerkom.karyawan.service.EmployeeService;
import com.enerkom.karyawan.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enerkom")
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto dto){
        if(dto.getEmail().isEmpty() && dto.getPassword().isEmpty()){
            return new ResponseEntity<>("Tidak berhasil mendaftar", HttpStatusCode.valueOf(400));
        }

        if(registrationService.findSameEmail(dto.getEmail())){
            return new ResponseEntity<>("Email sudah terdaftar", HttpStatusCode.valueOf(400));
        }

        var users = registrationService.registration(dto.getEmail(), dto.getPassword());
        var employee = employeeService.saveEmployee(users);
        log.info("Data employee: {}", employee);
        return new ResponseEntity<>("Berhasil mendaftar", HttpStatusCode.valueOf(200));
    }






}

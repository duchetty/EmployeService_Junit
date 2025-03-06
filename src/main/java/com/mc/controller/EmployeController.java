package com.mc.controller;

import com.mc.dto.EmployeDto;
import com.mc.entity.Employe;
import com.mc.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employe")
public class EmployeController {
    @Autowired
    private EmployeService service;
    @PostMapping("/save")
    public ResponseEntity<Employe> saveEmploye(@RequestBody EmployeDto dto)
    {
        Employe employe=service.fetchEmploye(dto);
        return new ResponseEntity<>(employe, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<Employe> getEmploye(@RequestParam Long id)
    {
        Employe employe=service.findEmployeById(id);
        return new ResponseEntity<>(employe, HttpStatus.OK);
    }

    @GetMapping("/getAllEmployes")
    public List<Employe> getAllEmployes()
    {
        return service.getAllEmploye();

    }

    @PutMapping("/update")
    public ResponseEntity<Employe> updateEmploye(@RequestParam long id, @RequestBody EmployeDto dto)
    {
        Employe employe=service.updateEmploye(id, dto);
        return new ResponseEntity<>(employe, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public void deleteEmploye(@RequestParam long id)
    {
        service.deleteEmploye(id);
    }
    
}

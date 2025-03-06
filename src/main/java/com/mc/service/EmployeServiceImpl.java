package com.mc.service;

import com.mc.dto.EmployeDto;
import com.mc.entity.Employe;
import com.mc.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeServiceImpl implements EmployeService{
    @Autowired
    private EmployeRepository repository;
    @Override
    public Employe fetchEmploye(EmployeDto dto) {
        Employe employe=Employe.builder().empName(dto.getEmpName())
                .email(dto.getEmail()).address(dto.getAddress())
                .salary(dto.getSalary()).build();
            repository.save(employe);
        return employe;
        }

    @Override
    public Employe findEmployeById(Long id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("Employe Not Found"));
    }

    @Override
    public List<Employe> getAllEmploye() {
        return repository.findAll();
    }

    @Override
    public Employe updateEmploye(Long id, EmployeDto dto) {
        Employe exEmp= repository.findById(id).orElse(null);
        exEmp.setEmpName(dto.getEmpName());
        exEmp.setEmail(dto.getEmail());
        exEmp.setSalary(dto.getSalary());
        exEmp.setAddress(dto.getAddress());
        return repository.save(exEmp);
    }

    @Override
    public void deleteEmploye(Long id) {
          repository.deleteById(id);
    }
}

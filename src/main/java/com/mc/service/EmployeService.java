package com.mc.service;


import com.mc.dto.EmployeDto;
import com.mc.entity.Employe;

import java.util.List;

public interface EmployeService {
    public Employe fetchEmploye(EmployeDto dto);
    public Employe findEmployeById(Long id);
    public List<Employe> getAllEmploye();
    public  Employe updateEmploye(Long id, EmployeDto dto);
    public void deleteEmploye(Long id);

}

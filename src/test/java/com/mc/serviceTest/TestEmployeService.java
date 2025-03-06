package com.mc.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mc.EmployeServiceApplication;
import com.mc.dto.EmployeDto;
import com.mc.entity.Employe;
import com.mc.repository.EmployeRepository;
import com.mc.service.EmployeService;

@SpringBootTest(classes = EmployeServiceApplication.class)
public class TestEmployeService {
	
	@MockBean 
	private EmployeRepository repository;
	
	@Autowired
	private EmployeService service;
	
	private Employe emp1;
	private Employe emp2;
	private EmployeDto empDto;
	private EmployeDto updateEmpDto;
	private Employe updateEmploye;
	
	@BeforeEach
	public void setUp()
	{
		
		empDto=new EmployeDto("devendra","devendra@gmail.com","chittoor","90000");
		emp1= new Employe(101,empDto.getEmpName(),empDto.getAddress(),empDto.getSalary(),empDto.getEmail());
		emp2= new Employe(102,"rajesh","rajesh@gmail.com","chittoor","79000");
		updateEmpDto=new EmployeDto("devendra","devendra@gmail.com","chittoor","60000");
		updateEmploye=new Employe(101,updateEmpDto.getEmpName(),updateEmpDto.getAddress(),updateEmpDto.getSalary(),updateEmpDto.getEmail());
	}
	
	@Test
	public void testFetchEmploye()
	{
		when(repository.save(any(Employe.class))).thenReturn(emp1);
		
		Employe employe=service.fetchEmploye(empDto);
		assertNotNull(employe);
		assertEquals("devendra",employe.getEmpName());
		verify(repository, times(1)).save(employe);
	}
	
	@Test
	public void testFindEmployeById_Found()
	{
		when(repository.findById(102L)).thenReturn(Optional.of(emp2));
		Employe employe=service.findEmployeById(102l);
		assertEquals("rajesh",employe.getEmpName());
		assertEquals("chittoor",employe.getAddress());
		verify(repository,times(1)).findById(102l);
		
	}
	@Test
	public void testFindEmployeById_NotFound()
	{
		//Mock repository to return empty Optional
		when(repository.findById(105L)).thenReturn(Optional.empty( ));
		//Expect RuntimeException when calling findEmployeById
		Exception exception=assertThrows(RuntimeException.class, ()-> {service.findEmployeById(105l);});
		//Check if the correct exception message is thrown
		assertEquals("Employe Not Found", exception.getMessage());
		//Verify that repository.findById(id) was called once
		verify(repository,times(1)).findById(105l);
		
	}
	
	@Test
	public void testGetAllEmploye()
	{
		when(repository.findAll()).thenReturn(Arrays.asList(emp1,emp2));
		List<Employe> list=service.getAllEmploye();
		assertNotNull(list);
		verify(repository,times(1)).findAll();
	}
	
	@Test
	public void testUpdateEmploye_Found()
	{
		when(repository.findById(101L)).thenReturn(Optional.of(emp1));
		when(repository.save(any(Employe.class))).thenReturn(emp1);
		
		Employe updateEmploye=service.updateEmploye(101L, updateEmpDto);
		
		assertNotNull(updateEmploye);
		assertEquals("60000",updateEmploye.getSalary());
		verify(repository,times(1)).findById(101l);
		verify(repository,times(1)).save(updateEmploye);
		
	}
	/*@Test
	public void testUpdateEmploye_NotFound()
	{
		when(repository.findById(101L)).thenReturn(Optional.empty());
	
		Employe updateEmploye=service.updateEmploye(101L, updateEmpDto);
		
		assertNull(updateEmploye);
		verify(repository,times(1)).findById(101L);
		verify(repository,times(1)).save(any(Employe.class)); 
		
	}*/
	@Test
	public void testDeleteEmploye()
	{
		
		service.deleteEmploye(101L);
		verify(repository,times(1)).deleteById(101L);
	}
	
	
}

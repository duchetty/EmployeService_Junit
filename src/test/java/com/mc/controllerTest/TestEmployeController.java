package com.mc.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mc.controller.EmployeController;
import com.mc.dto.EmployeDto;
import com.mc.entity.Employe;
import com.mc.service.EmployeService;

@WebMvcTest(value=EmployeController.class)
public class TestEmployeController {

	@MockBean
	private EmployeService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	private Employe emp1;
	private Employe emp2;
	private Employe emp2Updated;
	
	@BeforeEach
	public void setUp()
	{
		emp1=new Employe(1,"mohan","mohan@gmail.com","bangalore","87000");
		emp2=new Employe(2,"vani","vani@gmail.com","bangalore","56000");
	}
	
	@Test
	public void testSaveEmploye() throws Exception
	{
		
		when(service.fetchEmploye(any(EmployeDto.class))).thenReturn(emp1);
		
		String empJson=mapper.writeValueAsString(emp1);
		
		MockHttpServletRequestBuilder reqBuilder=MockMvcRequestBuilders.post("/employe/save").contentType(MediaType.APPLICATION_JSON).content(empJson);
		
		int status=mockMvc.perform(reqBuilder).andReturn().getResponse().getStatus();
		assertEquals("mohan", emp1.getEmpName());
		assertEquals(201, status);
		
	}
	
	@Test
	public void testGetEmploye() throws Exception
	{
		when(service.findEmployeById(2l)).thenReturn(emp2);
		
		MockHttpServletRequestBuilder reqBuilder=MockMvcRequestBuilders.get("/employe/get?id=2");
		
		int status=mockMvc.perform(reqBuilder).andReturn().getResponse().getStatus();
		assertEquals(2,emp2.getId());
		assertEquals("vani",emp2.getEmpName());
		assertEquals(200,status);
		
	}
	
	@Test
	public void testGetAllEmploye() throws Exception
	{
		when(service.getAllEmploye()).thenReturn(Arrays.asList(emp1,emp2));
		
		MockHttpServletRequestBuilder reqBuilder=MockMvcRequestBuilders.get("/employe/getAllEmployes");
		
		int status=mockMvc.perform(reqBuilder).andReturn().getResponse().getStatus();
		assertEquals(200,status);
	}
	
	@Test
	public void testUpdaeEmploye() throws Exception
	{
		
		emp2Updated=new Employe(2,"vani updated","vani@gmail.com","bangalore","56000");
		
		
		when(service.updateEmploye(eq(2L), any(EmployeDto.class))).thenReturn(emp2Updated);
		
		String updatedempJson=mapper.writeValueAsString(emp2Updated);
		MockHttpServletRequestBuilder reqBuilder=MockMvcRequestBuilders.put("/employe/update?id=2").contentType(MediaType.APPLICATION_JSON).content(updatedempJson);
		
		int status=mockMvc.perform(reqBuilder).andReturn().getResponse().getStatus();
		assertEquals("vani updated" ,emp2Updated.getEmpName());
		assertEquals(201,status);
	}
	
	@Test
	public void testDeleteEmploye() throws Exception
	{
		doNothing().when(service).deleteEmploye(2L);
		int status=mockMvc.perform(MockMvcRequestBuilders.delete("/employe/delete?id=2")).andReturn().getResponse().getStatus();
		assertEquals(200,status);
		
	}
		
}

package com.recruitment.datalake.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.datalake.entities.Employe;
import com.recruitment.datalake.service.EmployeService;

@RestController
@RequestMapping("/api/employes")
public class EmployeRESTController {

	 @Autowired
	    private EmployeService employeService;

	    @PostMapping
	    public Employe createEmploye(@RequestBody Employe employe) {
	        return employeService.saveEmploye(employe);
	    }

	    @GetMapping("/{id}")
	    public Employe getEmployeById(@PathVariable Long id) {
	        return employeService.getEmployeById(id);
	    }

	    @GetMapping
	    public List<Employe> getAllEmployes() {
	        return employeService.getAllEmployes();
	    }

	    @PutMapping("/{id}")
	    public Employe updateEmploye(@PathVariable Long id, @RequestBody Employe employe) {
	        return employeService.updateEmploye(employe);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteEmploye(@PathVariable Long id) {
	        employeService.deleteEmploye(id);
	    }

	    @GetMapping("/search")
	    public List<Employe> findByFirstName(@RequestParam String firstName) {
	        return employeService.findByFirstName(firstName);
	    }

	    @GetMapping("/company")
	    public List<Employe> findByCompany(@RequestParam String company) {
	        return employeService.findByCompany(company);
	    }
} 

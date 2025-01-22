package com.recruitment.datalake.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin(origins = "http://localhost:4200")
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
	    public ResponseEntity<Employe> updateEmploye(@PathVariable Long id, @RequestBody Employe employe) {
	        // Ensure the employee ID in the body matches the path ID
	        employe.setId(id);  

	        // Update the employee by calling the service
	        Employe updatedEmploye = employeService.updateEmploye(employe);

	        // Check if the employee was updated
	        if (updatedEmploye != null) {
	            // Return the updated employee with a 200 OK response
	            return ResponseEntity.ok(updatedEmploye);
	        } else {
	            // Return 404 Not Found if the employee does not exist
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
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

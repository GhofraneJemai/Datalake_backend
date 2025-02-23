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
		 public ResponseEntity<Employe> createEmploye(@RequestBody Employe employe) {
		     // Vérifiez si l'employé existe déjà, par exemple en fonction de son email ou d'un autre attribut unique
		     Employe existingEmploye = employeService.findByEmail(employe.getEmail());  // Exemple avec l'email
	
		     if (existingEmploye != null) {
		         // Si l'employé existe déjà, retourner une réponse avec un message d'erreur
		         return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		     }
	
		     // Si l'employé n'existe pas, le créer et le sauvegarder
		     Employe savedEmploye = employeService.saveEmploye(employe);
		     return ResponseEntity.status(HttpStatus.CREATED).body(savedEmploye);
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
	        // Vérifier si l'email est déjà utilisé par un autre employé
	        Employe existingEmploye = employeService.findByEmail(employe.getEmail());
	        
	        if (existingEmploye != null && !existingEmploye.getId().equals(id)) {
	            // Si un autre employé utilise déjà cet email, retourner une erreur
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	        }

	        // S'assurer que l'ID de l'employé correspond à celui du path
	        employe.setId(id);

	        // Mise à jour de l'employé
	        Employe updatedEmploye = employeService.updateEmploye(employe);

	        if (updatedEmploye != null) {
	            return ResponseEntity.ok(updatedEmploye);
	        } else {
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

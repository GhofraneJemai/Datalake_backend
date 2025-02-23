package com.recruitment.datalake;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.recruitment.datalake.entities.Employe;
import com.recruitment.datalake.repos.EmployeRepository;
import com.recruitment.datalake.service.EmployeService;

import jakarta.transaction.Transactional;

@SpringBootTest
class DatalakeApplicationTests {

    @Autowired
    private EmployeRepository EmployeRepository;
    @Autowired
    private EmployeService employeService;

    @Test
    public void testCreateEmploye() {
        Employe emp = new Employe("John", "Doe", "john.doe@example.com", new Date(), "Male", "Master's", "TechCorp", 5, 75000.0);
        EmployeRepository.save(emp);
        assertNotNull(emp.getId());
    }

    @Test
    public void testFindEmployeById() {
        Employe emp = EmployeRepository.findById(1L).orElse(null);
        assertNotNull(emp);
        System.out.println(emp);
    }

    @Test
    public void testUpdateEmploye() {
        Employe emp = EmployeRepository.findById(13L).orElse(null);
        assertNotNull(emp);
        emp.setCompany("GlobalTech");
        EmployeRepository.save(emp);

        Employe updatedEmp = EmployeRepository.findById(13L).get();
        assertEquals("GlobalTech", updatedEmp.getCompany());
    }

    @Test
    public void testDeleteEmploye() {
        EmployeRepository.deleteById(1L);
        Employe emp = EmployeRepository.findById(1L).orElse(null);
        assertNull(emp);
    }

    @Test
    public void testListAllEmployes() {
        List<Employe> Employes = EmployeRepository.findAll();
        Employes.forEach(System.out::println);
    }

    @Test
    public void testFindByFirstName() {
        List<Employe> Employes = EmployeRepository.findByFirstName("John");
        Employes.forEach(System.out::println);
    }

    @Test
    public void testFindByCompany() {
        List<Employe> Employes = EmployeRepository.findByCompany("TechCorp");
        Employes.forEach(System.out::println);
    }

    @Test
    public void testFindByExperienceGreaterThan() {
        List<Employe> Employes = EmployeRepository.findByExperienceGreaterThanEqual(3);
        Employes.forEach(System.out::println);
    }

    @Test
    public void testOrderBySalaryPackageDesc() {
        List<Employe> Employes = EmployeRepository.findByOrderBySalaryPackageDesc();
        Employes.forEach(System.out::println);
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testCreateEmploye_whenEmailAlreadyExists() {
        // Création d'un employé existant
        Employe existingEmploye = new Employe();
        existingEmploye.setFirstName("Martin");
        existingEmploye.setLastName("Paul");
        existingEmploye.setEmail("paul.martin@example.com");

        EmployeRepository.save(existingEmploye);

        // Vérification que l'email est bien enregistré
        Employe foundEmploye = employeService.findByEmail("paul.martin@example.com");
        assertNotNull(foundEmploye);
        assertEquals(existingEmploye.getEmail(), foundEmploye.getEmail());

        // Tentative d'ajouter un employé avec le même email (devrait lever une exception)
        Employe duplicateEmploye = new Employe();
        duplicateEmploye.setFirstName("Martin");
        duplicateEmploye.setLastName("Paul");
        duplicateEmploye.setEmail("paul.martin@example.com");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeService.saveEmploye(duplicateEmploye);
        });

        // Vérification que l'exception contient un message d'erreur approprié
        String expectedMessage = "Un employé avec cet email existe déjà";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    @Transactional
    @Rollback(true)
    public void testFindByEmail() {
        // Création et sauvegarde d'un employé avec un email unique
        Employe emp = new Employe();
        emp.setFirstName("Alice");
        emp.setLastName("Johnson");
        emp.setEmail("alice.johnson@example.com");
        EmployeRepository.save(emp);

        // Recherche de l'employé par email
        Employe foundEmp = employeService.findByEmail("alice.johnson@example.com");

        // Vérification que l'employé est bien trouvé
        assertNotNull(foundEmp);
        assertEquals("alice.johnson@example.com", foundEmp.getEmail());
        assertEquals("Alice", foundEmp.getFirstName());
        assertEquals("Johnson", foundEmp.getLastName());
    }
} 

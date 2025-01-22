package com.recruitment.datalake;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.recruitment.datalake.entities.Employe;
import com.recruitment.datalake.repos.EmployeRepository;

@SpringBootTest
class DatalakeApplicationTests {

    @Autowired
    private EmployeRepository EmployeRepository;

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
} 

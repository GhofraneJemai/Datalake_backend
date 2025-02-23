package com.recruitment.datalake.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recruitment.datalake.entities.Employe;
import com.recruitment.datalake.repos.EmployeRepository;

import java.util.List;

@Service
public class EmployeServiceImpl implements EmployeService {

    @Autowired
    private EmployeRepository EmployeRepository;

    @Override
    public Employe saveEmploye(Employe emp) {
        return EmployeRepository.save(emp);
    }

    @Override
    public Employe updateEmploye(Employe emp) {
        // Check if the employee exists
        Employe existingEmp = EmployeRepository.findById(emp.getId()).orElse(null);
        if (existingEmp != null) {
            // Update the existing employee's fields
            existingEmp.setFirstName(emp.getFirstName());
            existingEmp.setLastName(emp.getLastName());
            existingEmp.setEmail(emp.getEmail());
            existingEmp.setDob(emp.getDob());
            existingEmp.setGender(emp.getGender());
            existingEmp.setEducation(emp.getEducation());
            existingEmp.setCompany(emp.getCompany());
            existingEmp.setExperience(emp.getExperience());
            existingEmp.setSalaryPackage(emp.getSalaryPackage());
            
            // Save the updated employee
            return EmployeRepository.save(existingEmp);
        } else {
            // If the employee doesn't exist, handle the case (optional)
            return null;  // Or throw an exception to return a 404 error in the controller
        }
    }


    @Override
    public void deleteEmploye(Long id) {
        EmployeRepository.deleteById(id);
    }

    @Override
    public Employe getEmployeById(Long id) {
        return EmployeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employe> getAllEmployes() {
        return EmployeRepository.findAll();
    }

    @Override
    public List<Employe> findByFirstName(String firstName) {
        return EmployeRepository.findByFirstName(firstName);
    }

    @Override
    public List<Employe> findByCompany(String company) {
        return EmployeRepository.findByCompany(company);
    }

    @Override
    public List<Employe> findByExperienceGreaterThanEqual(int experience) {
        return EmployeRepository.findByExperienceGreaterThanEqual(experience);
    }

    @Override
    public List<Employe> findHighEarners(double salaryThreshold) {
        return EmployeRepository.findHighEarners(salaryThreshold);
    }

    @Override
    public List<Employe> sortByFirstNameAndCompany() {
        return EmployeRepository.sortByFirstNameAndCompany();
    }
    @Override
    public Employe findByEmail(String email) {
        return EmployeRepository.findByEmail(email); // Implémentation dépendante de votre repository
    }

} 

package com.recruitment.datalake.service;

import java.util.List;

import com.recruitment.datalake.entities.Employe;

public interface EmployeService {
	Employe saveEmploye(Employe Employe);

    Employe updateEmploye(Employe Employe);

    void deleteEmploye(Long id);

    Employe getEmployeById(Long id);

    List<Employe> getAllEmployes();

    List<Employe> findByFirstName(String firstName);

    List<Employe> findByCompany(String company);

    List<Employe> findByExperienceGreaterThanEqual(int experience);

    List<Employe> findHighEarners(double salaryThreshold);

    List<Employe> sortByFirstNameAndCompany();

}

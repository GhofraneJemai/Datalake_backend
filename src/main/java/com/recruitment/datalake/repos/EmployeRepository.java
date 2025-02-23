package com.recruitment.datalake.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.recruitment.datalake.entities.Employe;
@CrossOrigin(origins = "http://localhost:4200")
public interface EmployeRepository extends JpaRepository<Employe, Long> {
	List<Employe> findByFirstName(String firstName);

    List<Employe> findByFirstNameContaining(String keyword);

    List<Employe> findByCompany(String company);

    List<Employe> findByOrderByFirstNameAsc();
    Employe findByEmail(String email); 

    @Query("SELECT e FROM Employe e ORDER BY e.firstName ASC, e.company ASC")
    List<Employe> sortByFirstNameAndCompany();

    List<Employe> findByEducation(String education);

    List<Employe> findByExperienceGreaterThanEqual(int experience);

    @Query("SELECT e FROM Employe e WHERE e.salaryPackage >= ?1 ORDER BY e.salaryPackage DESC")
    List<Employe> findHighEarners(double salaryThreshold);

    // Correction : ajout de cette méthode manquante
    List<Employe> findByOrderBySalaryPackageDesc();

}

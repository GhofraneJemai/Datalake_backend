package com.recruitment.datalake.entities;


import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employe {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 private String firstName;
	 private String lastName;
	 private String email;
	 private Date dob;
	 private String gender;
	 private String education;
	 private String company;
	 private int experience;
	 private double salaryPackage;
	public Employe() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employe(String firstName, String lastName, String email, Date dob, String gender, String education,
			String company, int experience, double salaryPackage) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dob = dob;
		this.gender = gender;
		this.education = education;
		this.company = company;
		this.experience = experience;
		this.salaryPackage = salaryPackage;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public double getSalaryPackage() {
		return salaryPackage;
	}
	public void setSalaryPackage(double salaryPackage) {
		this.salaryPackage = salaryPackage;
	}
	
	
	@Override
	public String toString() {
		return "Employe [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", dob=" + dob + ", gender=" + gender + ", education=" + education + ", company=" + company
				+ ", experience=" + experience + ", salaryPackage=" + salaryPackage + "]";
	}
	 

}

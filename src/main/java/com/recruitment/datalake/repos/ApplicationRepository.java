package com.recruitment.datalake.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.datalake.entities.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

}

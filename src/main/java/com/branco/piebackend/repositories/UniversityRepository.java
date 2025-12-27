package com.branco.piebackend.repositories;

import com.branco.piebackend.entities.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniversityRepository extends JpaRepository<UniversityEntity, Long> {

}

package com.pet.shelter.repository;

import com.pet.shelter.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatTableRepository extends JpaRepository<Cat,Long> {


//List<CatTable> findAllByCatTable(long id);
}
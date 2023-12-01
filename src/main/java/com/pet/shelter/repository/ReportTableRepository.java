package com.pet.shelter.repository;

import com.pet.shelter.entity.ReportTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public  interface ReportTableRepository extends JpaRepository<ReportTable, Long> {
    List<ReportTable>findAllByChatId(long id);


}

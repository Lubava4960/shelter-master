package com.pet.shelter.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pengrad.telegrambot.model.Chat;
import com.pet.shelter.entity.ReportTable;
import com.pet.shelter.repository.ReportTableRepository;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ReportTableService {
  private final ReportTableRepository reportTableRepository;

  public ReportTableService(ReportTableRepository reportTableRepository) {
    this.reportTableRepository = reportTableRepository;
  }
  public void save(ReportTable reportTable){

    reportTableRepository.save(reportTable);
  }
  public byte[] getImage(int id) {
    ReportTable reportTable= (ReportTable) reportTableRepository.findAllByChatId( id);
    return reportTable.getImage();
  }
}
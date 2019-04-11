package com.ttn.reap.repositories;

import com.ttn.reap.entities.Recognition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecognitionRepository extends CrudRepository<Recognition, Integer> {
    List<Recognition> findAll();

    List<Recognition> findRecognitionByReceiverName(String receiverName);

    List<Recognition> findByDateBetween(LocalDate fromDate, LocalDate toDate);

    List<Recognition> findByDate(LocalDate date);

    List<Recognition> findByReceiverName(String receiverName);

    List<Recognition> findBySenderName(String senderName);
}

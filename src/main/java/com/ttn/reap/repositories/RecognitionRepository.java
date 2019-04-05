package com.ttn.reap.repositories;

import com.ttn.reap.entities.Recognition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RecognitionRepository extends CrudRepository<Recognition, Integer> {
    List<Recognition> findAll();
    
    List<Recognition> findRecognitionByReceiverName(String receiverName);
    
    List<Recognition> findByDateBetween(Date to, Date from);
}

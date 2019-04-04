package com.ttn.reap.repositories;

import com.ttn.reap.entities.Recognition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecognitionRepository extends CrudRepository<Recognition, Integer> {
    List<Recognition> findAll();
}

package com.inovagab.inovagab.repository;

import com.inovagab.inovagab.model.Estrategia;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EstrategiaRepository extends MongoRepository<Estrategia, String> {

    List<Estrategia> findByAtivaTrue();
}
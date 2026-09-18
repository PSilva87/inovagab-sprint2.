package com.inovagab.inovagab.repository;

import com.inovagab.inovagab.model.RegistroAuditoria;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistroAuditoriaRepository extends MongoRepository<RegistroAuditoria, String> {
}

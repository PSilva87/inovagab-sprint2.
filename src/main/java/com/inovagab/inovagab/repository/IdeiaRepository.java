package com.inovagab.inovagab.repository;
import com.inovagab.inovagab.model.Ideia;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
public interface IdeiaRepository extends MongoRepository<Ideia,String>{ List<Ideia> findByUsuarioId(String usuarioId); }

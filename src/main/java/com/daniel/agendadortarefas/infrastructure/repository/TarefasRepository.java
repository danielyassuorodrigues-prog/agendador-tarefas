package com.daniel.agendadortarefas.infrastructure.repository;

import com.daniel.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.daniel.agendadortarefas.infrastructure.enums.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TarefasRepository extends MongoRepository<TarefasEntity, String> {

    List<TarefasEntity> findByDataEventoBetweenAndStatus(LocalDateTime dataInicial, LocalDateTime dataFinal, Status status);


    List<TarefasEntity> findByEmailUsuario(String email);


}

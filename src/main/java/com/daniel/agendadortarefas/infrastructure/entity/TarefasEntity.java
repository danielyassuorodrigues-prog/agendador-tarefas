package com.daniel.agendadortarefas.infrastructure.entity;


import com.daniel.agendadortarefas.infrastructure.enums.Status;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Document("tarefa")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefasEntity {
    @Id
    private String id;

    private String nomeTarefa;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEvento;
    private String emailUsuario;
    private LocalDateTime dataAlteracao;
    private Status status;
}

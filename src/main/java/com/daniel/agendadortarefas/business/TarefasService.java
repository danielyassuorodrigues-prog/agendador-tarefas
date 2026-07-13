package com.daniel.agendadortarefas.business;


import com.daniel.agendadortarefas.business.dto.TarefasDTO;
import com.daniel.agendadortarefas.business.mapper.TarefasConverter;
import com.daniel.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.daniel.agendadortarefas.infrastructure.enums.Status;
import com.daniel.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.daniel.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {
    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefas( String token ,TarefasDTO dto){
        String email = jwtUtil.extraitEmailToken(token.substring(7));
        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatus(Status.PENDENTE);

        TarefasEntity tarefasEntity = tarefasConverter.paraTarefaEntity(dto);

        return tarefasConverter.paraTarefaDTO(tarefasRepository.save(tarefasEntity));
    }


}

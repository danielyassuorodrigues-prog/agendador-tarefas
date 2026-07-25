package com.daniel.agendadortarefas.business;


import com.daniel.agendadortarefas.business.dto.TarefasDTO;
import com.daniel.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.daniel.agendadortarefas.business.mapper.TarefasConverter;
import com.daniel.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.daniel.agendadortarefas.infrastructure.enums.Status;
import com.daniel.agendadortarefas.infrastructure.exceptions.ResourcesNotFoundException;
import com.daniel.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.daniel.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {
    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefasDTO gravarTarefas( String token ,TarefasDTO dto){
        String email = jwtUtil.extraitEmailToken(token.substring(7));
        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatus(Status.PENDENTE);

        TarefasEntity tarefasEntity = tarefasConverter.paraTarefaEntity(dto);

        return tarefasConverter.paraTarefaDTO(tarefasRepository.save(tarefasEntity));
    }

    public List<TarefasDTO> buscaTarefasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){
        return tarefasConverter.paraListaTarefasDTO(tarefasRepository.findByDataEventoBetweenAndStatus(dataInicial, dataFinal, Status.PENDENTE ));

    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token){
        String email = jwtUtil.extraitEmailToken(token.substring(7));
        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefasConverter.paraListaTarefasDTO(listaTarefas);
    }

    public void deletaTarefaPorId(String id){

        try{
            tarefasRepository.deleteById(id);
        }catch (ResourcesNotFoundException e){
            throw new ResourcesNotFoundException("Erro ao deletar tarefa, ID não existente " + id,  e.getCause());
        }

    }

    public TarefasDTO alteraStatus (Status status, String id){
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourcesNotFoundException("Tarefa não encontrada "));

            entity.setStatus(status);
            return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourcesNotFoundException e) {
            throw new ResourcesNotFoundException("Erro ao alterar Status da tarefa " + e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id){
        try{
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourcesNotFoundException("Tarefa não encontrada "));
            tarefaUpdateConverter.updateTarefas(dto , entity);
            return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourcesNotFoundException e) {
            throw new ResourcesNotFoundException("Erro ao alterar os dados da tarefa " + e.getCause());
        }

    }









}

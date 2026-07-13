package com.daniel.agendadortarefas.infrastructure.exceptions;

public class ResourcesNotFoundException extends RuntimeException {
    public ResourcesNotFoundException(String message) {
        super(message);
    }
    public ResourcesNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}

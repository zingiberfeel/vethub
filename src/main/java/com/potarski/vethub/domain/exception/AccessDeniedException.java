package com.potarski.vethub.domain.exception;


/* Uncheked-исключение, которое выбрасывается,
если пользователь пытается получить доступ к данным
животного другого пользователя */

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException() {
        super();
    }
}

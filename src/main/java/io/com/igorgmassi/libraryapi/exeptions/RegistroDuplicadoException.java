package io.com.igorgmassi.libraryapi.exeptions;

public class RegistroDuplicadoException extends RuntimeException{
    public RegistroDuplicadoException(String message){
        super(message);
    }
}

package br.com.rocketseat.todolist.error;

public class UnauthenticatedUserException extends RuntimeException{

    public UnauthenticatedUserException(String message){
        super(message);
    }

}

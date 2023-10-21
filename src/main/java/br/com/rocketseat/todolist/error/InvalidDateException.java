package br.com.rocketseat.todolist.error;

public class InvalidDateException extends RuntimeException {

    public InvalidDateException(String message){
        super(message);
    }
}

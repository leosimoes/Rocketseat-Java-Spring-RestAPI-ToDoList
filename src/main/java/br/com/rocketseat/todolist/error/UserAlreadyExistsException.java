package br.com.rocketseat.todolist.error;

public class UserAlreadyExistsException extends RuntimeException  {

    public UserAlreadyExistsException(String message){
        super(message);
    }

}

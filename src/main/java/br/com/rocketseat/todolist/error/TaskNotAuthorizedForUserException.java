package br.com.rocketseat.todolist.error;

public class TaskNotAuthorizedForUserException extends RuntimeException{

    public TaskNotAuthorizedForUserException(String message){
        super(message);
    }

}

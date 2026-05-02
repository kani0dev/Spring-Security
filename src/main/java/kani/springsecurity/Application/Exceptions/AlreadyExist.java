package kani.springsecurity.Application.Exceptions;

public class AlreadyExist extends RuntimeException{
    public AlreadyExist(String message){
        super(message);
    }
}

package kani.springsecurity.Application.Exceptions;

public class EmptyProfile extends RuntimeException{
    public EmptyProfile(String message){
        super(message);
    }
}

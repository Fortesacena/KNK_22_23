package services.exceptions;

public class UserAlreadyExists extends Exception{
    public UserAlreadyExists(String errorMessage){
        super(errorMessage);
    }
}
package services.exceptions;


public class IncorrectPassword extends Exception{
    public IncorrectPassword(String errorMessage){
        super(errorMessage);
    }
}
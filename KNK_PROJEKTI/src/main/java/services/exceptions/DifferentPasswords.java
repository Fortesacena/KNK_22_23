package services.exceptions;

public class DifferentPasswords extends Exception{
    public DifferentPasswords(String errorMessage){
        super(errorMessage);
    }
}
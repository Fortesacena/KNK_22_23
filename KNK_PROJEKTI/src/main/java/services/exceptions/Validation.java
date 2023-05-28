package services.exceptions;

public class Validation extends Exception{
    public Validation(String errorMessage){
        super(errorMessage);
    }
}
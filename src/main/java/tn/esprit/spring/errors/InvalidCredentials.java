package tn.esprit.spring.errors;

public class InvalidCredentials extends RuntimeException{
    private static final long serialVerisionUID = 4;
    public InvalidCredentials(String message) {
        super(message);
    }
}

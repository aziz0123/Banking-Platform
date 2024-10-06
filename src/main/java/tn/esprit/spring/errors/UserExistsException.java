package tn.esprit.spring.errors;

public class UserExistsException extends RuntimeException{
    private static final long serialVerisionUID = 3;
    public UserExistsException(String message) {
        super(message);
    }
}

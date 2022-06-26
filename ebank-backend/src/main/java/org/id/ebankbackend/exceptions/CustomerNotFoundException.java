package org.id.ebankbackend.exceptions;

public class CustomerNotFoundException extends Exception {


   /* des exception metier*/
    public CustomerNotFoundException(String message) {
        super(message);
    }
}

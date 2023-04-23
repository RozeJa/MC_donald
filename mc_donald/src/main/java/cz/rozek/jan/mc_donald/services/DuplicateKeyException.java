package cz.rozek.jan.mc_donald.services;

public class DuplicateKeyException extends Exception {
    public DuplicateKeyException(String message) {
        super(message);
    }
}

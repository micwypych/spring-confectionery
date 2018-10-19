package pl.edu.agh.kis.databases.confectionery.domain;

public class ChocolateNotFoundException extends RuntimeException {
    public ChocolateNotFoundException(String id) {
        super("id-"+id);
    }
}

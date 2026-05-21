package models;
import java.time.LocalDate;

public class ItemFrigobar extends Item {

    public ItemFrigobar(String id, String nome, LocalDate validade, double valor) {
        super(id, nome, validade, valor);
    }
    
}

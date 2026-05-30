package com.smarthotel.gui;

import com.smarthotel.models.*;
import com.smarthotel.dados.*;

/*import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;*/

public class TesteConsole {
    public static void main(String[] args) {

        /* 
        // inicializando frigobar do quarto
        Item agua = new Item("Água mineral", LocalDate.of(2030, 01, 01), 8.99); //9 real numa água é osso // preço de hotel

        Item chocolate = new Item("Barra de chocolate com super amêndoas", LocalDate.of(2028, 04, 12), 23.99); //Das pequenas tá

        Frigobar frigobar01 = new Frigobar();
        frigobar01.adicionarItem(agua);
        frigobar01.adicionarItem(chocolate);
        frigobar01.removerItem(agua);

        // criando um quarto
        Quarto quarto01= new QuartoPadrao("01", 2, 5);
        quarto01.setFrigobar(frigobar01);


        // criando um responsável
        Responsavel joel = new Responsavel("Joel", "123.456.789-00", LocalDate.of(1990, 5, 20), "0800-5000",
                                            "joel@gmail.com","cartao de credito");

        // criando hospedes
        Pessoa taylor= new Hospede("Taylor Swift", "987.654.321-00", LocalDate.of(1995, 8, 15), "0800-6000", "swift@hotmail.com", " ", " ");
        Pessoa sergio= new Hospede("Sergio", "111.111.111-12", LocalDate.of(1985, 3, 10), "0800-7000", "sergio@gmail.com", " ", " ");
        
        ArrayList<Hospede> hospedes1= new ArrayList<>();
        hospedes1.add((Hospede) taylor);
        hospedes1.add((Hospede) sergio); 

        Hospedagem h1= new Hospedagem(LocalDateTime.now(), joel, hospedes1, quarto01);
;
        System.out.println("Informações da hospedagem:\n");
        System.out.println("Quarto: " + h1.getQuarto().getId());
        System.out.println("Hóspede 1: " + h1.getHospedes().get(0).getNome());
        System.out.println("Hóspede 2: " + h1.getHospedes().get(1).getNome());
        System.out.println("Item 1 do frigobar: " + frigobar01.getItens().get(0).getNome());

        h1.checkIn();
        h1.checkOut();
        */

        Quarto quarto01= new QuartoPadrao("01", 2, 5);
        RepoQuartos hotel= new RepoQuartos();
        hotel.adicionar(quarto01);

        System.out.println("Capacidade do quarto encontrado: " + hotel.buscar("01").getCapacidade());
    }
}

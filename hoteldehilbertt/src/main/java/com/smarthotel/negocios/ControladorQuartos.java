package com.smarthotel.negocios;

import com.smarthotel.models.Frigobar;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Item;
import com.smarthotel.models.Quarto;
import com.smarthotel.models.Recibo;
import com.smarthotel.models.StatusQuarto;
import com.smarthotel.negocios.exceptions.LFException;
import com.smarthotel.dados.RepoQuartos;
//import com.smarthotel.dados.RepoItens;
import java.util.ArrayList;

import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;

// falta implementar: 
//  - recibo do frigobar (debitar da conta responsável pela hospedagem)
// - buscarItemRegistrado (verificar se o item está no sistema, depende do ControladorItens)

public class ControladorQuartos implements IContQuartos {

    static private RepoQuartos quartosHotel;

    public ControladorQuartos() {
        // Se o repositório já existir, não pode criar um novo
        if (quartosHotel == null) {
            quartosHotel = new RepoQuartos();
        }
    }

    public int gerarId() {
        int idAtual = Quarto.getDefinirId();
        int novoId = idAtual + 1;
        Quarto.setDefinirId(novoId);
        return idAtual;
    }

    // Métodos para gerenciar o repositório de quartos
    public Quarto buscarQuarto(String id) throws ONEException {
        Quarto quarto = quartosHotel.buscar(id);
        if (quarto == null) {
            throw new ONEException("Quarto não encontrado");
        }
        return quarto;
    }

    public void adicionarQuarto(Quarto quarto) throws ORException {
        if (quartosHotel.buscar(quarto.getId()) != null) {
            throw new ORException(quarto); // quarto repetido no repositório
        }
        String idQuarto =  "q" + String.valueOf(gerarId()); 
        quarto.setId(idQuarto);
        quartosHotel.adicionar(quarto);
    }

    public void removerQuarto(String id) throws ONEException {
        Quarto quarto = quartosHotel.buscar(id);
        if (quarto == null) {
            throw new ONEException("Quarto não encontrado");
        }
        quartosHotel.remover(quarto);
    }
    
    public void atualizarQuarto(String id, Quarto quarto) throws ONEException {
        if (id != null && quarto != null) {
            quartosHotel.atualizar(id, quarto);
        }
    }

    public boolean estaDisponivel(Quarto quarto) {
        if (quarto == null) {
            return false;
        }
        String id = quarto.getId();
        return (quartosHotel.buscar(id).getStatus() == StatusQuarto.DISPONIVEL);
    }

    public ArrayList<Quarto> listarQuartos() {
        return quartosHotel.getObjetos();
    }

    public ArrayList<Quarto> listarQuartosDisponiveis() {
        ArrayList<Quarto> quartosDisponiveis = new ArrayList<>();
        for (Quarto quarto : listarQuartos()) {
            if (estaDisponivel(quarto)) {
                quartosDisponiveis.add(quarto);
            }
        }
        return quartosDisponiveis;
    }

    public Item buscarItemFrigobar(String idQuarto, String idItem) throws ONEException {
        Item itemEncontrado = null;

        if (idQuarto != null && idItem != null) {
            Quarto quarto = quartosHotel.buscar(idQuarto);
            Frigobar frigobar = quarto.getFrigobar();

            if (frigobar == null) {
                throw new ONEException("Frigobar não encontrado no quarto");
            }

            ArrayList<Item> inventario = quarto.getFrigobar().getInventarioFrigobar();
            for (Item item : inventario) {
                if (item.getId().equals(idItem)) {
                    itemEncontrado = item;
                    break;
                }
            }
        
            if (itemEncontrado == null) {
                throw new ONEException("Item do frigobar não encontrado");
            }
            
        }
        return itemEncontrado;
    }

    public void pegarItemFrigobar(Hospedagem hosp, String idQuarto, String idItem) throws ONEException {
        if (idQuarto != null && idItem != null) {
            Item item = buscarItemFrigobar(idQuarto, idItem); // pode lançar ENEException se o item não for encontrado
            Quarto quarto = quartosHotel.buscar(idQuarto);
            Frigobar frigobar = quarto.getFrigobar();

            if (frigobar == null) {
                throw new ONEException("Frigobar não encontrado no quarto");
            }
            // caso o quarto realmente tenha um frigobar
            ArrayList<Item> inventario = quarto.getFrigobar().getInventarioFrigobar();
            inventario.remove(item);

            // se a hospedagem existir, um recibo é enviado para a conta responsavel
            // caso a hospedagem não exista, o item está sendo pego do frigobar pela administração do hotel
            if (hosp != null) {
                ControladorPagamentos pagamento = new ControladorPagamentos();
                
                Recibo recibo = pagamento.gerarReciboFrigobar(item, hosp);
                pagamento.adicionarRecibo(hosp.getConta(), recibo);
            }
        }
    }

    public void reporItemFrigobar(String idQuarto, String idItem) throws ONEException, LFException {
        if (idQuarto != null && idItem != null) {
            ControladorItens itensRegistrados = new ControladorItens();
            Item novoItem = itensRegistrados.buscarItem(idItem);

            Quarto quarto = quartosHotel.buscar(idQuarto);
            Frigobar frigobar = quarto.getFrigobar();

            if (frigobar == null) {
                throw new ONEException("Frigobar não encontrado no quarto");
            }
            // caso o quarto realmente tenha um frigobar
            int capacidade = frigobar.getCapacidadeMaxima();
            ArrayList<Item> inventario = quarto.getFrigobar().getInventarioFrigobar();
            
            if (inventario.size() < capacidade) {
                inventario.add(novoItem);
            }
            else {
                throw new LFException(capacidade);
            }
        }
    }
}


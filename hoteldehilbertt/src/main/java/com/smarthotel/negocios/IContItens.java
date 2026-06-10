package com.smarthotel.negocios;

import java.time.LocalDate;
import java.util.ArrayList;
import com.smarthotel.dados.exceptions.ONEException;
import com.smarthotel.dados.exceptions.ORException;
import com.smarthotel.models.Item;

public interface IContItens {
    Item buscarItem(String id) throws ONEException;
    void adicionarItem(Item item) throws ORException;
    void removerItem(String id) throws ONEException;
    void atualizarItem(String id, String nome, LocalDate validade, double valor) throws ONEException;

    ArrayList<Item> listarItensRegistrados();
    void alterarValorItem(String id, double novoPreco) throws ONEException;
}

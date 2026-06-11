package com.smarthotel.negocios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;

import com.smarthotel.models.ContaHospedagem;
import com.smarthotel.models.Funcionario;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Item;
import com.smarthotel.models.Quarto;
import com.smarthotel.models.Recibo;
import com.smarthotel.models.TipoRecibo;

public class ControladorPagamentos implements IContPagamentos {
    
    private static ControladorPagamentos instance;

    private ControladorPagamentos() { }

    public static ControladorPagamentos getInstance() {
        if (instance == null) {
            instance = new ControladorPagamentos();
        }
        return instance;
    }

    private int gerarId() {
        int idAtual = Recibo.getDefinirId();
        int novoId = idAtual + 1;
        Recibo.setDefinirId(novoId);
        return idAtual;
    }

    public Recibo gerarReciboDiaria(Hospedagem hosp) {
        LocalDate entrada = hosp.getDataEntrada();
        int dias = Period.between(entrada, LocalDate.now()).getDays();

        double taxaQuarto = hosp.getQuarto().getMultTaxa();
        double taxaTemp = 1;
        Month mesAtual = LocalDate.now().getMonth();

        if (estaEmAltaTemporada(mesAtual)) {
            taxaTemp = Quarto.getMultTemporada();
        }

        double valor = dias * taxaQuarto * taxaTemp;
        
        Recibo recibo = new Recibo(TipoRecibo.DIARIA, valor);
        String reciboId = String.valueOf(gerarId()) + recibo.getTipo().name(); 
        recibo.setId(reciboId);
        return recibo;
    }

    public Recibo gerarReciboServico(Hospedagem hosp, Funcionario f, String descricao) {
        LocalTime agora = LocalTime.now();
        LocalTime noiteInicio = LocalTime.of(22, 0);
        LocalTime noiteFim = LocalTime.of(5, 0);
        double taxaNoturna = 1;
        double valor = 5;

        if (agora.isAfter(noiteInicio) || agora.isBefore(noiteFim)) {
            taxaNoturna = 2;
        }

        valor = valor * taxaNoturna;
        String mensagem = f.getNome() + " | " + f.getCargo() + " | " + descricao;

        Recibo recibo = new Recibo(TipoRecibo.SERVICO, valor, mensagem);
        String reciboId = String.valueOf(gerarId()) + recibo.getTipo().name(); 
        recibo.setId(reciboId);
        return recibo;
    }

    public Recibo gerarReciboFrigobar(Item item, Hospedagem hosp) {
        double valor = item.getValor();
        String idHospedagem = hosp.getId();

        Recibo recibo = new Recibo(TipoRecibo.FRIGOBAR, valor, idHospedagem);
        String reciboId = String.valueOf(gerarId()) + recibo.getTipo().name(); 
        recibo.setId(reciboId);
        return recibo;
    }

    
    public void adicionarRecibo(ContaHospedagem conta, Recibo recibo) {
        ArrayList<Recibo> recibos = conta.getRecibos();
        recibos.add(recibo);

        double dividaTotal = conta.getDividaTotal();
        dividaTotal += recibo.getValor();
        conta.setDividaTotal(dividaTotal);
    }

    public void removerRecibo(ContaHospedagem conta, Recibo recibo) {
        ArrayList<Recibo> recibos = conta.getRecibos();
        recibos.remove(recibo);

        double dividaTotal = conta.getDividaTotal();
        dividaTotal -= recibo.getValor();
        conta.setDividaTotal(dividaTotal);
    }

    public void alterarTaxaTemporada(double novaTaxa) {
        if (novaTaxa >= 1) {
            Quarto.setMultTemporada(novaTaxa);
        }
    }

    private boolean estaEmAltaTemporada(Month mes) {
        ArrayList<Month> meses = Quarto.getAltaTemporada();

        if (meses != null) {
            return meses.contains(mes);
        }

        return false; // caso não exista altaTemporada
    }
}
    
    


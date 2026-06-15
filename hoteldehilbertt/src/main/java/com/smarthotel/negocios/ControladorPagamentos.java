package com.smarthotel.negocios;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.smarthotel.models.StatusHospedagem;
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

    private String gerarId(TipoRecibo tipo) {
        int idAtual = Recibo.getDefinirId();
        
        String id = String.valueOf(idAtual) + tipo.name().substring(0, 1);

        Recibo.setDefinirId(idAtual+1);

        return id;
    }

    public Recibo gerarReciboDiaria(Hospedagem hosp) {

        LocalDate entrada = hosp.getDataEntrada();
        LocalDate saida = LocalDate.now();
        double taxaQuarto = hosp.getQuarto().getMultTaxa();

        double valor = calcularValor(entrada, saida, taxaQuarto);

        Recibo recibo = new Recibo(TipoRecibo.DIARIA, valor);

        String reciboId = gerarId(TipoRecibo.DIARIA);
        recibo.setId(reciboId);

        return recibo;
    }

    // sobrecarrega o metodo anterior, pois serve para gerar diarias extras (saida atrasada)
    public Recibo gerarReciboDiaria(Hospedagem hosp, LocalDate entrada) {
        LocalDate saida = LocalDate.now();
        double taxaQuarto = hosp.getQuarto().getMultTaxa();

        double valor = calcularValor(entrada, saida, taxaQuarto);

        Recibo recibo = new Recibo(TipoRecibo.DIARIA, valor);

        String reciboId = gerarId(TipoRecibo.DIARIA);
        recibo.setId(reciboId);

        return recibo;
    }

    public Recibo gerarReciboServico(Hospedagem hosp, Funcionario f, String descricao) {
        LocalTime agora = LocalTime.now();
        LocalTime noiteInicio = LocalTime.of(22, 0);
        LocalTime noiteFim = LocalTime.of(5, 0);
        double taxaNoturna = 0;
        double valor = Quarto.getValorServico();

        if (agora.isAfter(noiteInicio) || agora.isBefore(noiteFim)) {
            taxaNoturna = Quarto.getTaxaNoturna();
        }

        valor = valor + taxaNoturna;
        String mensagem = f.getNome() + " | " + f.getCargo() + " | " + descricao;

        Recibo recibo = new Recibo(TipoRecibo.SERVICO, valor, mensagem);
        String reciboId = gerarId(TipoRecibo.SERVICO);
        recibo.setId(reciboId);
        return recibo;
    }

    public Recibo gerarReciboFrigobar(Item item, Hospedagem hosp) {
        double valor = item.getValor();
        String idHospedagem = hosp.getId();

        Recibo recibo = new Recibo(TipoRecibo.FRIGOBAR, valor, idHospedagem);
        String reciboId = gerarId(TipoRecibo.FRIGOBAR);
        recibo.setId(reciboId);
        return recibo;
    }
    
    public void adicionarRecibo(ContaHospedagem conta, Recibo recibo) {
        ArrayList<Recibo> recibos = conta.getRecibos();
        recibos.add(recibo);

        double dividaTotal = conta.getSaldoPendente();
        dividaTotal += recibo.getValor();
        conta.setSaldoPendente(dividaTotal);
    }

    public void removerRecibo(ContaHospedagem conta, Recibo recibo) {
        ArrayList<Recibo> recibos = conta.getRecibos();
        recibos.remove(recibo);

        double dividaTotal = conta.getSaldoPendente();
        dividaTotal -= recibo.getValor();
        conta.setSaldoPendente(dividaTotal);
    }

    public void alterarValores(double diaria, double servico) {
        if (diaria >= 1) {
            Quarto.setValorDiaria(diaria);
        }
        if (servico >= 1) {
            Quarto.setValorServico(servico);
        }
    }

    public void alterarTaxas(double padrao, double suite, double presidencial, double temporada, double servNoturno) {
        if (padrao >= 1) {
            Quarto.setTaxaPadrao(padrao);
        }
        if (suite >= 1) {
            Quarto.setTaxaSuite(suite);
        }
        if (presidencial >= 1) {
            Quarto.setTaxaPresidencial(presidencial);
        }
        if (temporada >= 1) {
            Quarto.setTaxaTemporada(temporada);
        }
        if (servNoturno >= 1) {
            Quarto.setTaxaNoturna(servNoturno);
        }
    }

    public double calcularValor(LocalDate entrada, LocalDate saida, double taxaQuarto) {
        int dias = Period.between(entrada, saida).getDays();

        if (dias <= 0) {
            dias = 1;
        }

        double valorDiaria = Quarto.getValorDiaria();
        double taxaTemp = 1;

        Month mesAtual = LocalDate.now().getMonth();

        if (estaEmAltaTemporada(mesAtual)) {
            taxaTemp = Quarto.getTaxaTemporada();
        }

        double valor = valorDiaria * dias * taxaQuarto * taxaTemp;

        return valor;
    }

    public void pagarDivida(Hospedagem hosp) {

        ContaHospedagem conta = hosp.getConta();
        double divida = conta.getSaldoPendente();
        
        conta.setSaldoPendente(0);
        conta.setSaldoPago(divida);
        
        hosp.setDiariaPaga(true);
    }

    public void pagarDividaGrupo(Hospedagem hosp) {
        pagarDivida(hosp);
        ContaHospedagem conta = hosp.getConta();
        IContHospedagens hospedagens = ControladorHospedagens.getInstance();

        for (Hospedagem h : hospedagens.getRepositorioHospedagens().getObjetos()) {

            // se existir outra hospedagem ativa, conectada à mesma conta e que ainda não foi paga:
            if ((h.getConta().equals(conta)) && (h.getStatus() == StatusHospedagem.ATIVA) && (h.isDiariaPaga() == false)) {
                ContaHospedagem conta2 = h.getConta();

                double divida = conta2.getSaldoPendente();
        
                conta2.setSaldoPendente(0);
                conta2.setSaldoPago(divida);
        
                h.setDiariaPaga(true);
            }
        }
    }

    public void verificarDiariaAtrasada(Hospedagem hosp) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime horarioSaida = hosp.getHorarioSaida();
        LocalDateTime checkout = hosp.getHorarioCheckOut();

        if (agora.isAfter(horarioSaida) && checkout == null) {
            hosp.setDiariaPaga(false);
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
    
    


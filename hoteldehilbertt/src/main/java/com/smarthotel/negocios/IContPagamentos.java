package com.smarthotel.negocios;

import java.time.LocalDate;

import com.smarthotel.models.ContaHospedagem;
import com.smarthotel.models.Funcionario;
import com.smarthotel.models.Hospedagem;
import com.smarthotel.models.Item;
import com.smarthotel.models.Recibo;

public interface IContPagamentos {
    Recibo gerarReciboDiaria(Hospedagem hosp);
    Recibo gerarReciboMulta(Hospedagem hosp);
    Recibo gerarReciboServico(Hospedagem hosp, Funcionario f, String descricao);
    Recibo gerarReciboFrigobar(Item item, Hospedagem hosp);

    void adicionarRecibo(ContaHospedagem conta, Recibo recibo);
    void removerRecibo(ContaHospedagem conta, Recibo recibo);

    void alterarValores(double diaria, double servico);
    void alterarTaxas(double padrao, double suite, double presidencial, double temporada, double servNoturno);
    double calcularValor(LocalDate entrada, LocalDate saida, double taxaQuarto);
    void verificarDiariaAtrasada(Hospedagem hosp);
}

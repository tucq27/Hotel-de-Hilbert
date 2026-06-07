package com.smarthotel.models;

public class Hospede extends Pessoa {

    private String preferencias; // Preferências do hóspede ( Ex: quarto com varanda, cama de casal, etc)
    private String historico; // Pode guardar informações sobre hospedagens anteriores
    private RestricaoHospede restricao= RestricaoHospede.DISPONIVEL; //caso o Hospede tenha historico ruim ele será PROIBIDO

    // Construtor
    public Hospede(Pessoa p) {
        super( p.getNome(), p.getCpf(), p.getDataNascimento(), p.getTelefone(), p.getEmail());
    }
    
    public Hospede(Pessoa p, String preferencias, String historico) {
        super( p.getNome(), p.getCpf(), p.getDataNascimento(), p.getTelefone(), p.getEmail());
        this.preferencias = preferencias;
        this.historico = historico;
    }

    // Geters e Setters
    public String getPreferencias() {
        return preferencias;
    }
    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public String getHistorico() {
        return historico;
    }
    public void setHistorico(String historico) {
        this.historico = historico;
    }
    public RestricaoHospede getRestricao() {
        return restricao;
    }
    public void setRestricao(RestricaoHospede restricao) {
        this.restricao = restricao;
    }
}

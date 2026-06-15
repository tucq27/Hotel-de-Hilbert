package com.smarthotel.models;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.smarthotel.dados.IIdentificavel;

public class Quarto implements IIdentificavel {

    private static final List<Month> MESESPADRAO = List.of(Month.JANUARY, Month.DECEMBER, Month.JULY, Month.JUNE);

    private String id;
    private int numero;
    private static int definirId = 1;
    private int andar;
    private StatusQuarto status= StatusQuarto.DISPONIVEL;
    private TipoQuarto tipo;

    private static double valorDiaria = 30;
    private static double valorServico = 10;
    private static double taxaNoturna = 2;
    private static double taxaPadrao =1;
    private static double taxaSuite = 2;
    private static double taxaPresidencial = 5;
    private static double taxaTemporada = 1.5;

    private static ArrayList<Month> altaTemporada = new ArrayList<>(MESESPADRAO);
    private int capacidade;
    private Frigobar frigobar;

    // construtor sem frigobar
    public Quarto(TipoQuarto tipo, int numero, int andar, int capacidade) {
        this.tipo = tipo;
        this.numero = numero;
        this.andar = andar;
        this.capacidade = capacidade;
    }

    // construtor com frigobar
    public Quarto(TipoQuarto tipo, int numero, int andar, int capacidade, Frigobar frigobar) {
        this.tipo = tipo;
        this.numero = numero;
        this.andar = andar;
        this.capacidade = capacidade;
        this.frigobar = frigobar;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }
    public int getAndar() {
        return andar;
    }
    public static double getTaxaTemporada() {
        return taxaTemporada;
    }
    public int getCapacidade() {
        return capacidade;
    }
    public Frigobar getFrigobar() {
        return frigobar;
    }
    public StatusQuarto getStatus() {
        return status;
    }
    public static ArrayList<Month> getAltaTemporada(){
        return altaTemporada;
    }
    public int getNumero() {
        return numero;
    }
    public static int getDefinirId() {
        return definirId;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    public static void setDefinirId(int definirId) {
        Quarto.definirId = definirId;
    }
    public void setAndar(int andar) {
        this.andar = andar;
    }
    public static void setTaxaTemporada(double taxaTemporada) {
        Quarto.taxaTemporada = taxaTemporada;
    }
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    public void setFrigobar(Frigobar frigobar) {
        this.frigobar = frigobar;
    }
    public void setStatus(StatusQuarto status) {
        this.status = status;
    }
    public void setAltaTemporada(ArrayList<Month> meses) {
        Quarto.altaTemporada = meses;
    }
    public void setId(String id) {
        this.id =id;
    }
     public TipoQuarto getTipo() {
        return tipo;
    }

    public void setTipo(TipoQuarto tipo) {
        this.tipo = tipo;
    }

    public static double getTaxaPadrao() {
        return taxaPadrao;
    }

    public static void setTaxaPadrao(double taxaPadrao) {
        Quarto.taxaPadrao = taxaPadrao;
    }

    public static double getTaxaSuite() {
        return taxaSuite;
    }

    public static void setTaxaSuite(double taxaSuite) {
        Quarto.taxaSuite = taxaSuite;
    }

    public static double getTaxaPresidencial() {
        return taxaPresidencial;
    }

    public static void setTaxaPresidencial(double taxaPresidencial) {
        Quarto.taxaPresidencial = taxaPresidencial;
    }
     public static double getValorDiaria() {
        return valorDiaria;
    }

    public static void setValorDiaria(double valorDiaria) {
        Quarto.valorDiaria = valorDiaria;
    }

    public static double getValorServico() {
        return valorServico;
    }

    public static void setValorServico(double valorServico) {
        Quarto.valorServico = valorServico;
    }

    public static double getTaxaNoturna() {
        return taxaNoturna;
    }

    public static void setTaxaNoturna(double taxaNoturna) {
        Quarto.taxaNoturna = taxaNoturna;
    }

    // retorna a taxa especifica de um quarto
    public double getMultTaxa(){
        double taxa = 1;

        if (tipo == TipoQuarto.PADRAO) {
            taxa = taxaPadrao;
        }
        if (tipo == TipoQuarto.SUITE) {
            taxa = taxaSuite;
        }
        if (tipo == TipoQuarto.PRESIDENCIAL) {
            taxa = taxaPresidencial;
        }

        return taxa;
    }

    public String getChave() {
        return id; // O id é a chave de identificação única para a classe Quarto
    }
    public void setChave(String id) {
        this.id = id;
    }

}   

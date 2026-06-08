package com.smarthotel.models;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.smarthotel.dados.IIdentificavel;

public abstract class Quarto implements IIdentificavel {

    private static final List<Month> MESESPADRAO = List.of(Month.JANUARY, Month.DECEMBER, Month.JULY, Month.JUNE);

    protected String id;
    protected int numero;
    protected static int definirId = 1;
    protected int andar;
    protected StatusQuarto status= StatusQuarto.DISPONIVEL;
    protected double multTaxa; //Multiplicador de taxa de acordo com o tipo de quarto
    protected static double multTemporada;
    protected static ArrayList<Month> altaTemporada = new ArrayList<>(MESESPADRAO);
    protected int capacidade;
    protected Frigobar frigobar;

    // construtor sem frigobar
    public Quarto(int numero, int andar, int capacidade) {
        this.numero = numero;
        this.andar = andar;
        this.capacidade = capacidade;
        // por padrao, o quarto é limpo e livre, entao não precisa passar isso no construtor
    }

    // construtor com frigobar
    public Quarto(int numero, int andar, int capacidade, Frigobar frigobar) {
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
    public double getMultTaxa() {
        return multTaxa;
    }
    public static double getMultTemporada() {
        return multTemporada;
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
    public void setMultTaxa(double multTaxa) {
        this.multTaxa = multTaxa;
    }
    public static void setMultTemporada(double multTemporada) {
        Quarto.multTemporada = multTemporada;
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

    public String getChave() {
        return id; // O id é a chave de identificação única para a classe Quarto
    }
    public void setChave(String id) {
        this.id = id;
    }

}   

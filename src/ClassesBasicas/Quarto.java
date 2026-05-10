package ClassesBasicas;
import Dados.InterfaceIdentificavel;

public abstract class Quarto implements InterfaceIdentificavel {

    protected String id;
    protected int andar;
    protected boolean limpo = true;
    protected boolean livre = true;
    protected double multTaxa; //Multiplicador de taxa de acordo com o tipo de quarto
    protected static double multTemporada;
    protected int capacidade;
    protected Frigobar frigobar;

    // construtor
    public Quarto(String id, int andar, int capacidade) {
        this.id = id;
        this.andar = andar;
        this.capacidade = capacidade;
        this.frigobar = new Frigobar();
        // por padrao, o quarto é limpo e livre, entao não precisa passar isso no construtor
    }

    // Getters
    public String getId() {
        return id;
    }
    public int getAndar() {
        return andar;
    }
    public boolean isLimpo() {
        return limpo;
    }
    public boolean isLivre() {
        return livre;
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

    // Setters
    public void setAndar(int andar) {
        this.andar = andar;
    }
    public void setLimpo(boolean limpo) {
        this.limpo = limpo;
    }
    public void setLivre(boolean livre) {
        this.livre = livre;
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
    
    public String getChave() {
        return id; // O id é a chave de identificação única para a classe Quarto
    }

}   

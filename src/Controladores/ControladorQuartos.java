package Controladores;
import Dados.InterfaceRepositorio;
import Dados.RepositorioPadrao;
import ClassesBasicas.Quarto;

public class ControladorQuartos {

    static private InterfaceRepositorio<Quarto> repositorioQuartos;

    public ControladorQuartos() {

        if (repositorio == null) {
            repositorio = new RepositorioPadrao<Quarto>();
    }

    public addQuarto(Quarto quarto) {

        if (repositorioQuartos.buscar(quarto) != null) {
            throw new ElementoRepetidoException(); //!IMPLEMENTAR
        }

        repositorioQuartos.adicionar(quarto);
    }

    public
}

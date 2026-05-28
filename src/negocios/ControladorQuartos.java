package negocios;
import dados.IRepositorio;
import dados.RepoQuartos;
import dados.Repositorio;
import models.Quarto;

public class ControladorQuartos {

    static private RepoQuartos repositorioQuartos;

    public ControladorQuartos() {

        if (repositorioQuartos == null) {
            repositorioQuartos = new RepoQuartos();
    }
}

    public void addQuarto(Quarto quarto) {

        if (repositorioQuartos.buscar(quarto) != null) {
            throw new ElementoRepetidoException(); //!IMPLEMENTAR
        }

        repositorioQuartos.adicionar(quarto);
    }

    public
}

package negocios;
import dados.IRepositorio;
import dados.RepoQuartos;
import dados.Repositorio;
import models.Quarto;
import dados.IRepositorio;
import dados.RepoQuartos;


public class ControladorQuartos {

    static private RepoQuartos repositorioQuartos;

    public ControladorQuartos() {

        if (repositorioQuartos == null) {
            repositorioQuartos = new RepoQuartos();
        }
    }

    public void addQuarto(Quarto quarto) {
        if (repositorioQuartos.buscar(quarto.getId()) != null) {
            throw new ElementoRepetidoException(); //!IMPLEMENTAR
        }
        repositorioQuartos.adicionar(quarto);
    }

    public Quarto buscarQuarto(String id) {
        return repositorioQuartos.buscar(id);
    }

}

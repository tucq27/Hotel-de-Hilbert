package dados;

import java.util.ArrayList;

public interface IRepositorio<Tipo> {
    Tipo buscar(String id); // busca um objeto no repositorio (array list) a partir de um id (string)
    void adicionar(Tipo objeto); // adiciona um objeto ao repositorio (array list)
    void remover(Tipo objeto); // remove um objeto do repositorio (array list)
    void atualizar(String id, Tipo objeto); // atualiza um objeto do repositorio com base no seu id
    ArrayList<Tipo> getObjetos(); // retorna a lista de objetos do repositorio
}

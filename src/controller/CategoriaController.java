package controller;

import dao.CategoriaDAO;
import model.Categoria;
import java.sql.SQLException;
import java.util.List;

public class CategoriaController {
    private CategoriaDAO dao = new CategoriaDAO();

    public void adicionarCategoria(String nome, String descricao) throws SQLException {
        dao.insert(new Categoria(nome, descricao));
    }

    public void atualizarCategoria(int id, String nome, String descricao) throws SQLException {
        dao.update(new Categoria(id, nome, descricao));
    }

    public void excluirCategoria(int id) throws SQLException {
        dao.delete(id);
    }

    public List<Categoria> listarCategorias() throws SQLException {
        return dao.getAll();
    }
}

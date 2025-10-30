package controller;

import dao.ProdutoDAO;
import model.Produto;
import model.Categoria;
import java.sql.SQLException;
import java.util.List;

public class ProdutoController {
    private ProdutoDAO dao = new ProdutoDAO();

    public void adicionarProduto(String nome, double preco, int estoque, Categoria categoria) throws SQLException {
        dao.insert(new Produto(nome, preco, estoque, categoria));
    }

    public void atualizarProduto(int id, String nome, double preco, int estoque, Categoria categoria) throws SQLException {
        dao.update(new Produto(id, nome, preco, estoque, categoria));
    }

    public void excluirProduto(int id) throws SQLException {
        dao.delete(id);
    }

    public List<Produto> listarProdutos() throws SQLException {
        return dao.getAll();
    }
}

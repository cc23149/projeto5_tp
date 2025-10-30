package dao;

import model.Produto;
import model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public void insert(Produto produto) throws SQLException {
        String sql = "INSERT INTO projetoTP5.Produtos (nome, preco, estoque, idCategoria) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getEstoque());
            ps.setInt(4, produto.getCategoria().getIdCategoria());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) produto.setIdProduto(rs.getInt(1));
            }
        }
    }

    public void update(Produto produto) throws SQLException {
        String sql = "UPDATE projetoTP5.Produtos SET nome = ?, preco = ?, estoque = ?, idCategoria = ? WHERE idProduto = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getEstoque());
            ps.setInt(4, produto.getCategoria().getIdCategoria());
            ps.setInt(5, produto.getIdProduto());
            ps.executeUpdate();
        }
    }

    public void delete(int idProduto) throws SQLException {
        String sql = "DELETE FROM projetoTP5.Produtos WHERE idProduto = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProduto);
            ps.executeUpdate();
        }
    }

    public List<Produto> getAll() throws SQLException {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT p.idProduto, p.nome, p.preco, p.estoque, c.idCategoria, c.nome AS nomeCat, c.descricao AS descCat " +
                "FROM projetoTP5.Produtos p JOIN projetoTP5.Categorias c ON p.idCategoria = c.idCategoria";
        try (Connection con = Conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Categoria cat = new Categoria(rs.getInt("idCategoria"), rs.getString("nomeCat"), rs.getString("descCat"));
                lista.add(new Produto(rs.getInt("idProduto"), rs.getString("nome"), rs.getDouble("preco"), rs.getInt("estoque"), cat));
            }
        }
        return lista;
    }

    public Produto getById(int idProduto) throws SQLException {
        String sql = "SELECT p.idProduto, p.nome, p.preco, p.estoque, c.idCategoria, c.nome AS nomeCat, c.descricao AS descCat " +
                "FROM projetoTP5.Produtos p JOIN projetoTP5.Categorias c ON p.idCategoria = c.idCategoria WHERE p.idProduto = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProduto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Categoria cat = new Categoria(rs.getInt("idCategoria"), rs.getString("nomeCat"), rs.getString("descCat"));
                    return new Produto(rs.getInt("idProduto"), rs.getString("nome"), rs.getDouble("preco"), rs.getInt("estoque"), cat);
                }
            }
        }
        return null;
    }
}

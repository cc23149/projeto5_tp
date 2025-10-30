package dao;

import model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public void insert(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO projetoTP5.Categorias (nome, descricao) VALUES (?, ?)";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getDescricao());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    categoria.setIdCategoria(rs.getInt(1));
                }
            }
        }
    }

    public void update(Categoria categoria) throws SQLException {
        String sql = "UPDATE projetoTP5.Categorias SET nome = ?, descricao = ? WHERE idCategoria = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getDescricao());
            ps.setInt(3, categoria.getIdCategoria());
            ps.executeUpdate();
        }
    }

    public void delete(int idCategoria) throws SQLException {
        String sql = "DELETE FROM projetoTP5.Categorias WHERE idCategoria = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            ps.executeUpdate();
        }
    }

    public List<Categoria> getAll() throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT idCategoria, nome, descricao FROM projetoTP5.Categorias";
        try (Connection con = Conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Categoria(rs.getInt("idCategoria"), rs.getString("nome"), rs.getString("descricao")));
            }
        }
        return lista;
    }

    public Categoria getById(int idCategoria) throws SQLException {
        String sql = "SELECT idCategoria, nome, descricao FROM projetoTP5.Categorias WHERE idCategoria = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(rs.getInt("idCategoria"), rs.getString("nome"), rs.getString("descricao"));
                }
            }
        }
        return null;
    }
}

package view;

import dao.ProdutoDAO;
import dao.CategoriaDAO;
import model.Produto;
import model.Categoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class FrameProduto extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public FrameProduto() {
        setTitle("Manutenção de Produtos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Preço", "Estoque", "Categoria"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Painel de botões
        JPanel panelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnExcluir);
        add(panelBotoes, BorderLayout.SOUTH);

        carregarTabela();

        // Ações
        btnAdicionar.addActionListener(e -> adicionarProduto());
        btnEditar.addActionListener(e -> editarProduto());
        btnExcluir.addActionListener(e -> excluirProduto());
    }

    private void carregarTabela() {
        try {
            tableModel.setRowCount(0);
            List<Produto> lista = produtoDAO.getAll();
            for (Produto p : lista) {
                tableModel.addRow(new Object[]{
                        p.getIdProduto(),
                        p.getNome(),
                        p.getPreco(),
                        p.getEstoque(),
                        p.getCategoria().getNome()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private Categoria selecionarCategoria() throws SQLException {
        List<Categoria> categorias = categoriaDAO.getAll();
        JComboBox<Categoria> combo = new JComboBox<>(categorias.toArray(new Categoria[0]));
        int option = JOptionPane.showConfirmDialog(this, combo, "Selecione a Categoria", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return (Categoria) combo.getSelectedItem();
        }
        return null;
    }

    private void adicionarProduto() {
        String nome = JOptionPane.showInputDialog(this, "Nome:");
        String precoStr = JOptionPane.showInputDialog(this, "Preço:");
        String estoqueStr = JOptionPane.showInputDialog(this, "Estoque:");

        if (nome != null && !nome.isEmpty()) {
            try {
                double preco = Double.parseDouble(precoStr);
                int estoque = Integer.parseInt(estoqueStr);
                Categoria cat = selecionarCategoria();
                if (cat != null) {
                    produtoDAO.insert(new Produto(nome, preco, estoque, cat));
                    carregarTabela();
                }
            } catch (SQLException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar produto: " + e.getMessage());
            }
        }
    }

    private void editarProduto() {
        int linha = table.getSelectedRow();
        if (linha >= 0) {
            int id = (int) tableModel.getValueAt(linha, 0);
            String nome = JOptionPane.showInputDialog(this, "Nome:", tableModel.getValueAt(linha, 1));
            String precoStr = JOptionPane.showInputDialog(this, "Preço:", tableModel.getValueAt(linha, 2));
            String estoqueStr = JOptionPane.showInputDialog(this, "Estoque:", tableModel.getValueAt(linha, 3));

            try {
                double preco = Double.parseDouble(precoStr);
                int estoque = Integer.parseInt(estoqueStr);
                Categoria cat = selecionarCategoria();
                if (cat != null) {
                    produtoDAO.update(new Produto(id, nome, preco, estoque, cat));
                    carregarTabela();
                }
            } catch (SQLException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Erro ao editar produto: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto.");
        }
    }

    private void excluirProduto() {
        int linha = table.getSelectedRow();
        if (linha >= 0) {
            int id = (int) tableModel.getValueAt(linha, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?");
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    produtoDAO.delete(id);
                    carregarTabela();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir produto: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto.");
        }
    }
}

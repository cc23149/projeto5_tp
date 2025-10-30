package view;

import dao.CategoriaDAO;
import model.Categoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class FrameCategoria extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private CategoriaDAO dao = new CategoriaDAO();

    public FrameCategoria() {
        setTitle("Manutenção de Categorias");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição"}, 0);
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
        btnAdicionar.addActionListener(e -> adicionarCategoria());
        btnEditar.addActionListener(e -> editarCategoria());
        btnExcluir.addActionListener(e -> excluirCategoria());
    }

    private void carregarTabela() {
        try {
            tableModel.setRowCount(0);
            List<Categoria> lista = dao.getAll();
            for (Categoria c : lista) {
                tableModel.addRow(new Object[]{c.getIdCategoria(), c.getNome(), c.getDescricao()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar categorias: " + e.getMessage());
        }
    }

    private void adicionarCategoria() {
        String nome = JOptionPane.showInputDialog(this, "Nome:");
        String desc = JOptionPane.showInputDialog(this, "Descrição:");
        if (nome != null && !nome.isEmpty()) {
            try {
                dao.insert(new Categoria(nome, desc));
                carregarTabela();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar: " + e.getMessage());
            }
        }
    }

    private void editarCategoria() {
        int linha = table.getSelectedRow();
        if (linha >= 0) {
            int id = (int) tableModel.getValueAt(linha, 0);
            String nome = JOptionPane.showInputDialog(this, "Nome:", tableModel.getValueAt(linha, 1));
            String desc = JOptionPane.showInputDialog(this, "Descrição:", tableModel.getValueAt(linha, 2));
            if (nome != null && !nome.isEmpty()) {
                try {
                    dao.update(new Categoria(id, nome, desc));
                    carregarTabela();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Erro ao editar: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria.");
        }
    }

    private void excluirCategoria() {
        int linha = table.getSelectedRow();
        if (linha >= 0) {
            int id = (int) tableModel.getValueAt(linha, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?");
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    dao.delete(id);
                    carregarTabela();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria.");
        }
    }
}

//Matheus Ferreira Fagundes - 23149
//Yasmin Victoria Lopes da Silva - 23581
package view;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class MenuPrincipal extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtPesquisa;
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public MenuPrincipal() {
        setTitle("Sistema Da Roça - Tela Inicial");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel superior: pesquisa + botões
        JPanel panelTop = new JPanel(new BorderLayout());

        txtPesquisa = new JTextField();
        txtPesquisa.setToolTipText("Pesquisar por nome ou categoria...");
        panelTop.add(txtPesquisa, BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        JButton btnCategorias = new JButton("Manutenção de Categorias");
        JButton btnProdutos = new JButton("Manutenção de Produtos");
        panelBotoes.add(btnCategorias);
        panelBotoes.add(btnProdutos);
        panelTop.add(panelBotoes, BorderLayout.EAST);

        add(panelTop, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Preço", "Estoque", "Categoria"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabela somente leitura
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Ações dos botões
        btnCategorias.addActionListener(e -> new FrameCategoria().setVisible(true));
        btnProdutos.addActionListener(e -> new FrameProduto().setVisible(true));

        // Pesquisa dinâmica
        txtPesquisa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarTabela(txtPesquisa.getText().trim());
            }
        });

        carregarTabela();
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

    private void filtrarTabela(String filtro) {
        try {
            tableModel.setRowCount(0);
            List<Produto> lista = produtoDAO.getAll();
            for (Produto p : lista) {
                if (p.getNome().toLowerCase().contains(filtro.toLowerCase()) ||
                        p.getCategoria().getNome().toLowerCase().contains(filtro.toLowerCase())) {

                    tableModel.addRow(new Object[]{
                            p.getIdProduto(),
                            p.getNome(),
                            p.getPreco(),
                            p.getEstoque(),
                            p.getCategoria().getNome()
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao filtrar produtos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}

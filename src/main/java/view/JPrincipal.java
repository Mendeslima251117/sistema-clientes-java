package view;

import util.MongoConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import java.awt.*;

import model.Cliente;

public class JPrincipal extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;

    private String usuarioLogado;
    private String tipoUsuario;

    public JPrincipal(String usuario, String tipo) {

        this.usuarioLogado = usuario;
        this.tipoUsuario = tipo;

        setTitle("Sistema de Clientes");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // ===== FUNDO =====
        getContentPane().setBackground(new Color(20, 20, 20));

        // ===== TOPO =====
        JPanel topo = new JPanel(null);
        topo.setBounds(0, 0, 1280, 60);
        topo.setBackground(new Color(10, 10, 10));

        JLabel titulo = new JLabel("Sistema de Clientes");
        titulo.setBounds(20, 10, 350, 40);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel lblUsuario = new JLabel(
                "Usuário: " + usuarioLogado + " (" + tipoUsuario + ")"
        );

        lblUsuario.setBounds(900, 18, 250, 20);
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnSair = new JButton("Sair");

        try {

            ImageIcon iconSair = new ImageIcon(
                    getClass().getResource("/imagens/logout.png")
            );

            Image img = iconSair.getImage()
                    .getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            btnSair.setIcon(new ImageIcon(img));

        } catch (Exception e) {

            System.out.println("Ícone logout não encontrado.");

        }

        btnSair.setBounds(1140, 10, 100, 40);
        btnSair.setBackground(new Color(180, 30, 30));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.setFont(new Font("Arial", Font.BOLD, 14));

        topo.add(titulo);
        topo.add(lblUsuario);
        topo.add(btnSair);

        add(topo);

        // ===== MENU =====
        JPanel menu = new JPanel(null);
        menu.setBounds(0, 60, 220, 660);
        menu.setBackground(new Color(35, 35, 35));

        JButton btnNovo = criarBotaoMenu(
                "Novo",
                "/imagens/novo.png",
                0
        );

        JButton btnEditar = criarBotaoMenu(
                "Editar",
                "/imagens/editar.png",
                70
        );

        JButton btnExcluir = criarBotaoMenu(
                "Excluir",
                "/imagens/excluir.png",
                140
        );

        JButton btnUsuarios = criarBotaoMenu(
                "Usuários",
                "/imagens/usuarios.png",
                210
        );

        menu.add(btnNovo);
        menu.add(btnEditar);
        menu.add(btnExcluir);

        // ===== SOMENTE ADMIN =====
        if (tipoUsuario.equalsIgnoreCase("ADMIN")) {

            menu.add(btnUsuarios);

        }

        add(menu);

        // ===== CARDS =====
        JPanel card1 = criarCard(
                "Clientes",
                "3",
                240,
                80
        );

        JPanel card2 = criarCard(
                "Sistema",
                "Online",
                450,
                80
        );

        JPanel card3 = criarCard(
                "Status",
                "Ativo",
                660,
                80
        );

        add(card1);
        add(card2);
        add(card3);

        // ===== TABELA =====
        JPanel painelTabela = new JPanel(null);
        painelTabela.setBounds(240, 170, 940, 420);
        painelTabela.setBackground(Color.WHITE);

        String[] colunas = {
                "CPF/CNPJ",
                "Nome",
                "Email",
                "Telefone",
                "Endereço"
        };

        modelo = new DefaultTableModel(colunas, 0);

        tabela = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(0, 0, 940, 420);

        painelTabela.add(scroll);

        add(painelTabela);

        carregarClientes();

        // ===== AÇÕES =====

        btnNovo.addActionListener(e -> {

            new JCadastro(this).setVisible(true);

        });

        btnEditar.addActionListener(e -> {

            int linha = tabela.getSelectedRow();

            if (linha == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "Selecione um cliente."
                );

                return;
            }

            Cliente c = new Cliente();

            c.setCpfCnpj(modelo.getValueAt(linha, 0).toString());
            c.setNome(modelo.getValueAt(linha, 1).toString());
            c.setEmail(modelo.getValueAt(linha, 2).toString());
            c.setTelefone(modelo.getValueAt(linha, 3).toString());
            c.setEndereco(modelo.getValueAt(linha, 4).toString());

            new JCadastro(this, c, linha).setVisible(true);

        });

        btnExcluir.addActionListener(e -> {

            int linha = tabela.getSelectedRow();

            if (linha == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "Selecione um cliente."
                );

                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente excluir?",
                    "Excluir",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {

                try {

                    String cpfCnpj = modelo.getValueAt(linha, 0).toString();

                    MongoCollection<Document> colecao =
                            MongoConnection
                                    .getDatabase()
                                    .getCollection("clientes");

                    // EXCLUI NO BANCO
                    colecao.deleteOne(
                            new Document("cpfCnpj", cpfCnpj)
                    );

                    // REMOVE DA TABELA
                    modelo.removeRow(linha);

                    JOptionPane.showMessageDialog(
                            null,
                            "Cliente excluído com sucesso!"
                    );

                } catch (Exception ex) {

                    ex.printStackTrace();

                    JOptionPane.showMessageDialog(
                            null,
                            "Erro ao excluir cliente."
                    );
                }
            }
        });

        btnUsuarios.addActionListener(e -> {

            new JUsuarios().setVisible(true);

        });

        btnSair.addActionListener(e -> {

            dispose();

            new JLogin().setVisible(true);

        });
    }

    // ===== BOTÃO MENU =====
    private JButton criarBotaoMenu(
            String texto,
            String caminhoIcone,
            int y
    ) {

        JButton btn = new JButton(texto);

        try {

            ImageIcon icon = new ImageIcon(
                    getClass().getResource(caminhoIcone)
            );

            Image img = icon.getImage()
                    .getScaledInstance(22, 22, Image.SCALE_SMOOTH);

            btn.setIcon(new ImageIcon(img));

        } catch (Exception e) {

            System.out.println(
                    "Ícone não encontrado: " + caminhoIcone
            );

        }

        btn.setBounds(0, y, 220, 65);

        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.setIconTextGap(15);

        btn.setBackground(new Color(45, 45, 45));
        btn.setForeground(Color.WHITE);

        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.setFont(new Font("Arial", Font.BOLD, 16));

        return btn;
    }

    // ===== CARD =====
    private JPanel criarCard(
            String titulo,
            String valor,
            int x,
            int y
    ) {

        JPanel card = new JPanel(null);

        card.setBounds(x, y, 190, 70);

        card.setBackground(new Color(40, 40, 40));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setBounds(15, 10, 150, 20);
        lblTitulo.setForeground(Color.GRAY);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel lblValor = new JLabel(valor);
        lblValor.setBounds(15, 30, 150, 30);
        lblValor.setForeground(Color.WHITE);
        lblValor.setFont(new Font("Arial", Font.BOLD, 28));

        card.add(lblTitulo);
        card.add(lblValor);

        return card;
    }

    // ===== CARREGAR CLIENTES =====
    private void carregarClientes() {

        modelo.setRowCount(0);

        MongoCollection<Document> col =
                MongoConnection
                        .getDatabase()
                        .getCollection("clientes");

        for (Document d : col.find()) {

            modelo.addRow(new Object[]{

                    d.getString("cpfCnpj"),
                    d.getString("nome"),
                    d.getString("email"),
                    d.getString("telefone"),
                    d.getString("endereco")

            });
        }
    }

    // ===== ADICIONAR CLIENTE =====
    public void adicionarCliente(Cliente c) {

        modelo.addRow(new Object[]{

                c.getCpfCnpj(),
                c.getNome(),
                c.getEmail(),
                c.getTelefone(),
                c.getEndereco()

        });
    }

    // ===== ATUALIZAR CLIENTE =====
    public void atualizarCliente(int linha, Cliente c) {

        modelo.setValueAt(c.getCpfCnpj(), linha, 0);
        modelo.setValueAt(c.getNome(), linha, 1);
        modelo.setValueAt(c.getEmail(), linha, 2);
        modelo.setValueAt(c.getTelefone(), linha, 3);
        modelo.setValueAt(c.getEndereco(), linha, 4);
    }
}
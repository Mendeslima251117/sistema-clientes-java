package view;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

import model.Cliente;
import model.ModeloTabela;
import session.Session;
import util.MongoConnection;

public class JPrincipal extends JFrame {

    private JTable table;
    private ModeloTabela modelo;
    private ArrayList<Cliente> lista;

    public JPrincipal() {

        setTitle("Sistema de Clientes");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== HEADER =====

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(20,20,20));
        header.setPreferredSize(new Dimension(0,60));

        JLabel titulo = new JLabel(" Sistema de Clientes");

        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel userLabel =
                new JLabel("Usuário: " + Session.getUsuario());

        userLabel.setForeground(Color.WHITE);

        JButton btnSair = new JButton("Sair");

        estilizarBotaoHeader(btnSair);

        JPanel right =
                new JPanel(new FlowLayout(
                        FlowLayout.RIGHT,
                        10,
                        10
                ));

        right.setOpaque(false);

        right.add(userLabel);
        right.add(btnSair);

        header.add(titulo, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ===== MENU =====

        JPanel menu = new JPanel();

        menu.setBackground(new Color(45,45,45));

        menu.setPreferredSize(new Dimension(180,0));

        menu.setLayout(new GridLayout(6,1,10,10));

        JButton btnNovo =
                new JButton(
                        "Novo",
                        icon("/imagens/novo.png",20,20)
                );

        JButton btnEditar =
                new JButton(
                        "Editar",
                        icon("/imagens/editar.png",20,20)
                );

        JButton btnExcluir =
                new JButton(
                        "Excluir",
                        icon("/imagens/excluir.png",20,20)
                );

        JButton btnUsuarios =
                new JButton(
                        "Usuários",
                        icon("/imagens/usuarios.png",20,20)
                );

        estilizarBotaoMenu(btnNovo);
        estilizarBotaoMenu(btnEditar);
        estilizarBotaoMenu(btnExcluir);
        estilizarBotaoMenu(btnUsuarios);

        menu.add(btnNovo);
        menu.add(btnEditar);
        menu.add(btnExcluir);
        menu.add(btnUsuarios);

        add(menu, BorderLayout.WEST);

        // ===== PERMISSÃO =====

        if (!"ADMIN".equals(Session.getTipo())) {

            btnExcluir.setEnabled(false);
            btnUsuarios.setEnabled(false);
        }

        // ===== TABELA =====

        JPanel conteudo = new JPanel(new BorderLayout());

        conteudo.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        table = new JTable();

        table.setRowHeight(28);

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        table.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        JScrollPane scroll =
                new JScrollPane(table);

        conteudo.add(scroll, BorderLayout.CENTER);

        add(conteudo, BorderLayout.CENTER);

        carregar();

        // =====================================================
        // NOVO
        // =====================================================

        btnNovo.addActionListener(e -> {

            JCadastro tela =
                    new JCadastro(this);

            tela.setLocationRelativeTo(null);

            tela.setVisible(true);
        });

        // =====================================================
        // EDITAR
        // =====================================================

        btnEditar.addActionListener(e -> {

            int linha = table.getSelectedRow();

            if (linha == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "Selecione um cliente!"
                );

                return;
            }

            Cliente c = modelo.getCliente(linha);

            JCadastro tela =
                    new JCadastro(
                            this,
                            c,
                            linha
                    );

            tela.setLocationRelativeTo(null);

            tela.setVisible(true);
        });

        // =====================================================
        // EXCLUIR
        // =====================================================

        btnExcluir.addActionListener(e -> {

            int linha = table.getSelectedRow();

            if (linha == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "Selecione um cliente!"
                );

                return;
            }

            Cliente c = modelo.getCliente(linha);

            int op =
                    JOptionPane.showConfirmDialog(
                            null,
                            "Excluir cliente?"
                    );

            if (op == JOptionPane.YES_OPTION) {

                MongoCollection<Document> col =
                        MongoConnection
                                .getDatabase()
                                .getCollection("clientes");

                col.deleteOne(
                        new Document(
                                "_id",
                                new ObjectId(c.getId())
                        )
                );

                carregar();
            }
        });

        // =====================================================
        // USUÁRIOS
        // =====================================================

        btnUsuarios.addActionListener(e -> {

            new JUsuarios().setVisible(true);
        });

        // =====================================================
        // LOGOUT
        // =====================================================

        btnSair.addActionListener(e -> {

            Session.limpar();

            dispose();

            new Jlogin().setVisible(true);
        });
    }

    // =====================================================
    // CARREGAR
    // =====================================================

    private void carregar() {

        lista = new ArrayList<>();

        MongoCollection<Document> col =
                MongoConnection
                        .getDatabase()
                        .getCollection("clientes");

        for (Document d : col.find()) {

            Cliente c = new Cliente();

            c.setId(
                    d.getObjectId("_id").toString()
            );

            c.setCpfCnpj(
                    d.getString("cpfCnpj")
            );

            c.setNome(
                    d.getString("nome")
            );

            c.setEmail(
                    d.getString("email")
            );

            c.setTelefone(
                    d.getString("telefone")
            );

            c.setEndereco(
                    d.getString("endereco")
            );

            lista.add(c);
        }

        modelo = new ModeloTabela(lista);

        table.setModel(modelo);

        // ESCONDER ID

        table.getColumnModel()
                .getColumn(0)
                .setMinWidth(0);

        table.getColumnModel()
                .getColumn(0)
                .setMaxWidth(0);

        table.getColumnModel()
                .getColumn(0)
                .setWidth(0);
    }

    // =====================================================
    // ADICIONAR
    // =====================================================

    public void adicionarCliente(Cliente cliente) {

        modelo.addCliente(cliente);
    }

    // =====================================================
    // ATUALIZAR
    // =====================================================

    public void atualizarCliente(
            int linha,
            Cliente cliente) {

        modelo.atualizarCliente(
                linha,
                cliente
        );
    }

    // =====================================================
    // ÍCONE
    // =====================================================

    private ImageIcon icon(
            String path,
            int w,
            int h) {

        ImageIcon i =
                new ImageIcon(
                        getClass().getResource(path)
                );

        Image img =
                i.getImage().getScaledInstance(
                        w,
                        h,
                        Image.SCALE_SMOOTH
                );

        return new ImageIcon(img);
    }

    // =====================================================
    // BOTÃO MENU
    // =====================================================

    private void estilizarBotaoMenu(JButton b) {

        b.setFocusPainted(false);

        b.setBackground(new Color(60,60,60));

        b.setForeground(Color.WHITE);

        b.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        b.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        b.setIconTextGap(10);

        b.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        b.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    public void mouseEntered(
                            java.awt.event.MouseEvent evt) {

                        b.setBackground(
                                new Color(80,80,80)
                        );
                    }

                    public void mouseExited(
                            java.awt.event.MouseEvent evt) {

                        b.setBackground(
                                new Color(60,60,60)
                        );
                    }
                }
        );
    }

    // =====================================================
    // BOTÃO HEADER
    // =====================================================

    private void estilizarBotaoHeader(JButton b) {

        b.setFocusPainted(false);

        b.setBackground(new Color(200,50,50));

        b.setForeground(Color.WHITE);

        b.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );
    }

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        new JPrincipal().setVisible(true);
    }
}
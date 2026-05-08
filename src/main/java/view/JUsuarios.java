package view;

import util.Tema;
import util.UsuarioDAO;

import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class JUsuarios extends JFrame {

    private JTable tabelaUsuarios;
    private DefaultTableModel modelo;

    private UsuarioDAO dao = new UsuarioDAO();

    public JUsuarios() {

        setTitle("Gerenciamento de Usuários");

        setSize(700, 400);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        getContentPane().setBackground(
                new Color(18,18,18)
        );

        // =====================================================
        // HEADER
        // =====================================================

        add(
                Tema.header("Usuários do Sistema"),
                BorderLayout.NORTH
        );

        // =====================================================
        // MENU LATERAL
        // =====================================================

        JPanel menu = new JPanel();

        menu.setBackground(new Color(45,45,45));

        menu.setPreferredSize(
                new Dimension(180,0)
        );

        menu.setLayout(
                new GridLayout(6,1,10,10)
        );

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

        estilizarBotaoMenu(btnNovo);
        estilizarBotaoMenu(btnEditar);
        estilizarBotaoMenu(btnExcluir);

        menu.add(btnNovo);
        menu.add(btnEditar);
        menu.add(btnExcluir);

        add(menu, BorderLayout.WEST);

        // =====================================================
        // TABELA
        // =====================================================

        modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Usuário", "Tipo"}
        );

        tabelaUsuarios = new JTable(modelo);

        tabelaUsuarios.setRowHeight(28);

        tabelaUsuarios.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        tabelaUsuarios.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        tabelaUsuarios.setBackground(Color.WHITE);

        JScrollPane scroll =
                new JScrollPane(tabelaUsuarios);

        JPanel conteudo =
                new JPanel(new BorderLayout());

        conteudo.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        conteudo.add(scroll, BorderLayout.CENTER);

        add(conteudo, BorderLayout.CENTER);

        carregar();

        // =====================================================
        // NOVO
        // =====================================================

        btnNovo.addActionListener(e -> {

            JTextField txtUsuario =
                    new JTextField();

            JTextField txtSenha =
                    new JTextField();

            JComboBox<String> combo =
                    new JComboBox<>(
                            new String[]{
                                    "ADMIN",
                                    "USER"
                            }
                    );

            JPanel panel =
                    new JPanel(
                            new GridLayout(0,1)
                    );

            panel.add(new JLabel("Usuário"));
            panel.add(txtUsuario);

            panel.add(new JLabel("Senha"));
            panel.add(txtSenha);

            panel.add(new JLabel("Tipo"));
            panel.add(combo);

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            panel,
                            "Novo Usuário",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if(result == JOptionPane.OK_OPTION){

                Usuario u = new Usuario();

                u.setUsuario(
                        txtUsuario.getText()
                );

                u.setSenha(
                        txtSenha.getText()
                );

                u.setTipo(
                        combo.getSelectedItem()
                                .toString()
                );

                dao.salvar(u);

                carregar();

                JOptionPane.showMessageDialog(
                        this,
                        "Usuário salvo!"
                );
            }
        });

        // =====================================================
        // EDITAR
        // =====================================================

        btnEditar.addActionListener(e -> {

            int linha =
                    tabelaUsuarios.getSelectedRow();

            if (linha == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione um usuário!"
                );

                return;
            }

            String usuarioAtual =
                    modelo.getValueAt(linha,0)
                            .toString();

            Usuario usuario =
                    dao.buscar(usuarioAtual);

            JTextField txtUsuario =
                    new JTextField(
                            usuario.getUsuario()
                    );

            JTextField txtSenha =
                    new JTextField(
                            usuario.getSenha()
                    );

            JComboBox<String> combo =
                    new JComboBox<>(
                            new String[]{
                                    "ADMIN",
                                    "USER"
                            }
                    );

            combo.setSelectedItem(
                    usuario.getTipo()
            );

            JPanel panel =
                    new JPanel(
                            new GridLayout(0,1)
                    );

            panel.add(new JLabel("Usuário"));
            panel.add(txtUsuario);

            panel.add(new JLabel("Senha"));
            panel.add(txtSenha);

            panel.add(new JLabel("Tipo"));
            panel.add(combo);

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            panel,
                            "Editar Usuário",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if(result == JOptionPane.OK_OPTION){

                Usuario novo =
                        new Usuario();

                novo.setUsuario(
                        txtUsuario.getText()
                );

                novo.setSenha(
                        txtSenha.getText()
                );

                novo.setTipo(
                        combo.getSelectedItem()
                                .toString()
                );

                dao.atualizar(
                        usuarioAtual,
                        novo
                );

                carregar();

                JOptionPane.showMessageDialog(
                        this,
                        "Usuário atualizado!"
                );
            }
        });

        // =====================================================
        // EXCLUIR
        // =====================================================

        btnExcluir.addActionListener(e -> {

            int linha =
                    tabelaUsuarios.getSelectedRow();

            if (linha == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione um usuário!"
                );

                return;
            }

            int op =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Deseja excluir?",
                            "Confirmação",
                            JOptionPane.YES_NO_OPTION
                    );

            if (op == JOptionPane.YES_OPTION) {

                String usuario =
                        modelo.getValueAt(linha,0)
                                .toString();

                dao.excluir(usuario);

                carregar();

                JOptionPane.showMessageDialog(
                        this,
                        "Usuário removido!"
                );
            }
        });
    }

    // =====================================================
    // CARREGAR
    // =====================================================

    private void carregar() {

        modelo.setRowCount(0);

        List<Usuario> lista =
                dao.listar();

        for(Usuario u : lista){

            modelo.addRow(
                    new Object[]{
                            u.getUsuario(),
                            u.getTipo()
                    }
            );
        }
    }
    public void recarregarTabela(){

        carregar();
    }

    // =====================================================
    // ÍCONES
    // =====================================================

    private ImageIcon icon(
            String path,
            int w,
            int h) {

        try {

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

        } catch (Exception e){

            System.out.println(
                    "Ícone não encontrado: "
                            + path
            );

            return null;
        }
    }

    // =====================================================
    // ESTILO BOTÃO MENU
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
}
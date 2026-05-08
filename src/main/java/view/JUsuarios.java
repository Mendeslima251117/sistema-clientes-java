package view;

import util.Tema;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class JUsuarios extends JFrame {

    private JTable tabelaUsuarios;
    private DefaultTableModel modelo;

    public JUsuarios() {

        setTitle("Gerenciamento de Usuários");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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

            new JCadastroUsuario(this)
                    .setVisible(true);
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

            String usuario =
                    modelo.getValueAt(linha,0)
                            .toString();

            String tipo =
                    modelo.getValueAt(linha,1)
                            .toString();

            new JCadastroUsuario(
                    usuario,
                    tipo
            ).setVisible(true);
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

                modelo.removeRow(linha);
            }
        });
    }

    // =====================================================
    // ADICIONAR USUÁRIO
    // =====================================================

    public void adicionarUsuario(
            String usuario,
            String tipo) {

        modelo.addRow(
                new Object[]{
                        usuario,
                        tipo
                }
        );
    }

    // =====================================================
    // CARREGAR
    // =====================================================

    private void carregar() {

        modelo.addRow(
                new Object[]{
                        "willian",
                        "ADMIN"
                }
        );

        modelo.addRow(
                new Object[]{
                        "kamila",
                        "ADMIN"
                }
        );
    }

    // =====================================================
    // ÍCONES
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
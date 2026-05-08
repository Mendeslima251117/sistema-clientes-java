package view;

import model.Usuario;
import util.UsuarioDAO;

import javax.swing.*;
import java.awt.*;

public class JCadastroUsuario extends JDialog {

    private JTextField txtUsuario;
    private JTextField txtSenha;
    private JComboBox<String> cbTipo;

    private UsuarioDAO dao = new UsuarioDAO();

    private JUsuarios telaUsuarios;

    public JCadastroUsuario(JUsuarios telaUsuarios) {

        this.telaUsuarios = telaUsuarios;

        setTitle("Cadastro de Usuário");

        setSize(350, 280);

        setLocationRelativeTo(null);

        setModal(true);

        setLayout(new BorderLayout());

        getContentPane().setBackground(
                new Color(25,25,25)
        );

        // =====================================================
        // TOPO
        // =====================================================

        JPanel topo = new JPanel();

        topo.setBackground(new Color(15,15,15));

        JLabel titulo =
                new JLabel("Novo Usuário");

        titulo.setForeground(Color.WHITE);

        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        topo.add(titulo);

        add(topo, BorderLayout.NORTH);

        // =====================================================
        // FORM
        // =====================================================

        JPanel form =
                new JPanel(
                        new GridLayout(6,1,5,5)
                );

        form.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        form.setBackground(
                new Color(25,25,25)
        );

        JLabel l1 = new JLabel("Usuário");
        l1.setForeground(Color.WHITE);

        txtUsuario = new JTextField();

        JLabel l2 = new JLabel("Senha");
        l2.setForeground(Color.WHITE);

        txtSenha = new JTextField();

        JLabel l3 = new JLabel("Tipo");
        l3.setForeground(Color.WHITE);

        cbTipo =
                new JComboBox<>(
                        new String[]{
                                "ADMIN",
                                "USER"
                        }
                );

        form.add(l1);
        form.add(txtUsuario);

        form.add(l2);
        form.add(txtSenha);

        form.add(l3);
        form.add(cbTipo);

        add(form, BorderLayout.CENTER);

        // =====================================================
        // BOTÕES
        // =====================================================

        JPanel botoes = new JPanel();

        botoes.setBackground(
                new Color(25,25,25)
        );

        JButton btnSalvar =
                new JButton("Salvar");

        JButton btnCancelar =
                new JButton("Cancelar");

        btnSalvar.setBackground(
                new Color(0,120,215)
        );

        btnSalvar.setForeground(Color.WHITE);

        btnCancelar.setBackground(
                new Color(180,30,30)
        );

        btnCancelar.setForeground(Color.WHITE);

        botoes.add(btnSalvar);
        botoes.add(btnCancelar);

        add(botoes, BorderLayout.SOUTH);

        // =====================================================
        // SALVAR
        // =====================================================

        btnSalvar.addActionListener(e -> {

            String usuario =
                    txtUsuario.getText();

            String senha =
                    txtSenha.getText();

            String tipo =
                    cbTipo.getSelectedItem()
                            .toString();

            if(usuario.isEmpty()
                    || senha.isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos!"
                );

                return;
            }

            Usuario u = new Usuario();

            u.setUsuario(usuario);

            u.setSenha(senha);

            u.setTipo(tipo);

            // SALVA NO BANCO
            dao.salvar(u);

            // RECARREGA TABELA
            telaUsuarios.recarregarTabela();

            JOptionPane.showMessageDialog(
                    this,
                    "Usuário salvo com sucesso!"
            );

            dispose();
        });

        // =====================================================
        // CANCELAR
        // =====================================================

        btnCancelar.addActionListener(e -> {

            dispose();
        });
    }
}
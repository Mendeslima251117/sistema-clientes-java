package view;

import util.Tema;

import javax.swing.*;
import java.awt.*;

public class JLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public JLogin() {

        setTitle("Login");

        // 🔥 AUMENTADO PARA NÃO CORTAR BOTÃO
        setSize(400, 320);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(null);
        painel.setBackground(Tema.BG_PRINCIPAL);

        // ===== TÍTULO =====
        JLabel titulo = new JLabel("Sistema de Clientes");
        titulo.setBounds(20, 20, 300, 35);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        painel.add(titulo);

        // ===== USUÁRIO =====
        JLabel lblUsuario = new JLabel("Usuário");
        lblUsuario.setBounds(40, 90, 100, 25);
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(Tema.FONTE_BOLD);
        painel.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(140, 90, 180, 35);
        txtUsuario.setBackground(new Color(50, 50, 50));
        txtUsuario.setForeground(Color.WHITE);
        txtUsuario.setCaretColor(Color.WHITE);
        txtUsuario.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        painel.add(txtUsuario);

        // ===== SENHA =====
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setBounds(40, 140, 100, 25);
        lblSenha.setForeground(Color.WHITE);
        lblSenha.setFont(Tema.FONTE_BOLD);

        painel.add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(140, 140, 180, 35);
        txtSenha.setBackground(new Color(50, 50, 50));
        txtSenha.setForeground(Color.WHITE);
        txtSenha.setCaretColor(Color.WHITE);
        txtSenha.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        painel.add(txtSenha);

        // ===== CADASTRAR =====
        JLabel lblCadastrar = new JLabel("Cadastrar novo usuário");
        lblCadastrar.setBounds(140, 190, 180, 20);
        lblCadastrar.setForeground(new Color(70, 130, 180));
        lblCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painel.add(lblCadastrar);

        lblCadastrar.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

            	new JCadastroUsuario(new JUsuarios()).setVisible(true);

            }
        });

        // ===== BOTÃO LOGIN =====
        JButton btnEntrar = new JButton("Entrar");

        // 🔥 DESCIDO PARA NÃO CORTAR
        btnEntrar.setBounds(120, 230, 150, 35);

        btnEntrar.setBackground(new Color(70, 130, 180));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setFont(Tema.FONTE_BOLD);

        painel.add(btnEntrar);

        // ===== LOGIN =====
        btnEntrar.addActionListener(e -> login());

        add(painel);
    }

    // ===== LOGIN =====
    private void login() {

        String usuario =
                txtUsuario.getText();

        String senha =
                new String(
                        txtSenha.getPassword()
                );

        if(usuario.isEmpty() || senha.isEmpty()){

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha usuário e senha!"
            );

            return;
        }

        try {

            util.UsuarioDAO dao =
                    new util.UsuarioDAO();

            String senhaCriptografada =
                    util.SenhaUtil.criptografar(senha);

            boolean ok =
                    dao.validarLogin(
                            usuario,
                            senhaCriptografada
                    );

            if(ok){

                String tipo =
                        dao.getTipo(usuario);

                dispose();

                new JPrincipal(
                        usuario,
                        tipo
                ).setVisible(true);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuário ou senha inválidos!"
                );
            }

        } catch (Exception e){

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao fazer login!"
            );
        }
    }
    // ===== MAIN =====
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new JLogin().setVisible(true);

        });
    }
}
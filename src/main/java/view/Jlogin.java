package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import session.Session;
import util.UsuarioDAO;

public class Jlogin extends JFrame {

    private JTextField usuario;
    private JPasswordField senha;

    public Jlogin() {

        setTitle("Login");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔷 HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(20, 20, 20));
        header.setPreferredSize(new Dimension(0, 60));

        JLabel titulo = new JLabel(" Sistema de Clientes");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        header.add(titulo, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // 🔷 FORM
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(30, 30, 30));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // USUÁRIO
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(label("Usuário"), gbc);

        gbc.gridx = 1;
        usuario = campo();
        form.add(usuario, gbc);

        // SENHA
        gbc.gridx = 0; gbc.gridy++;
        form.add(label("Senha"), gbc);

        gbc.gridx = 1;
        senha = campoSenha();
        form.add(senha, gbc);

        // 🔥 PLACEHOLDER USUÁRIO
        usuario.setText("Digite seu usuário");
        usuario.setForeground(Color.GRAY);

        usuario.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (usuario.getText().equals("Digite seu usuário")) {
                    usuario.setText("");
                    usuario.setForeground(Color.WHITE);
                }
            }

            public void focusLost(FocusEvent e) {
                if (usuario.getText().isEmpty()) {
                    usuario.setText("Digite seu usuário");
                    usuario.setForeground(Color.GRAY);
                }
            }
        });

        // 🔥 PLACEHOLDER SENHA
        senha.setEchoChar((char) 0);
        senha.setText("Digite sua senha");
        senha.setForeground(Color.GRAY);

        senha.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(senha.getPassword()).equals("Digite sua senha")) {
                    senha.setText("");
                    senha.setForeground(Color.WHITE);
                    senha.setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                if (senha.getPassword().length == 0) {
                    senha.setEchoChar((char) 0);
                    senha.setText("Digite sua senha");
                    senha.setForeground(Color.GRAY);
                }
            }
        });

        // LINK CADASTRO
        gbc.gridx = 1; gbc.gridy++;
        JLabel cadastro = new JLabel("Cadastrar novo usuário");
        cadastro.setForeground(new Color(100,180,255));
        cadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cadastro.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new JCadastroUsuario().setVisible(true);
                dispose();
            }
        });

        form.add(cadastro, gbc);

        add(form, BorderLayout.CENTER);

        // 🔷 BOTÃO
        JPanel rodape = new JPanel();
        rodape.setBackground(new Color(20,20,20));

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBackground(new Color(33,150,243));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(120, 35));

        rodape.add(btnLogin);
        add(rodape, BorderLayout.SOUTH);

        // 🔥 EVENTOS
        btnLogin.addActionListener(e -> logar());
        senha.addActionListener(e -> logar());

        SwingUtilities.invokeLater(() -> usuario.requestFocusInWindow());
    }

    // 🔧 CAMPO TEXTO
    private JTextField campo() {
        JTextField c = new JTextField();

        c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c.setBackground(new Color(45,45,45));
        c.setForeground(Color.WHITE);
        c.setCaretColor(Color.WHITE);
        c.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        c.setOpaque(true);

        return c;
    }

    // 🔧 CAMPO SENHA
    private JPasswordField campoSenha() {
        JPasswordField c = new JPasswordField();

        c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c.setBackground(new Color(45,45,45));
        c.setForeground(Color.WHITE);
        c.setCaretColor(Color.WHITE);
        c.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        return c;
    }

    // 🔧 LABEL PADRÃO
    private JLabel label(String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return l;
    }

    // 🔐 LOGIN
    private void logar() {

        String user = usuario.getText().trim();
        String pass = new String(senha.getPassword());

        if (user.equals("Digite seu usuário")) user = "";
        if (pass.equals("Digite sua senha")) pass = "";

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos!");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.validarLogin(user, pass)) {

            Session.setUsuario(user);
            Session.setTipo(dao.getTipo(user));

            dispose();

            JPrincipal p = new JPrincipal();
            p.setLocationRelativeTo(null);
            p.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos!");
            senha.setText("");
            senha.requestFocus();
        }
    }

    public static void main(String[] args) {
        new Jlogin().setVisible(true);
    }
}
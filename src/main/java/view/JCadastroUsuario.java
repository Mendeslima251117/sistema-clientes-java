package view;

import util.Tema;

import javax.swing.*;
import java.awt.*;

public class JCadastroUsuario extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JComboBox<String> cbTipo;

    private String usuarioEdicao = null;

    // 🔥 REFERÊNCIA DA TELA
    private JUsuarios telaUsuarios;

    // ===== CONSTRUTOR PADRÃO =====
    public JCadastroUsuario() {

        setTitle("Cadastro de Usuário");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(Tema.header("Cadastro de Usuário"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Tema.BG_PRINCIPAL);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Usuário
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(label("Usuário"), gbc);

        gbc.gridx = 1;
        txtUsuario = campo();
        form.add(txtUsuario, gbc);

        // Senha
        gbc.gridx = 0; gbc.gridy++;
        form.add(label("Senha"), gbc);

        gbc.gridx = 1;
        txtSenha = new JPasswordField(15);
        txtSenha.setBackground(new Color(50,50,50));
        txtSenha.setForeground(Color.WHITE);
        txtSenha.setCaretColor(Color.WHITE);
        form.add(txtSenha, gbc);

        // Tipo
        gbc.gridx = 0; gbc.gridy++;
        form.add(label("Tipo"), gbc);

        gbc.gridx = 1;
        cbTipo = new JComboBox<>(new String[]{"ADMIN", "USER"});
        form.add(cbTipo, gbc);

        // Botão
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(70,130,180));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setFont(Tema.FONTE_BOLD);

        form.add(btnSalvar, gbc);

        add(form, BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvar());
    }

    // 🔥 CONSTRUTOR COM REFERÊNCIA (NOVO)
    public JCadastroUsuario(JUsuarios telaUsuarios) {
        this();
        this.telaUsuarios = telaUsuarios;
    }

    // ===== EDITAR =====
    public JCadastroUsuario(String usuario, String tipo) {
        this();
        this.usuarioEdicao = usuario;

        txtUsuario.setText(usuario);
        cbTipo.setSelectedItem(tipo);
    }

    // ===== SALVAR =====
    private void salvar() {

        String usuario = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());
        String tipo = cbTipo.getSelectedItem().toString();

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        if (usuarioEdicao == null) {

            // 🔥 ATUALIZA A TABELA AUTOMATICAMENTE
            if (telaUsuarios != null) {
                telaUsuarios.adicionarUsuario(usuario, tipo);
            }

            JOptionPane.showMessageDialog(this, "Usuário cadastrado!");

        } else {
            JOptionPane.showMessageDialog(this, "Usuário atualizado!");
        }

        dispose();
    }

    private JLabel label(String txt) {
        JLabel l = new JLabel(txt);
        l.setForeground(Color.WHITE);
        l.setFont(Tema.FONTE_BOLD);
        return l;
    }

    private JTextField campo() {
        JTextField t = new JTextField(15);
        t.setBackground(new Color(50,50,50));
        t.setForeground(Color.WHITE);
        t.setCaretColor(Color.WHITE);
        t.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return t;
    }
}
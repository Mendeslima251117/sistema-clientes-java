package view;

import model.Cliente;
import util.MongoConnection;
import util.Tema;
import util.ValidarDocumento;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

import java.awt.*;

public class JCadastro extends JFrame {

    private JFormattedTextField cpf;
    private JTextField nome;
    private JTextField email;
    private JFormattedTextField telefone;
    private JTextField endereco;

    private JPrincipal principal;

    private int linhaEdicao = -1;

    private Cliente clienteEdicao;

    // =====================================================
    // NOVO
    // =====================================================

    public JCadastro(JPrincipal principal) {

        this.principal = principal;

        iniciarTela();
    }

    // =====================================================
    // EDITAR
    // =====================================================

    public JCadastro(
            JPrincipal principal,
            Cliente cliente,
            int linha) {

        this.principal = principal;
        this.linhaEdicao = linha;
        this.clienteEdicao = cliente;

        iniciarTela();

        preencherCampos(cliente);
    }

    // =====================================================
    // TELA
    // =====================================================

    private void iniciarTela() {

        setTitle("Cadastro de Cliente");
        setSize(500, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(Tema.header("Cadastro de Cliente"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());

        form.setBackground(Tema.BG_PRINCIPAL);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // =====================================================
        // CPF
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 0;

        form.add(label("CPF/CNPJ"), gbc);

        gbc.gridx = 1;

        try {

            MaskFormatter cpfMask =
                    new MaskFormatter("###.###.###-##");

            cpfMask.setPlaceholderCharacter('_');

            cpf = new JFormattedTextField(cpfMask);

            estilizarCampo(cpf);

            form.add(cpf, gbc);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // =====================================================
        // NOME
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy++;

        form.add(label("Nome"), gbc);

        gbc.gridx = 1;

        nome = campo();

        form.add(nome, gbc);

        // =====================================================
        // EMAIL
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy++;

        form.add(label("E-mail"), gbc);

        gbc.gridx = 1;

        email = campo();

        form.add(email, gbc);

        // =====================================================
        // TELEFONE
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy++;

        form.add(label("Telefone"), gbc);

        gbc.gridx = 1;

        try {

            MaskFormatter telMask =
                    new MaskFormatter("(##) #####-####");

            telMask.setPlaceholderCharacter('_');

            telefone = new JFormattedTextField(telMask);

            estilizarCampo(telefone);

            form.add(telefone, gbc);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // =====================================================
        // ENDEREÇO
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy++;

        form.add(label("Endereço"), gbc);

        gbc.gridx = 1;

        endereco = campo();

        form.add(endereco, gbc);

        // =====================================================
        // BOTÃO SALVAR
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridwidth = 2;

        JButton btnSalvar = new JButton("Salvar");

        btnSalvar.setBackground(new Color(70,130,180));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setFont(Tema.FONTE_BOLD);

        btnSalvar.setPreferredSize(
                new Dimension(180,35)
        );

        form.add(btnSalvar, gbc);

        add(form, BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvar());
    }

    // =====================================================
    // PREENCHER CAMPOS
    // =====================================================

    private void preencherCampos(Cliente cliente) {

        cpf.setText(cliente.getCpfCnpj());

        nome.setText(cliente.getNome());

        email.setText(cliente.getEmail());

        telefone.setText(cliente.getTelefone());

        endereco.setText(cliente.getEndereco());
    }

    // =====================================================
    // SALVAR
    // =====================================================

    private void salvar() {

        String cpfTxt = cpf.getText();
        String nomeTxt = nome.getText();
        String emailTxt = email.getText();
        String telefoneTxt = telefone.getText();
        String enderecoTxt = endereco.getText();

        // =====================================================
        // VALIDAÇÃO CAMPOS
        // =====================================================

        if (cpfTxt.contains("_")
                || telefoneTxt.contains("_")
                || nomeTxt.isBlank()
                || emailTxt.isBlank()
                || enderecoTxt.isBlank()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos!"
            );

            return;
        }

        // =====================================================
        // VALIDAR CPF/CNPJ
        // =====================================================

        if (!ValidarDocumento.isCpfOuCnpjValido(cpfTxt)) {

            JOptionPane.showMessageDialog(
                    this,
                    "CPF/CNPJ inválido!"
            );

            return;
        }

        // =====================================================
        // MONGO
        // =====================================================

        MongoCollection<Document> col =
                MongoConnection
                        .getDatabase()
                        .getCollection("clientes");

        // =====================================================
        // EDITAR
        // =====================================================

        if (linhaEdicao != -1) {

            Document doc = new Document();

            doc.put("cpfCnpj", cpfTxt);
            doc.put("nome", nomeTxt);
            doc.put("email", emailTxt);
            doc.put("telefone", telefoneTxt);
            doc.put("endereco", enderecoTxt);

            col.updateOne(
                    new Document(
                            "_id",
                            new ObjectId(clienteEdicao.getId())
                    ),
                    new Document("$set", doc)
            );

            Cliente cliente = new Cliente();

            cliente.setId(clienteEdicao.getId());
            cliente.setCpfCnpj(cpfTxt);
            cliente.setNome(nomeTxt);
            cliente.setEmail(emailTxt);
            cliente.setTelefone(telefoneTxt);
            cliente.setEndereco(enderecoTxt);

            principal.atualizarCliente(
                    linhaEdicao,
                    cliente
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente atualizado!"
            );
        }

        // =====================================================
        // NOVO
        // =====================================================

        else {

            Document doc = new Document();

            doc.put("cpfCnpj", cpfTxt);
            doc.put("nome", nomeTxt);
            doc.put("email", emailTxt);
            doc.put("telefone", telefoneTxt);
            doc.put("endereco", enderecoTxt);

            col.insertOne(doc);

            Cliente cliente = new Cliente();

            cliente.setId(
                    doc.getObjectId("_id").toString()
            );

            cliente.setCpfCnpj(cpfTxt);
            cliente.setNome(nomeTxt);
            cliente.setEmail(emailTxt);
            cliente.setTelefone(telefoneTxt);
            cliente.setEndereco(enderecoTxt);

            principal.adicionarCliente(cliente);

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente cadastrado!"
            );
        }

        dispose();
    }

    // =====================================================
    // LABEL
    // =====================================================

    private JLabel label(String txt) {

        JLabel l = new JLabel(txt);

        l.setForeground(Color.WHITE);
        l.setFont(Tema.FONTE_BOLD);

        return l;
    }

    // =====================================================
    // CAMPO
    // =====================================================

    private JTextField campo() {

        JTextField t = new JTextField(15);

        estilizarCampo(t);

        return t;
    }

    // =====================================================
    // ESTILO CAMPO
    // =====================================================

    private void estilizarCampo(JTextField t) {

        t.setPreferredSize(
                new Dimension(180,30)
        );

        t.setBackground(new Color(50,50,50));
        t.setForeground(Color.WHITE);
        t.setCaretColor(Color.WHITE);

        t.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        5,
                        5,
                        5
                )
        );
    }
}
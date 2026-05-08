package model;

public class Cliente {

    private String id;
    private String cpfCnpj;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;

    // ===== CONSTRUTOR VAZIO =====
    public Cliente() {
    }

    // ===== CONSTRUTOR COMPLETO =====
    public Cliente(
            String cpfCnpj,
            String nome,
            String email,
            String telefone,
            String endereco) {

        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    // ===== GETTERS =====

    public String getId() {
        return id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    // ===== SETTERS =====

    public void setId(String id) {
        this.id = id;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
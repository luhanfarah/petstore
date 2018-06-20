package team.tamba.petstore.entities;

import com.orm.SugarRecord;

public class Usuario extends SugarRecord<Usuario> {

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String cpf;
    private Endereco endereco;

    public Usuario() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String password) {
        this.senha = password;
    }

    public boolean isPasswordValid(String senha) {
        return this.senha.equalsIgnoreCase(senha);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}

package team.tamba.petstore.entities;

import com.orm.SugarRecord;

public class Endereco extends SugarRecord<Endereco> {

    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;


    public Endereco() {

    }

    public Endereco(String logradouro, String numero, String bairro, String cep) {
        this.setLogradouro(logradouro);
        this.setNumero(numero);
        this.setBairro(bairro);
        this.setCep(cep);
    }


    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}

package team.tamba.petstore.entities;

import com.orm.SugarRecord;

public class Produto extends SugarRecord<Produto> {

    private TipoProduto tipo;
    private float preco;
    private int quantidadeEstoque;
    private String descricao;
    private String imagePath;

    public Produto() {

    }

    public Produto(TipoProduto tipo, float preco, int quantidadeEstoque, String descricao, String imagePath) {
        this.tipo = tipo;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.descricao = descricao;
        this.imagePath = imagePath;

    }

    public TipoProduto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProduto tipo) {
        this.tipo = tipo;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getPrecoAsString() {
        return String.valueOf(this.preco);
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getQuantidadeEstoqueAsString() {
        return String.valueOf(quantidadeEstoque);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

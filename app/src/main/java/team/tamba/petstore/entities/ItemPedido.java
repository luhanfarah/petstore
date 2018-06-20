package team.tamba.petstore.entities;

import com.orm.SugarRecord;

public class ItemPedido extends SugarRecord<ItemPedido> {

    private int quantidade;
    private Produto produto;
    private Pedido pedido;

    public ItemPedido() {

    }

    public ItemPedido(int quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}

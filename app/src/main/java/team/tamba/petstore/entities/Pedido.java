package team.tamba.petstore.entities;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class Pedido extends SugarRecord<Pedido> {

    private List<ItemPedido> itens;
    private float total;
    private Usuario cliente;
    private int isActive;

    public Pedido() {

    }

    public List<ItemPedido> getItens() {
        if (this.itens == null) {
            this.itens = new ArrayList<>();
            this.save();
        }
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public float getTotal() {
        total = 0;
        for (ItemPedido item : this.itens) {
            total += item.getProduto().getPreco() * item.getQuantidade();
        }
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public boolean isActive() {
        return isActive == 1;
    }

    public void setActive(boolean active) {
        if (active) {
            isActive = 1;
        } else {
            isActive = 0;
        }

    }
}

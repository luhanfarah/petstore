package team.tamba.petstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team.tamba.petstore.entities.ItemPedido;
import team.tamba.petstore.entities.Pedido;

public class CartListAdapter extends ArrayAdapter<ItemPedido> {

    private Context context;
    private List<ItemPedido> itemPedidoList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private Pedido pedido;
    private TextView tvQuantidade;
    private TextView tvTotal;

    private OnUpdateListener listener;

    public CartListAdapter(@NonNull Context context, int resource, @NonNull List<ItemPedido> itemPedidoList) {
        super(context, resource, itemPedidoList);
        this.context = context;
        this.itemPedidoList = itemPedidoList;
    }

    public void setOnUpdateListner(OnUpdateListener listener) {
        this.listener = listener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //Get View
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.cart_list_layout, parent, false);

        //Get Current Item List
        final ItemPedido currentItemPedido = itemPedidoList.get(position);

        //Get Image Drawable and Set Resource
        ImageView ivImage = listItem.findViewById(R.id.ivImage);
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(currentItemPedido.getProduto().getImagePath(), "drawable",
                context.getPackageName());
        ivImage.setImageResource(resourceId);

        TextView tvDescricao = listItem.findViewById(R.id.tvDescricao);
        tvDescricao.setText(currentItemPedido.getProduto().getDescricao());

        TextView tvPreco = listItem.findViewById(R.id.tvPreco);
        tvPreco.setText(currentItemPedido.getProduto().getPrecoAsString());

        TextView tvEstoque = listItem.findViewById(R.id.tvEstoque);
        tvEstoque.setText(currentItemPedido.getProduto().getQuantidadeEstoqueAsString());

        tvQuantidade = listItem.findViewById(R.id.tvQuantidade);
        tvQuantidade.setText(Integer.toString(currentItemPedido.getQuantidade()));

        tvTotal = listItem.findViewById(R.id.tvTotal);
        tvTotal.setText(String.valueOf(currentItemPedido.getQuantidade() * currentItemPedido.getProduto().getPreco()));

        sharedPreferences = context.getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        long pedidoID = sharedPreferences.getLong(MainActivity.PEDIDO_ID, 0);
        pedido = Pedido.findById(Pedido.class, pedidoID);


        Button btAdd = listItem.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quantidadeAtual = currentItemPedido.getQuantidade();
                quantidadeAtual++;
                currentItemPedido.setQuantidade(quantidadeAtual);
                currentItemPedido.save();

                tvQuantidade.setText(String.valueOf(quantidadeAtual));

                float total = currentItemPedido.getQuantidade() * currentItemPedido.getProduto().getPreco();
                tvTotal.setText(String.valueOf(total));

                notifyDataSetChanged();

                if (listener != null) {
                    listener.onUpdate();
                }

            }
        });

        Button btRem = listItem.findViewById(R.id.btRem);
        btRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quantidadeAtual = currentItemPedido.getQuantidade();
                if (quantidadeAtual > 1) {
                    quantidadeAtual--;
                    currentItemPedido.setQuantidade(quantidadeAtual);
                    currentItemPedido.save();

                    tvQuantidade.setText(String.valueOf(quantidadeAtual));

                    float total = currentItemPedido.getQuantidade() * currentItemPedido.getProduto().getPreco();
                    tvTotal.setText(String.valueOf(total));
                } else {
                    Toast.makeText(context, "Quantidade mínima atingida!", Toast.LENGTH_SHORT).show();
                }

                notifyDataSetChanged();

                if (listener != null) {
                    listener.onUpdate();
                }


            }
        });

        return listItem;
    }

    public interface OnUpdateListener {
        void onUpdate();
    }

}

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
import team.tamba.petstore.entities.Produto;

public class ListAdapter extends ArrayAdapter<Produto> {

    private Context context;
    private List<Produto> produtoList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private Pedido pedido;
    private ItemPedido itemPedido;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<Produto> produtoList) {
        super(context, resource, produtoList);
        this.context = context;
        this.produtoList = produtoList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //Get View
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.home_list_layout, parent, false);
        //Get Current List Product
        final Produto currentProduto = produtoList.get(position);

        //Get Image Drawable and Set Resource
        ImageView ivImage = listItem.findViewById(R.id.ivImage);
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(currentProduto.getImagePath(), "drawable",
                context.getPackageName());
        ivImage.setImageResource(resourceId);

        TextView tvDescricao = listItem.findViewById(R.id.tvDescricao);
        tvDescricao.setText(currentProduto.getDescricao());

        TextView tvPreco = listItem.findViewById(R.id.tvPreco);
        tvPreco.setText(currentProduto.getPrecoAsString());

        TextView tvEstoque = listItem.findViewById(R.id.tvEstoque);
        tvEstoque.setText(currentProduto.getQuantidadeEstoqueAsString());

        sharedPreferences = context.getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        long pedidoID = sharedPreferences.getLong(MainActivity.PEDIDO_ID, 0);
        pedido = Pedido.findById(Pedido.class, pedidoID);
        itemPedido = new ItemPedido();

        Button btAdd = listItem.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemPedido.setProduto(currentProduto);
                itemPedido.setQuantidade(1);
                itemPedido.setPedido(pedido);
                itemPedido.save();

                Toast.makeText(context, "Adicionado ao Carrinho!", Toast.LENGTH_SHORT).show();

            }
        });


        return listItem;
    }

}

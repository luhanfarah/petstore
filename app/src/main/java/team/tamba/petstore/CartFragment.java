package team.tamba.petstore;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team.tamba.petstore.entities.ItemPedido;
import team.tamba.petstore.entities.Pedido;
import team.tamba.petstore.entities.Usuario;


public class CartFragment extends android.app.Fragment implements CartListAdapter.OnUpdateListener {

    private SharedPreferences sharedPreferences;


    private OnFragmentInteractionListener mListener;

    private ListView lista;
    private List<ItemPedido> itemPedidoList;
    private ArrayAdapter arrayAdapter;
    private Pedido pedido;

    private TextView tvTotalPedido;

    private CartListAdapter.OnUpdateListener listener;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        sharedPreferences = getContext().getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        Usuario usuario = Usuario.findById(Usuario.class, sharedPreferences.getLong(LoginActivity.USER_ID, 0));
        List<Pedido> pedidoList = Pedido.find(Pedido.class, "cliente = ?", String.valueOf(usuario.getId()));
        long pedidoID = 0;

        for (Pedido pedido :
                pedidoList) {
            if (pedido.isActive()) {
                pedidoID = pedido.getId();
            }
        }

        pedido = Pedido.findById(Pedido.class, pedidoID);

        itemPedidoList = ItemPedido.find(ItemPedido.class, "pedido = ?", String.valueOf(pedido.getId()));

        tvTotalPedido = view.findViewById(R.id.tvPedido);
        tvTotalPedido.setText("Total do Pedido: R$ " + String.valueOf(calculateTotal(itemPedidoList)));

        lista = view.findViewById(R.id.lvCartView);
        arrayAdapter = new CartListAdapter(getContext(), R.layout.cart_list_layout, itemPedidoList);
        lista.setAdapter(arrayAdapter);


        Button btFinalizar = view.findViewById(R.id.btFinalizar);
        btFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pedido.setActive(false);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Pedido Finalizado! Obrigado", Toast.LENGTH_SHORT).show();
                createNewPedido();
                restartPedido();

            }
        });


    }

    private float calculateTotal(List<ItemPedido> pedidos) {
        float total = 0;
        for (ItemPedido pedido :
                pedidos) {
            total += pedido.getQuantidade() * pedido.getProduto().getPreco();
        }
        return total;
    }

    private void restartPedido() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainer, new CartFragment(), "Cart");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void createNewPedido() {
        sharedPreferences = getContext().getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        Usuario usuario = Usuario.findById(Usuario.class, sharedPreferences.getLong(LoginActivity.USER_ID, 0));


        Pedido pedido = new Pedido();
        pedido.setCliente(usuario);
        pedido.setActive(true);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.save();
        List<ItemPedido> itemPedidoList = new ArrayList<>();
        itemPedidoList.add(itemPedido);
        pedido.setItens(itemPedidoList);

        pedido.setTotal(0.0f);
        pedido.save();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(MainActivity.PEDIDO_ID, pedido.getId());
        editor.commit();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            listener = (CartListAdapter.OnUpdateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onUpdate() {
        arrayAdapter.notifyDataSetChanged();
        String pretext = "Total do Pedido: R$ ";
        tvTotalPedido.setText(pretext + String.valueOf(calculateTotal(itemPedidoList)));

    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}

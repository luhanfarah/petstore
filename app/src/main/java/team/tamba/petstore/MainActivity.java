package team.tamba.petstore;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import team.tamba.petstore.entities.ItemPedido;
import team.tamba.petstore.entities.Pedido;
import team.tamba.petstore.entities.Usuario;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        CartFragment.OnFragmentInteractionListener,
        AccountFragment.OnFragmentInteractionListener,
        CartListAdapter.OnUpdateListener {

    public static final String PEDIDO_ID = "PedidoID";

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private SharedPreferences sharedPreferences;
    private Usuario usuario;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.frameContainer, new HomeFragment(), "Home");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                case R.id.navigation_cart:
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.frameContainer, new CartFragment(), "Cart");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                case R.id.navigation_account:
                    transaction = manager.beginTransaction();

                    AccountFragment accountFragment = new AccountFragment();

                    Bundle args = new Bundle();
                    args.putLong(LoginActivity.USER_ID, usuario.getId());
                    accountFragment.setArguments(args);

                    transaction.replace(R.id.frameContainer, accountFragment, "Account");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Start with Home Fragment
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.frameContainer, new HomeFragment(), "Home");
        transaction.addToBackStack(null);
        transaction.commit();

        //Get User Info Back
        sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        usuario = Usuario.findById(Usuario.class, sharedPreferences.getLong(LoginActivity.USER_ID, 0));

        long pedidoID = sharedPreferences.getLong(PEDIDO_ID, 0);
        if (pedidoID == -1 || pedidoID == 0) {
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
            editor.putLong(PEDIDO_ID, pedido.getId());
            editor.commit();
        } else {
            Pedido pedido = Pedido.findById(Pedido.class, pedidoID);
            if (!pedido.isActive()) {
                pedido = new Pedido();
                pedido.setCliente(usuario);
                pedido.setActive(true);
                pedido.save();
            }
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onUpdate() {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.frameContainer, new CartFragment(), "Cart");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

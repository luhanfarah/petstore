package team.tamba.petstore;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import team.tamba.petstore.entities.Endereco;
import team.tamba.petstore.entities.Usuario;

public class AccountFragment extends android.app.Fragment {

    private long userID;

    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private EditText etSenha2;
    private EditText etCPF;
    private EditText etTelefone;
    private EditText etLogradouro;
    private EditText etNumero;
    private EditText etBairro;
    private EditText etCEP;
    private Button btSalvar;

    private Usuario usuario;

    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userID = getArguments().getLong(LoginActivity.USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etNome = view.findViewById(R.id.etNome);
        etEmail = view.findViewById(R.id.etEmail);
        etSenha = view.findViewById(R.id.etSenha);
        etSenha2 = view.findViewById(R.id.etSenha2);
        etCPF = view.findViewById(R.id.etCPF);
        etTelefone = view.findViewById(R.id.etTelefone);
        etLogradouro = view.findViewById(R.id.etLogradouro);
        etNumero = view.findViewById(R.id.etNumero);
        etBairro = view.findViewById(R.id.etBairro);
        etCEP = view.findViewById(R.id.etCEP);
        btSalvar = view.findViewById(R.id.btSalvar);

        usuario = Usuario.findById(Usuario.class, userID);

        etNome.setText(usuario.getNome());
        etEmail.setText(usuario.getEmail());
        etCPF.setText(usuario.getCpf());
        etTelefone.setText(usuario.getTelefone());
        etLogradouro.setText(usuario.getEndereco().getLogradouro());
        etNumero.setText(usuario.getEndereco().getNumero());
        etBairro.setText(usuario.getEndereco().getBairro());
        etCEP.setText(usuario.getEndereco().getCep());
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllChanges();
                Toast.makeText(getContext(), "Salvo", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAllChanges() {
        if (!etNome.getText().toString().equalsIgnoreCase(usuario.getNome())) {
            usuario.setNome(etNome.getText().toString());
        }
        if (!etEmail.getText().toString().equalsIgnoreCase(usuario.getEmail())) {
            usuario.setEmail(etEmail.getText().toString());
        }
        if (!etCPF.getText().toString().equalsIgnoreCase(usuario.getCpf())) {
            usuario.setCpf(etCPF.getText().toString());
        }
        if (!etTelefone.getText().toString().equalsIgnoreCase(usuario.getTelefone())) {
            usuario.setTelefone(etTelefone.getText().toString());
        }

        Endereco endereco = Endereco.findById(Endereco.class, usuario.getEndereco().getId());

        if (!etLogradouro.getText().toString().equalsIgnoreCase(endereco.getLogradouro())) {
            endereco.setLogradouro(etLogradouro.getText().toString());
        }
        if (!etNumero.getText().toString().equalsIgnoreCase(endereco.getNumero())) {
            endereco.setNumero(etNumero.getText().toString());
        }
        if (!etBairro.getText().toString().equalsIgnoreCase(endereco.getBairro())) {
            endereco.setBairro(etBairro.getText().toString());
        }
        if (!etCEP.getText().toString().equalsIgnoreCase(endereco.getCep())) {
            endereco.setCep(etCEP.getText().toString());
        }
        endereco.save();
        usuario.save();

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

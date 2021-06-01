package dam.gala.damgame.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.damgame.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterDialogFragment extends DialogFragment {
    private AlertDialog.Builder builder;
    private LinearLayout lyRegisterMain, lyRegister;
    private EditText etEmail,etNameUser,etpPass,etpPassConfirmed;
    private Button btnRegistrar,btnVolver;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.builder=new AlertDialog.Builder(getActivity());
        View dialogView = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_register, null);
        this.builder.setView(dialogView);
        this.lyRegisterMain=dialogView.findViewById(R.id.lyRegisterMain);
        this.lyRegister=dialogView.findViewById(R.id.lyRegister);
        this.etEmail=dialogView.findViewById(R.id.etEmail);
        this.etNameUser=dialogView.findViewById(R.id.etNameUser);
        this.etpPass=dialogView.findViewById(R.id.etpPass);
        this.etpPassConfirmed=dialogView.findViewById(R.id.etpPassConfirmed);
        this.btnRegistrar=dialogView.findViewById(R.id.btnRegistrar);
        this.btnVolver=dialogView.findViewById(R.id.btnVolver);
        this.btnRegistrar.setOnClickListener(v->{
            if(this.etEmail.getText().length()!=0&&this.etNameUser.getText().length()!=0
                    &&this.etpPass.getText().length()!=0&&this.etpPassConfirmed.getText().length()!=0){
                if (this.etpPass.getText().toString().contentEquals(this.etpPassConfirmed.getText().toString())){
                    Map<String, String> map = new HashMap<>();
                    map.put("nameUser", String.valueOf(etNameUser.getText()));
                    map.put("password", String.valueOf(etpPass.getText()));
                    map.put("email", String.valueOf(etEmail.getText()));

                    RequestQueue rq = Volley.newRequestQueue(getActivity());
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://192.168.18.6:8080/register",
                            new JSONObject(map),
                            response -> {
                                Toast.makeText(this.getActivity(), R.string.usuario_registrado,Toast.LENGTH_SHORT).show();
                                RegisterDialogFragment.this.dismiss();
                            },
                            error -> {
                                Toast.makeText(this.getActivity(), error.getMessage(),Toast.LENGTH_SHORT);
                                etNameUser.setError(getResources().getString(R.string.usuario_existente));
                            });
                    rq.add(jsonObjectRequest);

                }else{
                    this.etpPass.setError(getResources().getString(R.string.contraseñas_diferentes));
                    this.etpPassConfirmed.setError(getResources().getString(R.string.contraseñas_diferentes));
                }
            }else{

                if(this.etEmail.getText().length()==0)this.etEmail.setError(getResources().getString(R.string.error_campo_vacio));
                if(this.etNameUser.getText().length()==0)this.etNameUser.setError(getResources().getString(R.string.error_campo_vacio));
                if(this.etpPass.getText().length()==0)this.etpPass.setError(getResources().getString(R.string.error_campo_vacio));
                if(this.etpPassConfirmed.getText().length()==0)this.etpPassConfirmed.setError(getResources().getString(R.string.error_campo_vacio));

            }
        });
        this.btnVolver.setOnClickListener(v->{
            RegisterDialogFragment.this.dismiss();
        });
        lyRegister.setBackground(getResources().getDrawable(R.drawable.register_in));
        etNameUser.setTextColor(Color.WHITE);
        etEmail.setTextColor(Color.WHITE);
        etpPass.setTextColor(Color.WHITE);
        etpPassConfirmed.setTextColor(Color.WHITE);
        btnRegistrar.setBackgroundColor(getResources().getColor(R.color.buton_register));
        btnVolver.setBackgroundColor(getResources().getColor(R.color.buton_register));
        Dialog dialog =  builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}

package dam.gala.damgame.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.damgame.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringTokenizer;

import dam.gala.damgame.activities.GameActivity;
import dam.gala.damgame.model.Email;
import dam.gala.damgame.model.Play;
import dam.gala.damgame.model.Player;
import dam.gala.damgame.utils.GameUtil;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Dialogo que saldra en el fin del juego ya sea por derrota o victoria
 * @author Juan Antonio Escribano Diaz
 * @version 1.0
 */
public class FinishDialogFragment extends DialogFragment {
    private Play play;
    private AlertDialog.Builder builder;
    private double duracion;
    private int minutos=0, segundos=0;
    /**
     * Constructor del dialogo de finalización
     */
    public FinishDialogFragment(Play play){
        this.play=play;
    }

    /**
     * Es el metodo de callback de DialogFragment entrara por aqui antes de mostrar el
     * dialog
     * @param savedInstanceState Contenedor para almacenar información del fragmento
     * @return devuelve el dialog que se mostrara
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.builder=new AlertDialog.Builder(getActivity());
        View viewD=getActivity().getLayoutInflater().inflate(R.layout.dialog_finish, null);
        builder.setView(viewD);
        duracion=play.getEndDateTime()-play.getStarDateTime();
        BigDecimal bd = new BigDecimal(duracion/60000).setScale(2, RoundingMode.HALF_UP);
        duracion = bd.doubleValue();
        StringTokenizer sT = new StringTokenizer(String.valueOf(duracion));
        minutos=Integer.valueOf(sT.nextToken("."));
        Double valorIni=Double.valueOf("0."+sT.nextToken("."));
        segundos=Integer.valueOf((int) (valorIni*60));

        Button btnAceptar=viewD.findViewById(R.id.btnAceptar), btnEnviar=viewD.findViewById(R.id.btnEnviar);
        LinearLayout lyFinishMain, lyinfo;
        lyFinishMain=viewD.findViewById(R.id.lyFinishMain);
        lyinfo=viewD.findViewById(R.id.lyInfo);
        TextView tvResult, tvNameUser,tvPoint, tvDuraction;
        tvResult=viewD.findViewById(R.id.tvResult);
        tvNameUser=viewD.findViewById(R.id.tvNick);
        tvPoint=viewD.findViewById(R.id.tvPoint);
        tvDuraction=viewD.findViewById(R.id.tvDuraction);
        tvNameUser.setText(this.play.getPlayer().getNameUser());
        tvPoint.setText(String.valueOf(this.play.getPoints()));
        tvDuraction.setText(String.valueOf(duracion));
        int tema = Integer.valueOf(getDefaultSharedPreferences(getActivity()).getString("ambient_setting",String.valueOf(GameUtil.TEMA_HIELO)));
        switch(tema){
            case GameUtil.TEMA_DESIERTO:
                tvResult.setText("DesertWorld");
                break;
            case GameUtil.TEMA_HIELO:
                lyFinishMain.setBackground(getResources().getDrawable(R.drawable.ice_dialog_border_out));
                lyinfo.setBackground(getResources().getDrawable(R.drawable.ice_dialog_border_out));
                tvResult.setText("IceWorld");
                tvResult.setTextColor(getResources().getColor(R.color.ice_text));
                btnAceptar.setBackground(getResources().getDrawable(R.drawable.ice_dialog_button));
                break;
            default:

                break;
        }
        btnAceptar.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), GameActivity.class);
            intent.putExtra("player", new String[]{
                    this.play.getPlayer().getNameUser(),
                    this.play.getPlayer().getPassword(),
                    this.play.getPlayer().getEmail()
            });
            startActivity(intent);
        });
        btnEnviar.setOnClickListener(v->{
            Email email = new Email(this.play.getPlayer().getEmail(), "Estadisticas DamGame","Usuario: "+this.play.getPlayer().getNameUser()+
                    "\nPuntos: "+String.valueOf(this.play.getPoints())+"\nDuracion de la Partida: "+minutos+":"+segundos);
            email.execute();
        });
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}

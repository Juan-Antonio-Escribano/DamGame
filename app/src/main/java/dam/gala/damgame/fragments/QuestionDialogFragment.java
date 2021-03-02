package dam.gala.damgame.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import com.example.damgame.R;
import dam.gala.damgame.interfaces.InterfaceDialog;
import dam.gala.damgame.model.Question;
import dam.gala.damgame.utils.GameUtil;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Cuadro de diálogo para mostrar la pregunta cunado se produce una colisión entre
 * flappy y un obstáculo
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class QuestionDialogFragment extends DialogFragment
{
    private Question question;
    private AlertDialog.Builder builder;
    private Button btConfirmar;
    private InterfaceDialog interfaceDialog;

    /**
     * Construye el cuadro de diálogo de la pregunta e inicializa la pregunta y la interfaz
     * de comunicación con el fragmento
     * @param question Información de la pregunta y respuesta
     * @param interfaceDialog Interfaz de comunicación entre el fragmento y la actividad que
     *                        lo muestra
     */
    public QuestionDialogFragment(Question question, InterfaceDialog interfaceDialog){
        this.question = question;
        this.interfaceDialog = interfaceDialog;
    }

    /**
     * Método de callback que se produce antes de mostrar el cuadro de diálogo
     * @param savedInstanceState Contenedor para almacenar información del fragmento
     * @return Cuadro de diálogo con la pregunta (Dialog)
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        switch(this.question.getTipo()) {
            case GameUtil.PREGUNTA_MULTIPLE:
                break;
            case GameUtil.PREGUNTA_SIMPLE:
                return crearPreguntaSimple();
            case GameUtil.PREGUNTA_LISTA:
                break;
        }
        return null;
    }
    /**
     * Devuelve el cuadro de diálogo para una pregunta de tipo simple
     * @return Dialog el cuadro de diálogo
     */
    private Dialog crearPreguntaSimple(){
        //RADIOBUTTON
        View dialogView = getActivity().getLayoutInflater().
                inflate(R.layout.pregunta_simple,null);
        builder.setView(dialogView);
        TextView tvEnunciado = dialogView.findViewById(R.id.tvPregunta);
        tvEnunciado.setText(question.getEnunciado());
        RadioGroup rgRes = dialogView.findViewById(R.id.rgRes);
        for(int i=0;i<rgRes.getChildCount();i++) {
            RadioButton rbRes = (RadioButton)rgRes.getChildAt(i);
            if (i > question.getRespuestas().length-1)
                rbRes.setVisibility(View.GONE);
            else {
                rbRes.setText(question.getRespuestas()[i]);
                rbRes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btConfirmar.setEnabled(true);
                    }
                });
            }
        }

        LinearLayout lyQuestionDialog = dialogView.findViewById(R.id.lyQuestionDialog);

        LinearLayout lyQuestion = dialogView.findViewById(R.id.lyQuestion);

        LinearLayout lyPuntos = dialogView.findViewById(R.id.lyPuntos);

        RadioGroup rgPuntos = dialogView.findViewById(R.id.rgPuntos);

        if(question.getComplejidad()== GameUtil.PREGUNTA_COMPLEJIDAD_ALTA) {
            rgPuntos.setVisibility(View.VISIBLE);
            RadioButton rbPuntos = dialogView.findViewById(R.id.rbPuntos);
            rbPuntos.setText(question.getPuntos()+ getString(R.string.puntos));
        }
        this.btConfirmar = dialogView.findViewById(R.id.btOk);
        this.btConfirmar.setEnabled(false);

        this.btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String respuesta = ((RadioButton)dialogView.findViewById(rgRes.getCheckedRadioButtonId())).getText().toString();
                interfaceDialog.setRespuesta(respuesta);
                QuestionDialogFragment.this.dismiss();
            }
        });

        //personalizar según el tema elegido
        String tema = getDefaultSharedPreferences(getActivity()).getString("theme_setting","100");
        switch(tema){
            case "@string/TEMA_DESIERTO":
            default:
                lyQuestionDialog.setBackground(ResourcesCompat.getDrawable(
                        this.getResources(),R.drawable.desert_dialog_border_out,
                        this.getActivity().getTheme()));
                lyQuestion.setBackground(ResourcesCompat.getDrawable(
                        this.getResources(),R.drawable.desert_bg,
                        this.getActivity().getTheme()));
                lyPuntos.setBackground(ResourcesCompat.getDrawable(
                        this.getResources(),R.drawable.desert_dialog_border_out,
                        this.getActivity().getTheme()));
                rgPuntos.setBackground(ResourcesCompat.getDrawable(
                        this.getResources(),R.drawable.desert_dialog_border_in,
                        this.getActivity().getTheme()));
                btConfirmar.setBackground(ResourcesCompat.getDrawable(
                        this.getResources(),R.drawable.desert_dialog_border_out,
                        this.getActivity().getTheme()));
                break;
        }

        Dialog dialog =  builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}

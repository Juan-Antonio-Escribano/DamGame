package dam.gala.damgame.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.example.damgame.R;

import dam.gala.damgame.activities.GameActivity;
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
    private GameActivity gameActivity;

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
    public QuestionDialogFragment(Question question, InterfaceDialog interfaceDialog, GameActivity gameActivity){
        this(question,interfaceDialog);
        this.gameActivity=gameActivity;
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
                return crearPreguntaCompleja();
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
            if (i > question.getRespuestas().size()-1)
                rbRes.setVisibility(View.GONE);
            else {
                rbRes.setText(question.getRespuestas().get(i));
                rbRes.setOnClickListener(view -> btConfirmar.setEnabled(true));
            }
        }

        LinearLayout lyQuestionDialog = dialogView.findViewById(R.id.lyQuestionDialog);

        LinearLayout lyQuestion = dialogView.findViewById(R.id.lyQuestion);

        LinearLayout lyPuntos = dialogView.findViewById(R.id.lyPuntos);

        RadioGroup rgPuntos = dialogView.findViewById(R.id.rgPuntos);
        RadioButton rbPuntos = dialogView.findViewById(R.id.rbPuntos);
        RadioButton rbVida = dialogView.findViewById(R.id.rbVida);

        if(question.getComplejidad()==GameUtil.PREGUNTA_COMPLEJIDAD_ALTA) {
            rgPuntos.setVisibility(View.VISIBLE);
            rbPuntos.setText(question.getPuntos()+ getString(R.string.puntos));
        }else{
            rgPuntos.setVisibility(View.VISIBLE);
            rbPuntos.setText(question.getPuntos()+ getString(R.string.puntos));
        }

        this.btConfirmar = dialogView.findViewById(R.id.btOk);
        this.btConfirmar.setEnabled(false);

        this.btConfirmar.setOnClickListener(view -> {
            String respuesta = ((RadioButton)dialogView.findViewById(rgRes.getCheckedRadioButtonId())).getText().toString();
            if(question.getRespuestas().indexOf(respuesta)==question.getRespuestasCorrectas()[0]){
                this.gameActivity.updateAnswersCorrect();
                if(rbVida.isChecked()){
                    this.gameActivity.getGameMove().setLifes(this.gameActivity.getGameMove().getLifes()+1);
                    this.gameActivity.updateLifesAdd();
                }else
                    this.gameActivity.updatePoints(question.getPuntos());
                this.interfaceDialog.setRespuesta("Respuesta Correcta");
            }else interfaceDialog.setRespuesta("Respuesta Incorrecta");

            this.gameActivity.updateQuestionCatched();
            QuestionDialogFragment.this.dismiss();
        });

        //personalizar según el tema elegido
        int tema = Integer.valueOf(getDefaultSharedPreferences(getActivity()).getString("ambient_setting",String.valueOf(GameUtil.TEMA_HIELO)));
        switch(tema){
            case GameUtil.TEMA_DESIERTO:

                break;
            case GameUtil.TEMA_HIELO:
                lyQuestionDialog.setBackground(getResources().getDrawable(R.drawable.ice_dialog_border_out));
                lyQuestion.setBackground(getResources().getDrawable(R.drawable.ice_dialog_border_out));
                tvEnunciado.setTextColor(getResources().getColor(R.color.ice_text));
                for(int i=0;i<rgRes.getChildCount();i++) {
                    RadioButton rbRes = (RadioButton)rgRes.getChildAt(i);
                    if (i < question.getRespuestas().size())
                        rbRes.setTextColor(getResources().getColor(R.color.ice_text));
                }
                for(int i=0;i<rgPuntos.getChildCount();i++) {
                    RadioButton rbP = (RadioButton)rgPuntos.getChildAt(i);
                    rbP.setTextColor(getResources().getColor(R.color.ice_text));
                }
                btConfirmar.setBackground(getResources().getDrawable(R.drawable.ice_dialog_button));
                break;
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
    /**
     * Devuelve el cuadro de diálogo para una pregunta de tipo complejo
     * @return Dialog el cuadro de diálogo
     */
    private Dialog crearPreguntaCompleja(){
        View dialogView = getActivity().getLayoutInflater().
                inflate(R.layout.pregunta_seleccion,null);
        builder.setView(dialogView);
        TextView tvEnunciado = dialogView.findViewById(R.id.tvPregunta);
        tvEnunciado.setText(question.getEnunciado());
        LinearLayout ly = dialogView.findViewById(R.id.lyCheck);
        for(int i=0;i<ly.getChildCount();i++) {
          CheckBox checkBox = (CheckBox) ly.getChildAt(i);
          if (i>question.getRespuestas().size()-1)
              checkBox.setVisibility(View.GONE);
          else {
              checkBox.setText(question.getRespuestas().get(i));
              checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                  if(isChecked)
                      btConfirmar.setEnabled(true);
                  else {
                      for (int i1 = 0; i1 <ly.getChildCount(); i1++){
                          CheckBox checkBox1 = (CheckBox) ly.getChildAt(i1);
                          if (checkBox1.isChecked()){
                              btConfirmar.setEnabled(true);
                              break;
                          }else btConfirmar.setEnabled(false);
                      }
                  }
              });
          }
        }

        LinearLayout lyQuestionDialog = dialogView.findViewById(R.id.lyQuestionDialog);

        LinearLayout lyQuestion = dialogView.findViewById(R.id.lyQuestion);

        LinearLayout lyPuntos = dialogView.findViewById(R.id.lyPuntos);

        RadioGroup rgPuntos = dialogView.findViewById(R.id.rgPuntos);
        RadioButton rbPuntos = dialogView.findViewById(R.id.rbPuntos);
        RadioButton rbVida = dialogView.findViewById(R.id.rbVida);

        if(question.getComplejidad()==GameUtil.PREGUNTA_COMPLEJIDAD_ALTA) {
            rgPuntos.setVisibility(View.VISIBLE);
            rbPuntos.setText(question.getPuntos()+ getString(R.string.puntos));
        }else{
            rgPuntos.setVisibility(View.VISIBLE);
            rbPuntos.setText(question.getPuntos()+ getString(R.string.puntos));
        }

        this.btConfirmar = dialogView.findViewById(R.id.btOk);
        this.btConfirmar.setEnabled(false);

        this.btConfirmar.setOnClickListener(view -> {
            String respuestasCorrectas = "",respuestasIncorrectas="";
            Boolean incorrecto=false;
            for (int i1 = 0; i1 <ly.getChildCount(); i1++){
                CheckBox checkBox1 = (CheckBox) ly.getChildAt(i1);
                if (checkBox1.isChecked()){
                    for (int j=0;j<question.getRespuestasCorrectas().length;j++){
                        if (question.getRespuestas().indexOf(checkBox1.getText())==question.getRespuestasCorrectas()[j]){
                            incorrecto=false;
                            respuestasCorrectas+="/"+checkBox1.getText();
                            break;
                        }else incorrecto=true;
                    }
                    if(incorrecto) respuestasIncorrectas+="/"+checkBox1.getText();
                }
            }
            if (respuestasIncorrectas.length()<1){
                if(rbVida.isChecked()){
                    this.gameActivity.getGameMove().setLifes(this.gameActivity.getGameMove().getLifes()+1);
                    this.gameActivity.updateLifesAdd();
                }else
                    this.gameActivity.updatePoints(question.getPuntos());
                this.gameActivity.updateAnswersCorrect();
            }
            interfaceDialog.setRespuesta("Respuestas correctas "+respuestasCorrectas+"\nRespuestas incorrectas "+respuestasIncorrectas);
            this.gameActivity.updateQuestionCatched();
            QuestionDialogFragment.this.dismiss();
        });

        //personalizar según el tema elegido
        int tema = Integer.valueOf(getDefaultSharedPreferences(getActivity()).getString("ambient_setting",String.valueOf(GameUtil.TEMA_HIELO)));
        switch(tema){
            case GameUtil.TEMA_DESIERTO:
                break;
            case GameUtil.TEMA_HIELO:
                lyQuestionDialog.setBackground(getResources().getDrawable(R.drawable.ice_dialog_border_out));
                lyQuestion.setBackground(getResources().getDrawable(R.drawable.ice_dialog_border_out));
                tvEnunciado.setTextColor(getResources().getColor(R.color.ice_text));
                btConfirmar.setBackground(getResources().getDrawable(R.drawable.ice_dialog_button));
                break;
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

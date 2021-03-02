package dam.gala.damgame.model;

/**
 * Pregunta
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class Question {
    private String enunciado;
    private int complejidad;
    private int tipo;
    private CharSequence[]respuestas;
    private int[] respuestasCorrectas;
    private int puntos;
    //TODO este constructor es provisional, habrá que quitarlo cuando se tengan las preguntas
    //de las preguntas
    public Question(){

    }
    /**
     * Construye una pregunta a partir de las propiedades indicadas como parámetros
     * @param enunciado Enunciado de la pregunta
     * @param complejidad Complejidad de la pregunta (ALTA O BAJA)
     * @param tipo Tipo de pregunta (respuesta simple o respuesta múltiple)
     * @param respuestas Respuestas a la pregunta
     * @param respuestasCorrectas Índices de las respuestas correctas
     * @param puntos Puntuación de la pregunta
     */
    public Question(String enunciado, int complejidad
        , int tipo, CharSequence[] respuestas,
                    int[] respuestasCorrectas, int puntos){
        this.enunciado=enunciado;
        this.complejidad = complejidad;
        this.tipo = tipo;
        this.respuestas = respuestas;
        this.respuestasCorrectas = respuestasCorrectas;
        this.puntos = puntos;
    }

    //-----------------------------------------------------------------------------------------
    //Métodos getter y setter para propiedades la pregunta
    //-----------------------------------------------------------------------------------------
    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public int getComplejidad() {
        return complejidad;
    }

    public void setComplejidad(int complejidad) {
        this.complejidad = complejidad;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public CharSequence[] getRespuestas() {
        return respuestas;
    }

    public int[] getRespuestasCorrectas() {
        return respuestasCorrectas;
    }

    public int getPuntos() {
        return puntos;
    }
}

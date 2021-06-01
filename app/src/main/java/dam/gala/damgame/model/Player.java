package dam.gala.damgame.model;

/**
 * Jugador
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class Player {
    private String nameUser;
    private String password;
    private String email;

    /**
     * Construye el objeto del jugador a partir de nick, clave y correo electrónico
     * @param nameUser Usuario del juego
     * @param password Clave del jugador
     * @param email Correo electrónico, se usará para enviar estadísticas de juego
     */
    public Player(String nameUser, String password, String email) {
        this.nameUser = nameUser;
        this.password = password;
        this.email = email;
    }

    //-----------------------------------------------------------------------------------------
    //Métodos getter y setter
    //-----------------------------------------------------------------------------------------

    public String getNameUser() {
        return nameUser;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

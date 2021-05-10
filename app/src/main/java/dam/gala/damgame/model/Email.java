package dam.gala.damgame.model;

import android.os.AsyncTask;

import java.util.Properties;

import dam.gala.damgame.utils.GameUtil;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Esta clase define el email y contiene herramientas para el envio de un correo
 * personalizado
 * @author Juan Antonio Escribano Diaz
 * @version 1.0
 */
public class Email extends AsyncTask<Void,Void,Void> {
    private String destinatario, asunto, cuerpo;
    public Email(String destinatario, String asunto, String cuerpo){
        this.destinatario=destinatario;
        this.asunto=asunto;
        this.cuerpo=cuerpo;
    }

    /**
     * Metodo sobrescrito. Se encarga de enviar un correo
     * @param voids
     * @return
     */
    @Override
    protected Void doInBackground(Void... voids) {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", GameUtil.EMAIL);
        props.put("mail.smtp.password", GameUtil.PASSWORD);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(GameUtil.EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", GameUtil.EMAIL, GameUtil.PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
        return null;
    }
}


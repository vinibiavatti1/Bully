package geral;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilidades gerais da aplicação.
 * 
 * @author Vinícius R. Biavatti
 */
public class Util {
    
    /**
     * Esperar em milisegundos
     * @param millis 
     */
    public static void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(Processo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Esperar em segundos
     * @param segundos 
     */
    public static void delaySegundos(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Processo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

package utilidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author profe
 */
public class Utilidades  {

    public static boolean esPrimo(long numero) {
        boolean primo= true;
        long candidatoDivisor= 2;
        while (candidatoDivisor < numero && primo) {
            try {
                Thread.sleep (0, 2);  // Para ralentizar el proceso
            } catch (InterruptedException ex) {
                
            }
            if (numero % candidatoDivisor == 0) {
                primo= false;
            } else
                candidatoDivisor++;                       
        }        
        return primo;
    }
    
    public static String getFechaHoraActualFormateada() {
        String patronFormato= "dd/MM/yyyy HH:mm:ss:SSS";
        DateTimeFormatter formato= DateTimeFormatter.ofPattern(patronFormato);
        LocalDateTime ahora= LocalDateTime.now();
        return ahora.format(formato);
    }
    
    public static String obtenerFecha() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss")).toString();
    }
// Este es un mÃ©todo mÃ¡s eficiente y rÃ¡pido para el test de primalidad
// Pero para estos ejercicios necesitamos precisamente algo mÃ¡s lento    
/*    boolean esPrimo(long numero) {
        boolean primo= true;
        long candidatoDivisor= 3;
        if (numero % 2 == 0) {
            primo= false;
        }
        while (candidatoDivisor < (int) Math.sqrt(numero) && !primo) {
            if (numero % candidatoDivisor == 0)
                primo= false;
            else
                candidatoDivisor +=2;                       
        }        
        return primo;
    }
*/    
    
}

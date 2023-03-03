/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cliente;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que define un cliente Http que recibe desde consola una URL,
 * realiza una peticion HTTP a esa URL, muestra por pantalla la respuesta
 * HTTP, muestra todas las cabeceras recibidas desde el servidor y si el 
 * contenido recibido es de tipo text/HTML lo almacena en un fichero de salida
 * @author Jose Manuel Saborido Noriega
 */
public class MainClienteHttp {
    
    private static final String RUTA_FICHERO_SALIDA = ".\\salida\\salida.html";
    private static URL url;
    private static URLConnection conn;
    private static final String URL_DEFAULT = "http://www.iesaguadulce.es/centro/index.php/oferta-formativa/formacion-profesional-a-distancia/";
    private static Map<String,List<String>> mapaCabecera;
    private static boolean isHTML = false;
    
    /**
     * Entrada al programa.
     * @param args argumentos(No se dispone de ninguno en esta palicación.)
     */
    public static void main(String[] args) {
        init();
        if(realizarPeticion()){
            mostrarCabecera();
            guardarSalida();
        } 
    }
    
    /**
     * Metodo que pinta el logo de la aplicacion por ponsola
     */
    private static void init(){
        StringBuilder sb = new StringBuilder();
        sb.append(" _____  _  _               _           _   _  _____  _____ ______\n");
        sb.append("/  __ \\| |(_)             | |         | | | ||_   _||_   _|| ___ \\\n");
        sb.append("| /  \\/| | _   ___  _ __  | |_   ___  | |_| |  | |    | |  | |_/ /\n");
        sb.append("| |    | || | / _ \\| '_ \\ | __| / _ \\ |  _  |  | |    | |  |  __/\n"); 
        sb.append("| \\__/\\| || ||  __/| | | || |_ |  __/ | | | |  | |    | |  | |\n");    
        sb.append(" \\____/|_||_| \\___||_| |_| \\__| \\___| \\_| |_/  \\_/    \\_/  \\_|\n"); 
        sb.append("============= Jose Manuel Saborido Noriega ===================\n");     
        System.out.println(sb.toString());
    }
    
    /**
     * Metodo que pide por consola que se introduzca una url para hacer la 
     * petición.
     * Si todo es correcto devuelve true
     * @exception MalformedURLException si la url no es correcta
     * @return true si todo es correcto y false si no lo es(saltará la excepción). 
     */
    private static boolean pedirUrl(){
        System.out.println("Introduce la URL a la que realizar la peticion:\n"
                + "(Si lo dejas en blanco se tomara una URL por defecto)\n");
        
        Scanner sc = new Scanner(System.in);
        String urlString = sc.nextLine();
        try {
            if(urlString.isBlank()){
                urlString = URL_DEFAULT;
            }
            System.out.println("Conectandose a  -> " + urlString + "\n");
            url = new URL(urlString);
            return true;
        } catch (MalformedURLException ex) {
            System.err.println("[ERROR] -> " + ex.fillInStackTrace());
            return false;
        }
    }
    
    /**
     * Metodo que realiza la peticion a la url y obtiene la cabecera.
     * @exception IOException si hay un problema de E/S
     * @return true si todo es correcto y false si no lo es(puede saltar la 
     * excepcion)
     */
    private static boolean realizarPeticion(){
        if(pedirUrl()){
            try {
                conn = url.openConnection();
                conn.connect();
                mapaCabecera = new HashMap();
                mapaCabecera = conn.getHeaderFields();
                return true;
            } catch (IOException ex) {
                System.err.println("[ERROR] -> E/S : " + ex.fillInStackTrace());
                return false;
            }
        }else{
            System.err.println("[ERROR] -> La URL facilitada no está bien formada.");
            return false;
        }
    } 
    
    /**
     * Muestra los datos de la cabecera por consola
     */
    private static void mostrarCabecera(){
        StringBuilder sb;
        System.out.println("###################");
        System.out.println("#    CABECERA     #");
        System.out.println("###################");
        for(String llaveMapa: mapaCabecera.keySet()){
            sb = new StringBuilder();
            if(llaveMapa == null){
                System.out.print("\n----> Respuesta:");
                for(String valorMapa: mapaCabecera.get(llaveMapa)){
                    sb.append("").append(valorMapa);
                }
                sb.append("\n\n");
                System.out.print(sb.toString());
            }else{
                System.out.print(llaveMapa + ":");
                for(String valorMapa: mapaCabecera.get(llaveMapa)){
                    if(llaveMapa.equals("Content-Type") && valorMapa.contains("text/html")){
                        isHTML = true;
                    }
                    sb.append("").append(valorMapa);
                }
                sb.append("\n");
                System.out.print(sb.toString());
            }
        }
    }
    
    /**
     * Si el contenido de la petición es codigo html lo guarda en el fichero de 
     * salida
     */
    private static void guardarSalida(){
        if(isHTML){
            System.out.println("\n\nGuardando HTML en fichero de salida...");
            try(InputStream is = conn.getInputStream()){
                byte[] allBytesStream= is.readAllBytes();
                FileOutputStream fos = new FileOutputStream(new File(RUTA_FICHERO_SALIDA));
                fos.write(allBytesStream);
                System.out.println("[OK] ---> Se guardo con exito el contenido en el fichero " + RUTA_FICHERO_SALIDA);
            } catch (IOException ex) {
                System.err.println("[KO] ---> Hubo un error al intentar guardar el contenido en el fichero de salida...");
                System.err.println("[ERROR] -> " + ex.fillInStackTrace());
            }
        }else{
            System.out.println("El contenido recuperado no es de tipo HTML");
        }
        
    }
}

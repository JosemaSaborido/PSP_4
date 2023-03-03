/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor2;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executors;
import servidor1.HttpHandlerSaludar;
import servidor2.HttpHandlerCalculadora;


/**
 * Clase que define el servidor Http.
 * Se iniciará en el puerto 80 si no se le asigna un puerto concreto.
 * Devolverá un saludo al nombre y apellido que se pase por parametros o
 * realizará calculos segun las operaciones que se le indique
 * 
 * @author Jose Manuel Saborido Noriega
 */
public class ServidorHttp {
    //ATRIBUTOS
    private static final int PUERTO_DEFAULT = 80;
    private static final String PATH_SALUDAR = "/saludar";
    private static final String PATH_CALCULADORA = "/calculadora";
    private static int puerto;
    private static HttpServer sv;
    //METODOS
    /**
     * Entrada al programa
     * @param args 
     */
    public static void main(String[] args) {
        init();
        solicitarPuerto();
        crearServidor(puerto);
    }
    
    /**
     * Metodo que pinta el logo de la aplicación por consola
     */
    private static void init(){
        StringBuilder sb = new StringBuilder();
        sb.append(" _____                     _      _                _   _  _____  _____ ______\n"); 
        sb.append("/  ___|                   (_)    | |              | | | ||_   _||_   _|| ___ \\\n");
        sb.append("\\ `--.   ___  _ __ __   __ _   __| |  ___   _ __  | |_| |  | |    | |  | |_/ /\n");
        sb.append(" `--. \\ / _ \\| '__|\\ \\ / /| | / _` | / _ \\ | '__| |  _  |  | |    | |  |  __/\n"); 
        sb.append("/\\__/ /|  __/| |    \\ V / | || (_| || (_) || |    | | | |  | |    | |  | |\n");    
        sb.append("\\____/  \\___||_|     \\_/  |_| \\__,_| \\___/ |_|    \\_| |_/  \\_/    \\_/  \\_|\n"); 
        sb.append("===================== Jose Manuel Saborido Noriega =========================\n");
        System.out.println(sb.toString());
    }
    
    /**
     * Metodo que solicita el puerto por el que se iniciará el servidor
     * @exception NumberFormatException si el formato del puerto no es el 
     * correcto
     */
    private static void solicitarPuerto(){
        System.out.println("Introduce el puerto en el que se montará el servidor:\n"
                + "(Si lo dejas en blanco se tomara el puerto por defecto)\n");
        
        Scanner sc = new Scanner(System.in);
        String puertoString = sc.nextLine();
        if(puertoString.isBlank()){
            puerto = PUERTO_DEFAULT;
        }else{
            try{
                puerto = Integer.parseInt(puertoString);
            }catch(NumberFormatException ex){
                System.err.println("[ERROR] -> El puerto facilitado no es "
                        + "un entero. Se porcede a asignar el puerto por defecto."
                        + " Puerto - " + PUERTO_DEFAULT);
                puerto = PUERTO_DEFAULT;
            }
        }
    }
    
    /**
     * Metodo que crea el servidor y configura sus parametros
     * @param puerto en el que se pondrá a la escucha
     * @exception IOException si hay algun error de E/S
     * @return true si se ha creado correctamente
     */
    private static boolean crearServidor(Integer puerto){
        try {
            System.out.println("Creando instanacia de servidor CONCURRENTE en el puerto " 
                    + puerto + "...");
            sv = HttpServer.create(new InetSocketAddress(puerto), 0);
            sv.createContext(PATH_SALUDAR, new HttpHandlerSaludar());
            sv.createContext(PATH_CALCULADORA, new HttpHandlerCalculadora());
            sv.setExecutor(Executors.newCachedThreadPool());
            sv.start();
            System.out.println("Servidor iniciado con exito. Esperando peticion ...");
            return true;
        } catch (IOException ex) {
            System.err.println("[ERROR] -> E/S. " + ex.fillInStackTrace());
            return false;
        }
    }
}

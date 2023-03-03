/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor1;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utilidades.Utilidades;

/**
 * Clase Handler que trata la consulta y genera una respuesta al explorador.
 * @author Josema Saborido
 */
public class HttpHandlerSaludar implements HttpHandler{
    //ATRIBUTOS
    private OutputStream os;
    private String respuesta;
    private HttpExchange httpex;
    private String queryRequest;

    //CONSTRUCTORES
    public HttpHandlerSaludar() {
    }

    //METODOS
    /**
     * Metodo de entrada del Handler
     * @param exchange solicitud HTTP
     * @throws IOException Se podría lanzar una excepcion de E/S
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.httpex = exchange;
        URI uri = this.httpex.getRequestURI();
        this.queryRequest = uri.getQuery();
        System.out.println("["+ Utilidades.obtenerFecha() +"] Atendiendo peticion -> " + queryRequest);
        this.obtenerParametrosRequest(this.queryRequest);
        os = this.httpex.getResponseBody();
        os.write(this.respuesta.getBytes());
        os.close();
    }

    /**
     * Metodo que obtiene los parametros de consulta. Valor del parametro nombre
     * y el valor del parametro apellido
     * @param queryRequest consulta HTTP
     */
    private void obtenerParametrosRequest(String queryRequest) {
        String nombre = "";
        String apellido = "";
        Pattern pt = Pattern.compile("nombre=([a-zA-Z]+)&apellido=([a-zA-Z]+)");
        Matcher m = pt.matcher(queryRequest);
        //Si hay conincidencias con la consulta
        if(m.matches()){
            //Descomponemos el query para obtener los paramtros
            String[] parametros = queryRequest.split("&");
            nombre = parametros[0].split("=")[1];
            apellido = parametros[1].split("=")[1];
        }
        this.componerResponse(nombre, apellido);
    }

    /**
     * Metodo que compone la respuesta
     * @param nombre valor de la peticion
     * @param apellido valor de la peticion
     * @exception IOException puede saltar un error de E/S
     */
    private void componerResponse(String nombre, String apellido) {
        try {
            if(nombre.isBlank() && apellido.isBlank()){
                this.respuesta = """
                                 <h1>ERROR!!!</h1>
                                 <h4>Persona no identificada.</h4>
                """;
                this.httpex.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, this.respuesta.length());
                System.err.println("["+ Utilidades.obtenerFecha() +"] Error. Peticion mal formada. Respuesta -> Persona no identificada.");
            }else{
                this.respuesta = "Hola, " + nombre + " " + apellido;
                this.httpex.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, this.respuesta.length());
                System.out.println("["+ Utilidades.obtenerFecha() +"] Respuesta a la peticion "+ this.queryRequest + " -> " + this.respuesta);
            }
        } catch (IOException ex) {
            System.err.println("[ERROR] -> E/S. " + ex.fillInStackTrace());
        }
    }

    
    
    //GETTER Y SETTER
    /**
     * @return the os
     */
    public OutputStream getOs() {
        return os;
    }

    /**
     * @param os the os to set
     */
    public void setOs(OutputStream os) {
        this.os = os;
    }

    /**
     * @return the respuesta
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * @param respuesta the respuesta to set
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }



    
}

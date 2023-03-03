/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor2;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utilidades.Calculadora;
import utilidades.Utilidades;

/**
 *
 * @author josem
 */
public class HttpHandlerCalculadora implements HttpHandler {
    //ATRIBUTOS
    private OutputStream os;
    private String respuesta;
    private HttpExchange httpex;
    private String queryRequest;
    private Calculadora calculadora;
    
    //CONSTRUCTORES
    public HttpHandlerCalculadora() {
    }
    
    //METODOS
    @Override
    public void handle(HttpExchange exchange){
        try {
            this.httpex = exchange;
            URI uri = this.httpex.getRequestURI();
            this.queryRequest = uri.getQuery();
            System.out.println("["+ Utilidades.obtenerFecha() +"] Atendiendo peticion -> " + queryRequest);
            this.obtenerParametrosRequest(this.queryRequest);
            os = this.httpex.getResponseBody();
            os.write(this.respuesta.getBytes());
            os.close();
        } catch (IOException ex) {
            System.err.println("[ERROR] -> E/S. " + ex.fillInStackTrace());
        }
    }

    /**
     * Metodo que obtiene los parametros de consulta.
     * @param queryRequest consulta HTTP
     */
    private void obtenerParametrosRequest(String queryRequest) {
        double op1 = 0, op2 = 0;
        String ope = "";
        Pattern pt = Pattern.compile("op1=([0-9]+)&op2=([0-9]+&ope=[a-zA-Z]+)");
        Matcher m = pt.matcher(queryRequest);
        //Si hay conincidencias con la consulta
        if(m.matches()){
            //Descomponemos el query para obtener los paramtros
            String[] parametros = queryRequest.split("&");
            op1 = Double.parseDouble(parametros[0].split("=")[1]);
            op2 = Double.parseDouble(parametros[1].split("=")[1]);
            ope = parametros[2].split("=")[1];
        }
        this.componerResponse(op1, op2, ope);
    }

    private void componerResponse(double op1, double op2, String ope) {
        StringBuilder sb = new StringBuilder();
        try {
            if(!ope.isBlank()){
                switch(ope){
                    case "suma":
                        sb.append(new Calculadora(op1, op2 ,
                                '+').getResultado());
                        this.respuestaOK(sb);
                        break;
                    case "resta":
                        sb.append(new Calculadora(op1, op2 ,
                                '-').getResultado());
                        this.respuestaOK(sb);
                        break;
                    case "multiplica":
                        sb.append(new Calculadora(op1, op2 ,
                                '*').getResultado());
                        this.respuestaOK(sb);
                        break;
                    case "divide":
                        sb.append(new Calculadora(op1, op2 ,
                                '/').getResultado());   
                        this.respuestaOK(sb);
                        break;
                    default:
                        this.respuestaBAD_REQUEST();
                        break;
                }
            }else{
                this.respuestaBAD_REQUEST();
            }
        } catch (IOException ex) {
            System.err.println("[ERROR] -> E/S. " + ex.fillInStackTrace());
        }
    }
    
    private void respuestaOK(StringBuilder sb) throws IOException{
        System.out.println("[" + Utilidades.obtenerFecha() + "]Respuesta a la peticion " + this.queryRequest
                        + "=> " + sb.toString());
        this.respuesta = sb.toString();
        this.httpex.sendResponseHeaders(HttpURLConnection.HTTP_OK,
                this.respuesta.length());
    }
    
    private void respuestaBAD_REQUEST() throws IOException{
        System.err.println("[" + Utilidades.obtenerFecha() + "]No se puede realizar la consulta. Peticion no valida.");
        this.respuesta = """
                         <h1>ERROR!!</h1>
                         <h4>No se puede realizar la consulta. Peticion no valida.</h4>
                         """;
        this.httpex.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,
                    this.respuesta.length());
    }
    
    //GETTER Y SETTER

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public HttpExchange getHttpex() {
        return httpex;
    }

    public void setHttpex(HttpExchange httpex) {
        this.httpex = httpex;
    }

    public String getQueryRequest() {
        return queryRequest;
    }

    public void setQueryRequest(String queryRequest) {
        this.queryRequest = queryRequest;
    }

    public Calculadora getCalculadora() {
        return calculadora;
    }

    public void setCalculadora(Calculadora calculadora) {
        this.calculadora = calculadora;
    }
}

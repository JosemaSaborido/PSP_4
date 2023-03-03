/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

/**
 *
 * @author Usuario
 */
public class Calculadora {

    private double num1;
    private double num2;
    private char operacion;
    private String resultado;

    public Calculadora(double num1, double num2, char operacion) {
        this.num1 = num1;
        this.num2 = num2;
        this.operacion = operacion;
        calcularResultado();
    }

    private void calcularResultado() {
        switch (operacion) {
            case '+':
                sumar();
                break;
            case '-':
                restar();
                break;
            case '*':
                multiplicar();
                break;
            case '/':
                dividir();
                break;
            default:
                resultado = "Operación no permitida. Los operadores vá¡lidos son ('+','-','*','/').";

        }
    }

    private void sumar() {
        resultado = num1 + " + " + num2 + " = " + (num1 + num2);
    }

    private void restar() {
        resultado = num1 + " - " + num2 + " = " + (num1 - num2);
    }

    private void multiplicar() {
        resultado = num1 + " * " + num2 + " = " + (num1 * num2);
    }

    private void dividir() {
        if (num2 == 0) {
            resultado = "No se puede dividir entre cero";
        } else {
            resultado = num1 + " / " + num2 + " = " + (num1 / num2);
        }
    }

    public double getNum1() {
        return num1;
    }

    public double getNum2() {
        return num2;
    }

    public char getOperacion() {
        return operacion;
    }

    public String getResultado() {
        return resultado;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public void setOperacion(char operacion) {
        this.operacion = operacion;
    }
}
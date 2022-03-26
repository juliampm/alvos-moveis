package com.example.testeav1.componentes;

public class Utils {
    public static double distanciaEuclidiana(double x1, double y1, int x2, int y2) {
        var valor = Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2);
        return Math.sqrt(valor);
    }
}

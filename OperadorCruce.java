import java.util.List;

public interface OperadorCruce extends Elemento {
    
    List<Solucion> generarHijos(List<Solucion> padres, int tamanioSolucion, int[][] costos);

    
}
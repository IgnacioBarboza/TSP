import java.util.List;

public interface OperadorCruce {
    
    List<Solucion> generarHijos(List<Solucion> padres, int cantidadHijos, int[][] costos);

    
    abstract List<Solucion> cruzar(Solucion p1, Solucion p2, int[][] costos);
}
import java.util.List;

public interface SeleccionadorSobrevivientes extends Elemento {
    
    List<Solucion> seleccionar(List<Solucion> padres,List<Solucion> hijos);
}

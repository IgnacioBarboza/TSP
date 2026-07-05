import java.util.ArrayList;
import java.util.List;

public class SeleccionHijosPorPadres implements SeleccionadorSobrevivientes {

    @Override
    public String getNombre() {
        return "Reemplazo Total (Hijos por Padres)";
    }

    @Override
    public List<Solucion> seleccionar(List<Solucion> poblacionActual, List<Solucion> hijosGenerados) {
        System.out.println("Size: "+hijosGenerados.size());
        return new ArrayList<>(hijosGenerados);
    }
}
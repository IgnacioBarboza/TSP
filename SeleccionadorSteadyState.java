import java.util.ArrayList;
import java.util.List;

public class SeleccionadorSteadyState implements SeleccionadorSobrevivientes {

    private int n; // La cantidad de individuos a reemplazar

    public SeleccionadorSteadyState(int n) {
        this.n = n;
    }

    @Override
    public String getNombre() {
        return "Steady State con reemplazo de " + n;
    }
    
    @Override
    public List<Solucion> seleccionar(List<Solucion> padres, List<Solucion> hijos) {
        

        List<Solucion> padresOrdenados = new ArrayList<>(padres);
        List<Solucion> hijosOrdenados = new ArrayList<>(hijos);

        padresOrdenados.sort((s1, s2) -> Double.compare(s1.getFitness(), s2.getFitness()));
        hijosOrdenados.sort((s1, s2) -> Double.compare(s1.getFitness(), s2.getFitness()));

        int reemplazosReales = Math.min(n, hijosOrdenados.size());

        for (int i = 0; i < reemplazosReales; i++) {
            padresOrdenados.remove(padresOrdenados.size() - 1);
        }

        for (int i = 0; i < reemplazosReales; i++) {
            padresOrdenados.add(hijosOrdenados.get(i));
        }

        return padresOrdenados;
    }


}
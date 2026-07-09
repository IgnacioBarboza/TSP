import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeleccionTorneo implements SeleccionadorPadres {
    
    private int tamañoTorneo;
    private Random random;

    // Constructor donde configuramos el tamaño del torneo (k)
    public SeleccionTorneo(int tamañoTorneo) {
        this.tamañoTorneo = tamañoTorneo;
        this.random = new Random();
    }
    @Override
    public String getNombre(){
        return "Por Torneo con tamaño de torneo: " + tamañoTorneo + " individuos";
    }
    @Override
    public List<Solucion> seleccionar(Poblacion poblacion, int cantidadPadres) {
        List<Solucion> padresSeleccionados = new ArrayList<>();
        for (int i = 0; i < cantidadPadres; i++) {
            Solucion ganador = realizarUnTorneo(poblacion);
            padresSeleccionados.add(ganador);
        }

        return padresSeleccionados;
    }

    private Solucion realizarUnTorneo(Poblacion poblacion) {
        Solucion mejorCandidato = null;

        for (int i = 0; i < tamañoTorneo; i++) {
            int indiceAlAzar = random.nextInt(poblacion.getCantidadSoluciones());
            Solucion candidatoActual = poblacion.get(indiceAlAzar);
            if (mejorCandidato == null || candidatoActual.getFitness() < mejorCandidato.getFitness()) {
                mejorCandidato = candidatoActual;
            }
        }

        return mejorCandidato;
    }
}
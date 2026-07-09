import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeleccionSobrevivientesTorneo implements SeleccionadorSobrevivientes {

    private int tamañoTorneo;
    private Random random;

    // Constructor donde configuramos el tamaño del torneo (k)
    public SeleccionSobrevivientesTorneo(int tamañoTorneo) {
        this.tamañoTorneo = tamañoTorneo;
        this.random = new Random();
    }
    @Override
    public String getNombre(){
        return "Por Torneo con tamaño de torneo: " + tamañoTorneo + " individuos";
    }


    // Método privado que hace la lógica de un solo torneo
    private Solucion realizarUnTorneo(List<Solucion> soluciones) {
        Solucion mejorCandidato = null;

        for (int i = 0; i < tamañoTorneo; i++) {
            int indiceAlAzar = random.nextInt(soluciones.size());
            Solucion candidatoActual = soluciones.get(indiceAlAzar);

            if (mejorCandidato == null || candidatoActual.getFitness() < mejorCandidato.getFitness()) {
                mejorCandidato = candidatoActual;
            }
        }

        return mejorCandidato; // El ganador del torneo
    }

    @Override
    public List<Solucion> seleccionar(List<Solucion> padres, List<Solucion> hijos) {
        List<Solucion> candidatos = new ArrayList<>();
        List<Solucion> sobrevivientes= new ArrayList<>();
        candidatos.addAll(padres);
        candidatos.addAll(hijos);
        for (int i = 0; i < padres.size(); i++) {
            Solucion ganador = realizarUnTorneo(candidatos);
            sobrevivientes.add(ganador);
        }

        return sobrevivientes;
    }

}
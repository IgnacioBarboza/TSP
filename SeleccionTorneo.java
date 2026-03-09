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
    public List<Solucion> seleccionar(Poblacion poblacion, int cantidadPadres) {
        List<Solucion> padresSeleccionados = new ArrayList<>();

        // Repetimos el torneo hasta conseguir la cantidad de padres requerida
        for (int i = 0; i < cantidadPadres; i++) {
            Solucion ganador = realizarUnTorneo(poblacion);
            padresSeleccionados.add(ganador);
        }

        return padresSeleccionados;
    }

    // Método privado que hace la lógica de un solo torneo
    private Solucion realizarUnTorneo(Poblacion poblacion) {
        Solucion mejorCandidato = null;

        for (int i = 0; i < tamañoTorneo; i++) {
            // 1. Elegir un individuo al azar de la población
            int indiceAlAzar = random.nextInt(poblacion.getCantidadSoluciones());
            Solucion candidatoActual = poblacion.get(indiceAlAzar);

            // 2. Comparar. ¡Ojo acá! Como es TSP, buscamos el MENOR fitness (distancia)
            if (mejorCandidato == null || candidatoActual.getFitness() < mejorCandidato.getFitness()) {
                mejorCandidato = candidatoActual;
            }
        }

        return mejorCandidato; // El ganador del torneo
    }

    /*private Solucion realizarUnTorneoImprimir(Poblacion poblacion) {
        System.out.println("=== Iniciando un nuevo Torneo (Tamaño: " + tamañoTorneo + ") ===");
        Solucion mejorCandidato = null;

        for (int i = 0; i < tamañoTorneo; i++) {
            System.out.println("  Competidor " + (i + 1) + " de " + tamañoTorneo + ":");
            
            // 1. Elegir un individuo al azar de la población
            int indiceAlAzar = random.nextInt(poblacion.getCantidadSoluciones());
            Solucion candidatoActual = poblacion.get(indiceAlAzar);
            
            System.out.println("    -> Seleccionado individuo en el índice [" + indiceAlAzar + "] de la población.");
            System.out.println("    -> Fitness del candidato: " + candidatoActual.getFitness());

            // 2. Comparar y decidir
            if (mejorCandidato == null) {
                // Caso especial: es el primer competidor del torneo
                System.out.println("    -> Decisión: Como es el primer competidor, automáticamente es el mejor por ahora.");
                mejorCandidato = candidatoActual;
                
            } else {
                // Ya hay un mejor candidato previo, toca competir
                System.out.println("    -> Comparando: Candidato Actual (" + candidatoActual.getFitness() + ") vs Mejor Actual (" + mejorCandidato.getFitness() + ")");
                
                if (candidatoActual.getFitness() < mejorCandidato.getFitness()) {
                    System.out.println("    -> Decisión: ¡Gana el candidato actual! Tiene un viaje más corto (menor fitness).");
                    mejorCandidato = candidatoActual;
                } else {
                    System.out.println("    -> Decisión: El candidato actual pierde y es descartado. Su viaje es igual o más largo.");
                }
            }
        }

        System.out.println("=== Fin del Torneo. El ganador definitivo tiene un fitness de: " + mejorCandidato.getFitness() + " ===\n");

        return mejorCandidato; // El ganador del torneo
    }
    */
}
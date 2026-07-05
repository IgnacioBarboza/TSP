import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Poblacion {
    
    //--------------------------Variables-------------------------------
    private List<Solucion> soluciones;
    private int cantidadSoluciones; // El límite o tamaño esperado de la población

    //--------------------------Constructores-------------------------------
    public Poblacion(int dimension) {
        this.cantidadSoluciones = dimension;
        // Inicializamos la Lista. Ya no necesitamos la variable 'indice'
        this.soluciones = new ArrayList<>();
    }
    //--------------------------Métodos de Ordenamiento-------------------------------

    public void ordenarPorFitnessMenorAMayor() {
        this.soluciones.sort(Comparator.comparingDouble(Solucion::getFitness));
    }

    public void ordenarPorFitnessMayorAMenor() {
        this.soluciones.sort(Comparator.comparingDouble(Solucion::getFitness).reversed());
    }

    public void ordenarPorFitnessInvertidoMayorAMenor() {
        this.soluciones.sort(Comparator.comparingDouble((Solucion s) -> 1.0 / s.getFitness()).reversed());
    }

    public void ordenarPorFitnessInvertidoMenorAMayor() {
        this.soluciones.sort(Comparator.comparingDouble((Solucion s) -> 1.0 / s.getFitness()));
    }
    //--------------------------Métodos-------------------------------
    
    public int getCantidadSoluciones() {
        return cantidadSoluciones;
    }

    public void setCantidadSoluciones(int cantidadSoluciones) {
        this.cantidadSoluciones = cantidadSoluciones;
    }

    public List<Solucion> getSoluciones() {
        return soluciones;
    }

    public void setSoluciones(List<Solucion> soluciones) {
        this.soluciones = soluciones;
    }

    public int getTamañoActual() {
        return this.soluciones.size();
    }

    public void pushSolucion(Solucion solucion){
        if (!isFull()) {
            this.soluciones.add(solucion);
        }
    }   
    public void popSolucion(){
        if (!isEmpty()) {
            this.soluciones.remove(this.soluciones.size() - 1);
        }
    }

    public boolean isFull(){
        return this.soluciones.size() >= cantidadSoluciones;
    }

    public boolean isEmpty(){
        return this.soluciones.isEmpty();
    }

    public void llenarRandom(int[][] costos){
        while (!isFull()) {
            this.soluciones.add(new Solucion(costos.length, costos));
        }
    }

    public Solucion get(int indice){
        return this.soluciones.get(indice);
    }

    public void mutarSoluciones(Mutador mutador,int[][] costos){
        for(Solucion s : this.soluciones){
            mutador.mutar(s,costos);
        }
    }
}
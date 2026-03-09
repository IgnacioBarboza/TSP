import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solucion {
    
    //------------------------------------Variables-----------------------------------------
    private double Fitness;
    private List<Integer> camino; 
    private int largoCamino;
    
    //------------------------------------Constructores-----------------------------------------
    
    public Solucion(List<Integer> camino, int[][] costos) {
        this.camino = new ArrayList<>(camino); 
        this.largoCamino = this.camino.size(); 
        this.Fitness = calcularFitness(costos);
    }

    public Solucion(int tamanio, int[][] costos) {
        this.largoCamino = tamanio;
        generarCaminoRandom();
        this.Fitness = calcularFitness(costos);
    }

    //--------------------------------------Métodos---------------------------------------
    
    public double getFitness() {
        return Fitness;
    }

    public double getFitnessInvertido() {
        return 1.0 / Fitness; // Aseguramos que la división sea con decimales usando 1.0
    }

    public List<Integer> getCamino() {
        return camino;
    }

    public void setCamino(List<Integer> camino) {
        this.camino = camino;
    }

    public void setFitness(double fitness) { 
        Fitness = fitness;
    }

    public void swapCaminos(int i, int j) {
        Collections.swap(this.camino, i, j);
    }
    
    private double calcularFitness(int[][] costos) {
        double fitness = 0;
        for (int i = 0; i < largoCamino; i++) {
            int origen = camino.get(i);
            int destino = camino.get((i + 1) % largoCamino);
            
            fitness += costos[origen][destino];
        }
        return fitness; 
    }

    public void generarCaminoRandom(){
        this.camino = new ArrayList<>(largoCamino);
        
        for (int i = 0; i < largoCamino; i++) {
            this.camino.add(i); 
        }
        
        Collections.shuffle(this.camino); 
    }

    public void imprimirCaminoLn(){
        for(int i = 0; i < largoCamino; i++){
            // Usamos .get()
            System.out.print(camino.get(i) + " ");
        }
        System.out.println();
    }

    public void imprimirCamino(){
        for(int i = 0; i < largoCamino; i++){
            System.out.print(camino.get(i) + " ");
        }
    }
}
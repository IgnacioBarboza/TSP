
import java.util.ArrayList;
import java.util.List;

public class Instancia {
    // --------------------------Variables-------------------------------
    private int dimensionCostos;
    private int cantidadSoluciones;
    private int[][] costos;
    private Poblacion poblacion;
    private SeleccionadorPadres selectorPadres;
    private OperadorCruce operadorCruce;
    private Mutador mutador;
    private SeleccionadorSobrevivientes seleccionadorSobrevivientes;
    private ArrayList<Double> mejoresFitness;
    private ArrayList<Double> peorFitness;

    // --------------------------Constructor-------------------------------
    public Instancia(int dimensionCostos, int[][] costos, int cantidadSoluciones, SeleccionadorPadres selectorPadres, OperadorCruce operadorCruce, Mutador mutador, SeleccionadorSobrevivientes seleccionadorSobrevivientes) {
        this.dimensionCostos = dimensionCostos;
        this.cantidadSoluciones = cantidadSoluciones;
        this.costos = costos;
        this.selectorPadres = selectorPadres;
        this.operadorCruce = operadorCruce;
        this.mutador = mutador;
        this.seleccionadorSobrevivientes = seleccionadorSobrevivientes;
        this.mejoresFitness=new ArrayList<>();
        this.peorFitness=new ArrayList<>();

    }
    // --------------------------Métodos-------------------------------

    public Poblacion getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(Poblacion poblacion) {
        this.poblacion = poblacion;
    }

    public int[][] getCamino() {
        return costos;
    }

    public int getDimensionCostos() {
        return dimensionCostos;
    }

    public int getcantidadSoluciones() {
        return cantidadSoluciones;
    }

    public SeleccionadorPadres getSelectorPadres() {
        return selectorPadres;
    }

    public OperadorCruce getOperadorCruce() {
        return operadorCruce;
    }

    public Mutador getMutador() {
        return mutador;
    }

    public SeleccionadorSobrevivientes getSeleccionadorSobrevivientes() {
        return seleccionadorSobrevivientes;
    }
    public ArrayList<Double> getMejoresFitness(){return mejoresFitness;}

    public ArrayList<Double> getPeorFitness(){return peorFitness;}

    public List<Solucion> getHijos(List<Solucion> padres){
        return operadorCruce.generarHijos(padres, cantidadSoluciones, costos);
    }

    public void inicializar() {
        this.poblacion = new Poblacion(cantidadSoluciones);
        this.poblacion.llenarRandom(costos);
    }

    public List<Solucion> seleccionarPadres(){
        return selectorPadres.seleccionar(poblacion, cantidadSoluciones);
    }

    public void mutarSolucion(Solucion hijo) {
        mutador.mutar(hijo, costos);
    }

    public List<Solucion> seleccionarSobrevivientes(List<Solucion> padres, List<Solucion> hijos){
        return seleccionadorSobrevivientes.seleccionar(padres, hijos);
    }
    public void setearPoblacion(List<Solucion> sobrevivientes){
        poblacion.setSoluciones(sobrevivientes);
        poblacion.ordenarPorFitnessMenorAMayor();
        mejoresFitness.add(poblacion.get(0).getFitness());
        peorFitness.add(poblacion.get(poblacion.getTamañoActual()-1).getFitness());
    }

    public void ordenarPorFitnessMenorAMayor() {
        poblacion.ordenarPorFitnessInvertidoMenorAMayor();
    }

    public void ordenarPorFitnessMayorAMenor() {
        poblacion.ordenarPorFitnessMayorAMenor();
    }

    public void ordenarPorFitnessInvertidoMayorAMenor() {
        poblacion.ordenarPorFitnessInvertidoMayorAMenor();
    }

    public void ordenarPorFitnessInvertidoMenorAMayor() {
        poblacion.ordenarPorFitnessInvertidoMenorAMayor();
    }
}

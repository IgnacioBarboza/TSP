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

    // --------------------------Constructor-------------------------------
    public Instancia(int dimensionCostos, int[][] costos, int cantidadSoluciones, SeleccionadorPadres selectorPadres, OperadorCruce operadorCruce, Mutador mutador) {
        this.dimensionCostos = dimensionCostos;
        this.cantidadSoluciones = cantidadSoluciones;
        this.costos = costos;
        this.selectorPadres = selectorPadres;
        this.operadorCruce = operadorCruce;
        this.mutador = mutador;
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

    public List<Solucion> getHijos(List<Solucion> padres){
        return operadorCruce.generarHijos(padres, cantidadSoluciones, costos);
    }

    private void inicializar() {
        this.poblacion = new Poblacion(cantidadSoluciones);
        this.poblacion.llenarRandom(costos);
    }

    public static void main(String[] args) {
        // Creamos una instancia de prueba para poder llamar al método no estático
        int[][] costosPrueba = {
                { 0, 42, 61, 30, 17, 82, 31, 11, 56, 19 },
                { 42, 0, 14, 87, 28, 70, 19, 33, 65, 12 },
                { 61, 14, 0, 20, 81, 21, 8, 29, 88, 53 },
                { 30, 87, 20, 0, 34, 33, 91, 10, 59, 25 },
                { 17, 28, 81, 34, 0, 41, 62, 78, 44, 66 },
                { 82, 70, 21, 33, 41, 0, 55, 24, 15, 48 },
                { 31, 19, 8, 91, 62, 55, 0, 69, 22, 90 },
                { 11, 33, 29, 10, 78, 24, 69, 0, 85, 39 },
                { 56, 65, 88, 59, 44, 15, 22, 85, 0, 71 },
                { 19, 12, 53, 25, 66, 48, 90, 39, 71, 0 }
        };
        int tamanio = 10; // Tamaño de la población
        int tamañotorneo = 20; // Cuantas veces comparo el ganador del torneo? Mayor = más selectivo y Menor =
                               // más aleatorio
        int cantidadSoluciones = 100; // Cantidad de soluciones en la población
        int cantidadPadres = 50; // Cantidad de padres que queremos seleccionar
        Instancia instancia = new Instancia(tamanio, costosPrueba, cantidadSoluciones,
                new SeleccionTorneo(tamañotorneo),new CruceDeOrden(),new MutacionPorInversion());

        instancia.inicializar();
        int iterador = 0;
        while (iterador < 100000){
            iterador++;
            System.out.println(iterador);
            List<Solucion> padres = instancia.selectorPadres.seleccionar(instancia.poblacion, cantidadPadres);
            List<Solucion> hijos = instancia.getHijos(padres);
            for (Solucion hijo : hijos) {
                instancia.mutador.mutar(hijo, costosPrueba);
            }
            instancia.poblacion.setSoluciones(hijos);
            instancia.poblacion.ordenarPorFitnessMenorAMayor();
        }
        for(int i=0; i<5;i++){
            instancia.poblacion.get(i).imprimirCamino();
            System.out.println("Fitness : " + instancia.poblacion.get(i).getFitness());;
        }

    }

}

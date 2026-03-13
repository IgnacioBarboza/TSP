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


    // --------------------------Constructor-------------------------------
    public Instancia(int dimensionCostos, int[][] costos, int cantidadSoluciones, SeleccionadorPadres selectorPadres, OperadorCruce operadorCruce, Mutador mutador, SeleccionadorSobrevivientes seleccionadorSobrevivientes) {
        this.dimensionCostos = dimensionCostos;
        this.cantidadSoluciones = cantidadSoluciones;
        this.costos = costos;
        this.selectorPadres = selectorPadres;
        this.operadorCruce = operadorCruce;
        this.mutador = mutador;
        this.seleccionadorSobrevivientes = seleccionadorSobrevivientes;

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
        int[][] costosPruebaAsimetrica = {
                //   0   1   2   3   4   5   6   7   8   9
                {  0, 42, 61, 30, 17, 82, 31, 11, 56, 19 }, // Ciudad 0
                { 48,  0, 14, 87, 28, 70, 19, 33, 65, 12 }, // Ciudad 1
                { 55, 18,  0, 20, 81, 21,  8, 29, 88, 53 }, // Ciudad 2
                { 36, 92, 25,  0, 34, 33, 91, 10, 59, 25 }, // Ciudad 3
                { 21, 35, 76, 40,  0, 41, 62, 78, 44, 66 }, // Ciudad 4
                { 75, 66, 27, 39, 45,  0, 55, 24, 15, 48 }, // Ciudad 5
                { 28, 24, 12, 85, 59, 61,  0, 69, 22, 90 }, // Ciudad 6
                { 15, 30, 31, 14, 83, 29, 74,  0, 85, 39 }, // Ciudad 7
                { 60, 60, 95, 62, 40, 19, 26, 81,  0, 71 }, // Ciudad 8
                { 22, 17, 49, 29, 70, 53, 85, 43, 75,  0 }  // Ciudad 9
        };
        int tamanio = 10; // Tamaño de la población
        int tamañotorneo = 6; // Cuantas veces comparo el ganador del torneo? Mayor = más selectivo y Menor =
                               // más aleatorio
        int cantidadSoluciones = 100; // Cantidad de soluciones en la población
        int cantidadPadres = 50; // Cantidad de padres que queremos seleccionar
        Instancia instancia = new Instancia(tamanio, costosPruebaAsimetrica, cantidadSoluciones,
                new SeleccionTorneo(tamañotorneo),new CruceDeOrden(),new MutacionPorInversion(), new SeleccionHijosPorPadres());

        instancia.inicializar();

        
        instancia.poblacion.ordenarPorFitnessMenorAMayor();
        for(int i=0; i<5;i++){
            instancia.poblacion.get(i).imprimirCamino();
            System.out.println("Fitness : " + instancia.poblacion.get(i).getFitness());;
        }
        int iterador = 0;
        System.out.println("Inicio iteraciones: ");
        while (iterador < 100){
            iterador++;
            List<Solucion> padres = instancia.selectorPadres.seleccionar(instancia.poblacion, cantidadPadres);
            List<Solucion> hijos = instancia.getHijos(padres);
            for (Solucion hijo : hijos) {
                instancia.mutador.mutar(hijo, costosPruebaAsimetrica);
            }
            instancia.poblacion.setSoluciones(instancia.seleccionadorSobrevivientes.seleccionar(padres, hijos));
            instancia.poblacion.ordenarPorFitnessMenorAMayor();
            
            System.out.println("Iteración número : " + iterador);
            for(int i=0; i<5;i++){
                instancia.poblacion.get(i).imprimirCamino();
                System.out.println("Fitness : " + instancia.poblacion.get(i).getFitness());;
            }
        }

    }

}

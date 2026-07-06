import java.util.List;
import java.util.Scanner;

public class Main {
    
    private static SeleccionadorPadres elegirSelectorPadres(Scanner scanner) {
        System.out.println("1 : Selección por Torneo");
        System.out.println("2 : Selección por Ruleta");
        System.out.print("▶ Ingrese su opción (1 o 2): ");
        
        int opcion = scanner.nextInt();

        if (opcion == 1) {
            System.out.println("Indique el tamaño del torneo: ");
            int tamanoTorneo = scanner.nextInt();
            return new SeleccionTorneo(tamanoTorneo);
        } else {
            return new SeleccionRuleta();
        }
    }

    private static SeleccionadorSobrevivientes elegirSelectorSobrevivientes(Scanner scanner) {
        System.out.println("\nElija el método de Selección de Sobrevivientes:");
        System.out.println("1 : Selección por Reemplazo");
        System.out.println("2 : Steady State");
        System.out.print("▶ Ingrese su opción (1 o 2): ");
        
        int opcion = scanner.nextInt();

        if (opcion == 1) {
            return new SeleccionHijosPorPadres();
        } else if (opcion == 2){
            System.out.println("Indique la cantidad de soluciones de reemplazo: ");
            int n = scanner.nextInt();
            return new SeleccionadorSteadyState(n);
        } else {
            return new SeleccionSobrevivientesRuleta();
        }
    }

    private static Mutador elegirMutador(Scanner scanner) {
        System.out.println("\nElija el método de Mutación:");
        System.out.println("1 : por Inversión");
        System.out.println("2 : por Intercambio");
        System.out.print("▶ Ingrese su opción (1): ");
        int opcion = scanner.nextInt();

        System.out.println("\nElija las probabilidades de mutación:");
        double prob = scanner.nextDouble();

        if (opcion == 1) {
            return new MutacionPorInversion(prob);
        } else {
            System.out.print("Elija la cantidad de intercambios por mutación");
            int cantIntercambios=scanner.nextInt();
            return new MutacionPorIntercambio(cantIntercambios,prob);
        }

    }

    private static OperadorCruce elegirOperadorCruce(Scanner scanner) {
        System.out.println("\nElija el operador de cruce:");
        System.out.println("1 : Cruce de Orden");
        System.out.println("2 : Cruce de Arcos");
        System.out.print("▶ Ingrese su opción (1 o 2): ");
        
        int opcion = scanner.nextInt();

        if (opcion == 1) {
            return new CruceDeOrden();
        } 
        return new CruceDeArcos();
    }

    private static Instancia CreacionInstancia(Scanner scanner) {
        System.out.println("=========================================================");
        System.out.println(" 🧬 BIENVENIDO AL ALGORITMO EVOLUTIVO - EL VIAJANTE 🧬 ");
        System.out.println("=========================================================\n");
        
        System.out.println("Por favor, configuremos los parámetros iniciales:\n");
        System.out.println("Elija un archivo con las distancias entre las ciudades");
        System.out.print("Presione enter para continuar.");
        scanner.nextLine();

        System.out.print("...");

        int[][] costos=ControladorArchivos.inicializarMatriz();
        int tamanio=costos.length;

        // 1. Tamaño del problema (ciudades)
        System.out.println("▶ 1. Cantidad total de ciudades del problema: " + tamanio);

        // 2. Tamaño de la población
        System.out.print("▶ 2. Ingrese el tamaño de la población (ej. 100): ");
        int cantidadSoluciones = scanner.nextInt();
        
        // 3. Tamaño de la población
        System.out.print("▶ 3. Ingrese la cantidad de iteraciones del experimento (ej. 500 o 1000): ");
        int cantidadIteraciones = scanner.nextInt();
        
        // 4. Selección de padres
        SeleccionadorPadres selectorPadres = elegirSelectorPadres(scanner);

        // 5. Selección de sobrevivientes
        SeleccionadorSobrevivientes seleccionadorSobrevivientes = elegirSelectorSobrevivientes(scanner);

        // 6. Selección de mutación
        Mutador mutador = elegirMutador(scanner);

        // 7. Selección de operador de cruce
        OperadorCruce operadorCruce = elegirOperadorCruce(scanner);

        System.out.println("\n=========================================================");
        System.out.println("   ¡Configuración completada exitosamente!");
        System.out.println("   - Ciudades: " + tamanio);
        System.out.println("   - Población: " + cantidadSoluciones + " individuos");
        System.out.println("   - Cantidad de Iteraciones: " + cantidadIteraciones);
        System.out.println("   - Selector de Padres: " + selectorPadres.getNombre());
        System.out.println("   - Selector de Sobrevivientes: " + seleccionadorSobrevivientes.getNombre());
        System.out.println("   - Mutador: " + mutador.getNombre());
        System.out.println("   - Operador de Cruce: " + operadorCruce.getNombre());
        System.out.println("=========================================================\n");
        
        System.out.println("Iniciando la evolución... \n");

        Instancia instancia = new Instancia(tamanio, costos, cantidadSoluciones,
                selectorPadres,operadorCruce,mutador, seleccionadorSobrevivientes,cantidadIteraciones);
        return instancia;
    }

    public static void FinalizacionEvolucion(long tiempoTotalInicio, int iterador, Instancia instancia){
        long tiempoTotalFin = System.currentTimeMillis();
        long tiempoTotal = tiempoTotalFin - tiempoTotalInicio; 
        double tiempoPromedioPorGen = (double) tiempoTotal / iterador;

        instancia.getPoblacion().ordenarPorFitnessMenorAMayor();
        Solucion mejorSolucionFinal = instancia.getPoblacion().get(0);
        double mejorCosto = mejorSolucionFinal.getFitness();

        double sumaCostos = 0;
        for (Solucion s : instancia.getPoblacion().getSoluciones()) {
            sumaCostos += s.getFitness();
        }
        double costoPromedio = sumaCostos / instancia.getPoblacion().getTamañoActual();

        System.out.println("\n=========================================================");
        System.out.println(" 🏆 EVOLUCIÓN FINALIZADA - RESULTADOS OBTENIDOS 🏆");
        System.out.println("=========================================================");
        System.out.println(" ⚙️  CONFIGURACIÓN UTILIZADA:");
        System.out.println("     Selección: " + instancia.getSelectorPadres().getNombre());
        System.out.println("     Cruce:     " + instancia.getOperadorCruce().getNombre());
        System.out.println("     Mutación:  " + instancia.getMutador().getNombre());
        System.out.println("     Reemplazo: " + instancia.getSeleccionadorSobrevivientes().getNombre());
        System.out.println("---------------------------------------------------------");
        System.out.println(" 🥇 Mejor Costo (Distancia):    " + mejorCosto);
        System.out.println(" 📊 Costo Promedio (Población): " + String.format("%.2f", costoPromedio));
        System.out.println(" ⏱️  Tiempo Total de Ejecución: " + tiempoTotal + " ms");
        System.out.println(" ⏱️  Tiempo Promedio x Gen:     " + String.format("%.2f", tiempoPromedioPorGen) + " ms");
        System.out.println("=========================================================\n");
        
        System.out.print("▶ Ruta del mejor viaje encontrado: ");
        mejorSolucionFinal.imprimirCaminoLn();
        ControladorArchivos.guardarResultados(instancia,costoPromedio,tiempoTotal,tiempoPromedioPorGen);
        for (int i = 0; i < instancia.getcantidadSoluciones(); i++) {
            Solucion s=instancia.getPoblacion().getSoluciones().get(i);
            s.imprimirCamino();
            System.out.println(" Fitness: "+s.getFitness());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Instancia instancia = CreacionInstancia(scanner);

        instancia.inicializar();
        // CRONOMETRO
        long tiempoTotalInicio = System.currentTimeMillis();

        int iterador = 0;
        System.out.println("Inicio iteraciones: ");


        while (iterador < instancia.getcantidadIteraciones()){
            iterador++;
            List<Solucion> padres = instancia.seleccionarPadres();
            List<Solucion> hijos = instancia.getHijos(padres);
            for (Solucion hijo : hijos) {
                    instancia.mutarSolucion(hijo);
            }
            List<Solucion> sobrevivientes = instancia.seleccionarSobrevivientes(padres, hijos);
            instancia.setearPoblacion(sobrevivientes);
            instancia.ordenarPorFitnessMenorAMayor();
        }

        scanner.close();
        
        FinalizacionEvolucion(tiempoTotalInicio, iterador, instancia);
        
    }
}

import java.util.Random;

public class MutacionPorInversion implements Mutador{

    private Random random;

    public MutacionPorInversion(){
        random = new Random();
    }

    @Override
    public void mutar(Solucion origen,int[][] costos){
        int tamanio = origen.getCamino().size();
        int inicioMutacion = random.nextInt(tamanio);
        int finMutacion= random.nextInt(tamanio);

        if (inicioMutacion > finMutacion){
            int temp = inicioMutacion;
            inicioMutacion = finMutacion;
            finMutacion = temp;
        }

        while (inicioMutacion < finMutacion) {
            // Intercambiamos los extremos
            origen.swapCaminos(inicioMutacion, finMutacion);
            
            // Acercamos los punteros hacia el centro
            inicioMutacion++;
            finMutacion--;
        }

        origen.recalcularFitness(costos);
    }
}
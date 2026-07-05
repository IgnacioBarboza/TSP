import java.util.Random;

public class MutacionPorInversion extends Mutador{

    private Random random;

    public MutacionPorInversion(double probabilidad){
        super(probabilidad);
        random = new Random();
    }
    @Override
    public String getNombre() {
        return "Mutación por Inversión. Probabilidad: "+probabilidad;
    }

    @Override
    public void mutar(Solucion origen,int[][] costos){
        if(Math.random()<=probabilidad){
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
}
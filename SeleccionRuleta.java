import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeleccionRuleta implements SeleccionadorPadres {
    
    private Random random;

    public SeleccionRuleta() {
        this.random = new Random();
    }

    @Override
    public List<Solucion> seleccionar(Poblacion poblacion, int cantidadPadres) {

        List<Solucion> padresSeleccionados = new ArrayList<>();

        double sumaTotalInvertida = 0.0;
        for(Solucion s : poblacion.getSoluciones()){
            sumaTotalInvertida+=s.getFitnessInvertido();
        }

        // Seleccionamos la cantidad de padres requerida
        for (int i = 0; i < cantidadPadres; i++) {
            
            double tiroRuleta = random.nextDouble() * sumaTotalInvertida; 
            double acumulador = 0.0;

            for(Solucion s: poblacion.getSoluciones()){
                acumulador += s.getFitnessInvertido();
                if(acumulador >= tiroRuleta){
                    padresSeleccionados.add(s);
                    break;
                }
            }
        }

        return padresSeleccionados;
    }
}
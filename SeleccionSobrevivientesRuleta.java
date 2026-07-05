import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeleccionSobrevivientesRuleta implements SeleccionadorSobrevivientes {

    private Random random;


    public SeleccionSobrevivientesRuleta() {
        this.random = new Random();
    }

    @Override
    public String getNombre() {
        return "Por Ruleta";
    }


    @Override
    public List<Solucion> seleccionar(List<Solucion> padres, List<Solucion> hijos) {
        List<Solucion> seleccionados = new ArrayList<>();
        List<Solucion> soluciones = new ArrayList<>();
        for(Solucion s: padres){
            soluciones.add(s);
        }
        for(Solucion s: hijos){
            soluciones.add(s);
        }


        double sumaTotalInvertida = 0.0;
        for(Solucion s : soluciones){
            sumaTotalInvertida+=s.getFitnessInvertido();
        }


        for (int i = 0; i < padres.size(); i++) {

            double tiroRuleta = random.nextDouble() * sumaTotalInvertida;
            double acumulador = 0.0;

            for(Solucion s: soluciones){
                acumulador += s.getFitnessInvertido();
                if(acumulador >= tiroRuleta){
                    seleccionados.add(s);
                    break;
                }
            }
        }

        return seleccionados;
    }
}
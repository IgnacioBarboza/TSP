public class MutacionPorIntercambio extends Mutador{
    private final int cantIntercambios;
    MutacionPorIntercambio(int cantIntercambios,double probabilidad) {
        super(probabilidad);
        this.cantIntercambios=cantIntercambios;
    }

    @Override
    public String getNombre() {
        return "Mutación por intercambio ("+cantIntercambios+"). Probabilidad: "+probabilidad;
    }

    @Override
    void mutar(Solucion solucion, int[][] costos) {
        int tamanio = solucion.getCamino().size();
        for (int i = 0; i < cantIntercambios; i++) {
            int pos1=(int)(Math.random()*(tamanio));
            int pos2=(int)(Math.random()*(tamanio));
            solucion.swapCaminos(pos1,pos2);
        }
        solucion.recalcularFitness(costos);
    }
}

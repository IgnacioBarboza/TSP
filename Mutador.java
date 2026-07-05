public abstract class Mutador implements Elemento{
    protected double probabilidad;
    Mutador(double probabilidad){
        if((0<probabilidad)&&(probabilidad<1)){
            this.probabilidad=probabilidad;
        } else {
            this.probabilidad=0.1D;
        }
    }
    abstract void mutar(Solucion original, int[][] costos);
}

import java.util.List;

public interface SeleccionadorPadres {
    /**
     * Selecciona una cantidad específica de padres de la población actual.
     * * @param poblacion La lista de soluciones actuales.
     * @param cantidadPadres Cuántos padres necesitamos seleccionar.
     * @return Una lista con las soluciones seleccionadas para reproducirse.
     */
    List<Solucion> seleccionar(Poblacion poblacion, int cantidadPadres);
}
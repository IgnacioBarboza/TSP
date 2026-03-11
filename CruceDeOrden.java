import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class CruceDeOrden implements OperadorCruce {
    
    private Random random;

    public CruceDeOrden() {
        // ¡Crucial! Inicializar el objeto Random
        this.random = new Random();
    }

    @Override
    public List<Solucion> generarHijos(List<Solucion> padres, int cantidadHijos, int[][] costos) {
        List<Solucion> hijos = new ArrayList<>();
        
        for (int i = 0; i < cantidadHijos / 2; i++) {
            Solucion padre1 = padres.get(random.nextInt(padres.size()));
            Solucion padre2 = padres.get(random.nextInt(padres.size()));
            List<Solucion> parDeHijos = cruzar(padre1, padre2, costos);
            hijos.addAll(parDeHijos);
        }

        // Si la cantidad de hijos pedida era impar, nos falta generar 1 hijo más.
        // Hacemos un cruce extra y tomamos solo el primer hijo.
        if (cantidadHijos % 2 != 0) {
            Solucion padre1 = padres.get(random.nextInt(padres.size()));
            Solucion padre2 = padres.get(random.nextInt(padres.size()));
            hijos.add(cruzar(padre1, padre2, costos).get(0));
        }

        return hijos;
    }
    @Override
    public List<Solucion> cruzar(Solucion p1, Solucion p2, int[][] costos) {
        int tamanio = p1.getCamino().size();
        
        int inicioCruce = random.nextInt(tamanio);
        int finCruce = random.nextInt(tamanio);
        if (inicioCruce > finCruce){
            int temp = inicioCruce;
            inicioCruce = finCruce;
            finCruce = temp;
        }

        List<Integer> rutaHijo1 = new ArrayList<>();
        List<Integer> rutaHijo2 = new ArrayList<>();
        
        for (int i = 0; i < tamanio; i++) {
            rutaHijo1.add(-1);
            rutaHijo2.add(-1);
        }

        // 3. Agregamos el patron comun de los padres (la franja central)
        for(int i = inicioCruce; i <= finCruce; i++){
            rutaHijo1.set(i, p1.getCamino().get(i));
            rutaHijo2.set(i, p2.getCamino().get(i));
        }

        // 4. Rellenar los huecos (Lógica corregida de OX)
        // Empezamos a leer y escribir justo después de la franja copiada
        int indiceLectura = (finCruce + 1) % tamanio;
        int indiceEscrituraH1 = (finCruce + 1) % tamanio;
        int indiceEscrituraH2 = (finCruce + 1) % tamanio;

        // Iteramos tantas veces como ciudades haya para dar toda la vuelta
        for(int i = 0; i < tamanio; i++) {
            int indiceActual = (indiceLectura + i) % tamanio;
            
            int ciudadP2 = p2.getCamino().get(indiceActual);
            int ciudadP1 = p1.getCamino().get(indiceActual);

            if (!rutaHijo1.contains(ciudadP2)){
                rutaHijo1.set(indiceEscrituraH1 % tamanio, ciudadP2);
                indiceEscrituraH1++; 
            }
            
            if (!rutaHijo2.contains(ciudadP1)){
                rutaHijo2.set(indiceEscrituraH2 % tamanio, ciudadP1);
                indiceEscrituraH2++; 
            }
        }
        
        Solucion s1 = new Solucion(rutaHijo1, costos);
        Solucion s2 = new Solucion(rutaHijo2, costos);

        List<Solucion> hijosGenerados = new ArrayList<>();
        hijosGenerados.add(s1);
        hijosGenerados.add(s2);

        return hijosGenerados; 
    }
}
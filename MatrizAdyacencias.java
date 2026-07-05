import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatrizAdyacencias {
    private int[][] mat;

    private int dimension;
    public MatrizAdyacencias(List<String> valores) throws Exception {
        boolean encontroDimension=false;
        boolean encontroInicio=false;
        for (int i = 0; i < valores.size() ; i++) {
            if(valores.get(i).toLowerCase().contains("dimension")){
                dimension=getDimension(valores.get(i));
                encontroDimension=true;
            }
        }
        if (!encontroDimension){ throw new Exception("No se encontró la dimension.");}
        mat=new int[dimension][dimension];
        int inicioMatriz = 0;
        for (int i = 0; i < valores.size() ; i++) {
            if(valores.get(i).toUpperCase().contains("EDGE_WEIGHT_SECTION")){
                inicioMatriz=i+1;
                encontroInicio=true;
                break;
            }
        }
        if(!encontroInicio){ throw new Exception("No se encontró el inicio de la matriz de costos.");}
        for (int i = 0; i < dimension; i++) {
            agregarFila(mat,valores.get(inicioMatriz+i),i);
        }
    }

    private void agregarFila(int[][] mat,String texto,int linea){
        String[] palabras = texto.splitWithDelimiters(" +",-1);
        List<String> palabrasLista= new ArrayList<>(Arrays.stream(palabras).toList());
        int sizeOriginal =palabrasLista.size();
        List<Integer> aEliminar= new ArrayList<Integer>(sizeOriginal);
        for (int i = 0; i < sizeOriginal; i++) {
            if((palabrasLista.get(i).isEmpty())||(palabrasLista.get(i).contains(" "))){
                aEliminar.add(i);
            }
        }
        for(int i=sizeOriginal-1;i>=0;i--){
            if (aEliminar.contains(i))
                palabrasLista.remove(i);
        }
        for (int i = 0; i < dimension; i++) {
            mat[linea][i]=Integer.parseInt(palabrasLista.get(i));
        }
    }

    public int getDimension(String texto) throws Exception {
        String[] palabras = texto.splitWithDelimiters(" +",-1);
        int dim=-1;
        for (String p:palabras) {
            try {
                dim=Integer.parseInt(p);
                break;
            } catch (NumberFormatException _){}
        }
        if (dim!=-1)
            return dim;
        else
            throw new Exception("Error de lectura de la dimensión.");
    }

    public int getCosto(int nroCiudad1,int nroCiudad2){
        return mat[nroCiudad1][nroCiudad2];
    }

    public int getDimension() {
        return dimension;
    }

    public int[][] getMat() {
        return mat;
    }
}

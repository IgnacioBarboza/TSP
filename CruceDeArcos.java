import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class CruceDeArcos implements OperadorCruce{


    public CruceDeArcos(){}
    public List<Solucion> generarHijos(List<Solucion> padres, int cantidadHijos, int[][] costos) {
        List<Solucion> descendencia=new Vector<>(cantidadHijos);
        int largoCamino=padres.getFirst().getCamino().size();
        int j=0;
        int k=cantidadHijos-1;
        int indexPadre=-2;
        for (int i = 0; i < cantidadHijos; i=i+1) {//Genera un hijo en cada loop
            Solucion primer;
            Solucion segundo;
            indexPadre+=2;
            if (i<(cantidadHijos/2)){
                primer=padres.get(indexPadre);
                segundo=padres.get(indexPadre+1);
            } else {
                primer=padres.get(j);
                segundo=padres.get(k);
                j++;
                k--;
            }
            TablaArcos tablaArcos=new TablaArcos(primer,segundo);
            int[] camino=new int[largoCamino];

            int elemento=(int)(Math.random()*largoCamino);
            camino[0]=elemento;
            tablaArcos.eliminarElemento(elemento);
            int cantidadInserciones=1;
            int pivotAsc=1;
            int pivotDesc= largoCamino-1;
            boolean creciente=true;
            boolean cambioDireccion=false; //Controla que no haya dos cambios de dirección consecutivos, para que no se quede iterando indefinidamente
            while (cantidadInserciones < largoCamino) { // Agrega hasta que el hijo esté completo
                int siguiente=tablaArcos.obtenerSiguiente(elemento);
                if (siguiente!=-1){
                        if (creciente){
                            camino[pivotAsc]=siguiente;
                            pivotAsc+=1;
                        }
                        else {
                            camino[pivotDesc]=siguiente;
                            pivotDesc-=1;
                        }
                        tablaArcos.eliminarElemento(siguiente);
                        cantidadInserciones+=1;
                        elemento=siguiente;
                        cambioDireccion=false;
                    } else {
                        if (cambioDireccion){
                            elemento= tablaArcos.random();
                            tablaArcos.eliminarElemento(elemento);
                            cantidadInserciones++;
                            if (creciente){
                                camino[pivotAsc]=elemento;
                                pivotAsc+=1;
                            } else {
                                camino[pivotDesc]=elemento;
                                pivotDesc-=1;
                            }
                            cambioDireccion=false;
                        } else {
                            cambioDireccion=true;
                            if (creciente){
                                if (pivotDesc==largoCamino-1)
                                    elemento=camino[0];
                                else
                                    elemento=camino[pivotDesc];
                            } else {
                                elemento=camino[pivotAsc];
                            }
                            creciente=!creciente;
                        }
                    }
                }
            List<Integer> caminoList= new ArrayList<>(largoCamino);
            for (int l = 0; l < largoCamino; l++) {
                caminoList.add(camino[l]);
            }
            Solucion hijo=new Solucion(caminoList,costos);
            descendencia.add(hijo);
        }
        return descendencia;
    }




    @Override
    public String getNombre() {
        return "Cruce de arcos";
    }


    private class TablaArcos{
        private int[][] lista;
        private int dimension;

        private TablaArcos(Solucion ind1,Solucion ind2){
            dimension=ind1.getCamino().size();
            lista=new int[dimension][5];
            for (int i = 0; i < dimension; i++) {
                addElemento(ind1,ind2,i);
            }
        }

        private int obtenerSiguiente(int elemento){
            //Se comprueba si hay lazos en común en ambos individuos
            if(((lista[elemento][0]==lista[elemento][3])||lista[elemento][0]==lista[elemento][2])&&(lista[elemento][0]!=-1))
                return lista[elemento][0];
            if(((lista[elemento][1]==lista[elemento][3])||lista[elemento][1]==lista[elemento][2])&&(lista[elemento][1]!=-1))
                return lista[elemento][1];
            int tamanioMin=5;
            boolean encontro=false;
            ArrayList<Integer> candidatos=new ArrayList<Integer>(4);

            for (int i = 0; i < 4; i++) {
                if (lista[elemento][i]!=-1){
                    int tamanio=tamanioLista(lista[elemento][i]);
                    encontro=true;
                    if(tamanio==tamanioMin){
                        candidatos.add(lista[elemento][i]);
                    }
                    if (tamanio<tamanioMin){
                        candidatos.clear();
                        candidatos.add(lista[elemento][i]);
                        tamanioMin=tamanio;
                    }
                }
            }
            int length=candidatos.size();
            int random=(int) (Math.random()*length);
            if (encontro)
                return candidatos.get(random);
            else
                return -1;

        }

        private int random(){
            List<Integer> valoresExistentes= new ArrayList<Integer>(dimension);
            for (int i = 0; i < dimension; i++) {
                if (lista[i][4]!=-1)
                    for (int j = 0; j < 4; j++) {
                        if (!valoresExistentes.contains(lista[i][j])&&lista[i][j]!=-1)
                            valoresExistentes.add(lista[i][j]);
                    }
            }
            int length =valoresExistentes.size();
            return valoresExistentes.get((int) (Math.random()*length));
        }

        private int tamanioLista(int elemento){
            return lista[elemento][4];
        }

        private void addElemento(Solucion ind1,Solucion ind2,int i){
            List<Integer> camino1=ind1.getCamino();
            List<Integer> camino2=ind2.getCamino();
            int elemento=camino1.get(i);
            int ady1=ind1.getCiudadAnterior(i);
            int ady2=ind1.getCiudadPosterior(i);
            int indice=ind2.getIndice(elemento);
            int ady3=ind2.getCiudadAnterior(indice);
            int ady4=ind2.getCiudadPosterior(indice);
            int cant=4;
            if((ady1==ady3)||(ady1==ady4))
                cant-=1;
            if((ady2==ady3)||(ady2==ady4))
                cant-=1;
            lista[elemento][0]=ady1;
            lista[elemento][1]=ady2;
            lista[elemento][2]=ady3;
            lista[elemento][3]=ady4;
            lista[elemento][4]=cant;
        }

        private void eliminarElemento(int elemento){
            for (int i = 0; i < dimension ; i++) {
                boolean elimino=false;
                for (int j = 0; j < 4; j++) {
                    if(lista[i][j]==elemento){
                        lista[i][j]=-1;
                        elimino=true;
                    }
                }
                if (elimino)
                    lista[i][4]-=1;
            }
        }
    }
    private class Elemento{
        public Elemento(int ciudad){
            this.ciudad=ciudad;
        }
        int ciudad;
        boolean doble;
        Elemento siguiente;


    }
}

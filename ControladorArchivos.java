import com.formdev.flatlaf.*;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ControladorArchivos {
    private static String nombreArchivo = "";

    public static String getNombreArchivo() {
        return nombreArchivo;
    }
    public static int[][] inicializarMatriz()  {
        FlatLightLaf.setup();
        Path path=elegirArchivo();
        try (BufferedReader reader = Files.newBufferedReader(path);
            Stream<String> lines = reader.lines();) {
            List<String> renglones = lines.toList();
            return crearMatrizAdyacencias(renglones);
        }catch (Exception ee){
            System.out.print("El archivo elegido no es de texto\n\n");
            throw new RuntimeException(ee);
        }
    }

    private static Path elegirArchivo() {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        JFileChooser chooser = new JFileChooser();
        int res = chooser.showOpenDialog(frame);
        File archivo = null;
        if (res == JFileChooser.APPROVE_OPTION) {
            archivo = chooser.getSelectedFile();
            nombreArchivo = archivo.getName();
            return archivo.toPath();
        } else throw new RuntimeException();
    }

    public static void guardarResultados(Instancia instancia,double costoPromedio, double tiempoTotal, double tiempoPromedioPorGen){
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("Problema: " + nombreArchivo + System.lineSeparator());
            writer.write("Configuración utilizada:"+System.lineSeparator());
            writer.write("Operador de selección: "+instancia.getSelectorPadres().getNombre()+System.lineSeparator());
            writer.write("Operador de cruce: "+ instancia.getOperadorCruce().getNombre()+System.lineSeparator());
            writer.write("Operador de mutación: " + instancia.getMutador().getNombre()+System.lineSeparator());
            writer.write("Selector de sobrevivientes: " + instancia.getSeleccionadorSobrevivientes().getNombre()+System.lineSeparator());
            writer.write("Fitness mejor solución:    " + instancia.getPoblacion().getSoluciones().get(0).getFitness()+System.lineSeparator());
            writer.write("Costo promedio (Población): " + String.format("%.2f", costoPromedio)+System.lineSeparator());
            writer.write("Tiempo total de ejecución: " + tiempoTotal + " ms"+System.lineSeparator());
            writer.write("Tiempo promedio por generación: " + String.format("%.2f", tiempoPromedioPorGen) + " ms"+System.lineSeparator());
            writer.write("\nEvolución del Fitness en el tiempo");
            int totalIteraciones=instancia.getMejoresFitness().size();
            for (int i = 0; i < totalIteraciones; i++) {
                int sig=i+1;
                double fitness=instancia.getMejoresFitness().get(i);
                double diferencia=fitness-instancia.getPeorFitness().get(i);
                writer.write("Mejor fitness en iteración "+sig+": "+fitness+" Diferencia: "+diferencia+"\n");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("Resultados guardados en: " + nombreArchivo);
    }

    public static int[][] crearMatrizAdyacencias(List<String> valores) throws Exception {
        boolean encontroDimension=false;
        boolean encontroInicio=false;
        int dimension=-1;
        for (int i = 0; i < valores.size() ; i++) {
            if(valores.get(i).toLowerCase().contains("dimension")){
                dimension=getDimension(valores.get(i));
                encontroDimension=true;
            }
        }
        if (!encontroDimension){ throw new Exception("No se encontró la dimension.");}
        int[][] mat = new int[dimension][dimension];
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
        return mat;
    }

    private static void agregarFila(int[][] mat,String texto,int linea){
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
        for (int i = 0; i < mat.length; i++) {
            mat[linea][i]=Integer.parseInt(palabrasLista.get(i));
        }
    }

    public static int getDimension(String texto) throws Exception {
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

}

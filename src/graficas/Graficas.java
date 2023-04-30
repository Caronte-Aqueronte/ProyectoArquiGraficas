package graficas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.math.plot.Plot2DPanel;

/**
 *
 * @author Luis Monterroso
 */
public class Graficas {

    //Datos a tratar en la grafica
    private double[] x = {0.0, 0.0};
    private double[] y = {0.0, 0.0};

    //Datos a tratar en la grafica
    private ArrayList<Double> ejeX;
    private ArrayList<Double> ejeY;

    //Objetos de la vista
    private JTextField txtCSV;
    private JButton btnGraficar;
    private JButton btnBorrar;
    private ArrayList list;

    //Objeto que permite graficar
    private Plot2DPanel plot = new Plot2DPanel();
    
    public Graficas() {
        this.ejeX = new ArrayList<>();
        this.ejeY = new ArrayList<>();

        //instancia a los elementos graficos
        txtCSV = new JTextField(10);
        
        btnGraficar = new JButton("Graficar");
        btnBorrar = new JButton("Borrar");

        //Generar Ventana
        JFrame frame = new JFrame("Grafica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.add(construirPanelPrincipal());
        frame.setVisible(true);

        //Agregar datos a la Grafica      
        plot.addScatterPlot("Datos", x, y);
        
        //evento click en el boton de borrar la grafica
        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                ejeX.clear();//vacia los arrays
                ejeY.clear();
                plot.removeAllPlots();//quita todos los puntos graficados
                txtCSV.setText("");//borra el texto en el txtCsv
                
            }
        });
        //Evento clic en boton de leer un csv
        btnGraficar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                //obtenemos los vslores ingresados separados por comas
                String[] valores = txtCSV.getText().split(",");

                //obtener los valores numericos del split
                double distancia = Double.parseDouble(valores[0]);
                double tiempo = Double.parseDouble(valores[1]);
                double velocidad = Double.parseDouble(valores[2]);
                double aceleracion = Double.parseDouble(valores[3]);
                
                //al array del eje x le agregamos el tiempo
                agregarEnX(tiempo);
                //al array del eje y le agreamos las otras mediciones
                agregarEnY(velocidad);
                agregarEnY(aceleracion);
                agregarEnY(distancia);
                //convertimos los array list a un array de double
                double[] puntosX = toDoubleArray(ejeX);
                double[] puntosY = toDoubleArray(ejeY);

                //borrar todos los puntos de la grafica
                plot.removeAllPlots();
                //mandamos a graficar los arrays
                plot.addScatterPlot("Datos", puntosX, puntosY);
                
            }
        });
    }
    
    private void agregarEnX(double valor) {
        this.ejeX.add(valor);//anadimos el valor al eje
    }
    
    private void agregarEnY(double valor) {
        this.ejeY.add(valor);//anadimos el valor al eje
    }

    //Panel principal que trae los otros paneles para generar la interfaz grafica
    //ordena los paneles
    private JPanel construirPanelPrincipal() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(plot, BorderLayout.CENTER);//Se posiciona la grafica en el centro del panel principal
        panelPrincipal.add(contruirPanelSur(), BorderLayout.SOUTH);
        return panelPrincipal;
    }

    //Panel que se localiza en la parte inferior de la interfaz grafica
    private JPanel contruirPanelSur() {
        JPanel pSur = new JPanel();
        pSur.add(new JLabel("insertar CSV: "));
        pSur.add(txtCSV);
        pSur.add(btnGraficar);
        pSur.add(btnBorrar);
        return pSur;
    }
    
    public static void main(String[] args) {
        Graficas graficas = new Graficas();
    }
    
    private double[] toDoubleArray(ArrayList arreglo) {
        //creamos un nuevo array con la dimencion del array list enviado
        double[] nuevoArreglo = new double[arreglo.size()];
        //iteramos en el array creado
        for (int x = 0; x < nuevoArreglo.length; x++) {
            //por cada iteracion asiganos un elemento nuevo al array con el elemento del array list en la posicion x
            nuevoArreglo[x] = (Double) arreglo.get(x);
        }
        return nuevoArreglo;
    }
    
}

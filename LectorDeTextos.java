import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class LectorDeTextos extends JFrame {
    private JTextArea areaTexto;
    private File archivoSeleccionado;
    private JButton botonEditar;
    private JButton botonGuardar;
    private JPanel panelBotones;

    public LectorDeTextos() {
        super("Lector de Textos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.setPreferredSize(new Dimension(100, 30));
        botonBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser selectorArchivos = new JFileChooser();
                int resultado = selectorArchivos.showOpenDialog(LectorDeTextos.this);
                if (resultado == JFileChooser.APPROVE_OPTION) {
                    archivoSeleccionado = selectorArchivos.getSelectedFile();
                    mostrarContenidoArchivo(archivoSeleccionado);
                    botonEditar.setEnabled(true);
                    botonGuardar.setEnabled(true);
                }
            }
        });

        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBuscar.add(botonBuscar);
        panelPrincipal.add(panelBuscar, BorderLayout.NORTH);

        areaTexto = new JTextArea();
        JScrollPane panelDesplazamiento = new JScrollPane(areaTexto);
        panelPrincipal.add(panelDesplazamiento, BorderLayout.CENTER);

        botonEditar = new JButton("Editar");
        botonEditar.setEnabled(false);
        botonEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                areaTexto.setEditable(true);
            }
        });

        botonGuardar = new JButton("Guardar");
        botonGuardar.setEnabled(false);
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (archivoSeleccionado != null) {
                    try {
                        FileWriter escritor = new FileWriter(archivoSeleccionado);
                        escritor.write(areaTexto.getText());
                        escritor.close();
                        JOptionPane.showMessageDialog(LectorDeTextos.this, "Se guardaron los cambios correctamente.",
                                "Guardar", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(LectorDeTextos.this, "Error al guardar el archivo.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(botonEditar);
        panelBotones.add(botonGuardar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    private void mostrarContenidoArchivo(File archivo) {
        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
            lector.close();
            areaTexto.setText(contenido.toString());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LectorDeTextos().setVisible(true);
            }
        });
    }
}

package vista;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import modelo.Provincia;
import modelo.Region;
import presenter.Presenter;

public class VentanaPrincipal extends JFrame implements IVentanaPrincipal {

    private static final long serialVersionUID = 1L;

    // Presenter
    private Presenter presenter;

    // Componentes para agregar Provincia
    private JTextField txtNombreProvincia;
    private JButton btnAgregarProvincia;

    // Componentes para agregar Similaridad (Arista)
    private JComboBox<Provincia> comboProvincia1;
    private JComboBox<Provincia> comboProvincia2;
    private JTextField txtPeso;
    private JButton btnAgregarArista;

    // Componentes para ejecutar el algoritmo
    private JSpinner spinnerK;
    private JButton btnCalcularRegiones;

    // Listas y tablas de visualización
    private DefaultListModel<Provincia> listModelProvincias;
    private JList<Provincia> listProvincias;
    private DefaultTableModel modelAristas;
    private JTable tablaAristas;
    private JTextArea txtResultadoRegiones;

    public VentanaPrincipal() {
        super("Diseñador de Regiones - Programación III");
        this.presenter = new Presenter(this);

        initGUI();
    }

    private void initGUI() {
        // Ajuste de resolución (Máximo permitido: 1366x768)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 680);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(new BorderLayout(10, 10));

        // Panel Izquierdo: Carga de Datos y Control
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setPreferredSize(new Dimension(380, 0));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelIzquierdo.add(crearPanelProvincias());
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(crearPanelAristas());
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(crearPanelEjecucion());

        // Panel Derecho: Visualización
        JPanel panelDerecho = new JPanel(new GridLayout(2, 1, 10, 10));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        // Subpanel Superior Derecho: Vista de Aristas/Similaridades registradas
        modelAristas = new DefaultTableModel(new Object[]{"Provincia 1", "Provincia 2", "Similaridad (Peso)"}, 0);
        tablaAristas = new JTable(modelAristas);
        JScrollPane scrollTabla = new JScrollPane(tablaAristas);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Conexiones y Similaridades Cargadas"));

        // Subpanel Inferior Derecho: Resultado de las Regiones
        txtResultadoRegiones = new JTextArea();
        txtResultadoRegiones.setEditable(false);
        txtResultadoRegiones.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollRegiones = new JScrollPane(txtResultadoRegiones);
        scrollRegiones.setBorder(BorderFactory.createTitledBorder("Regiones Formadas"));

        panelDerecho.add(scrollTabla);
        panelDerecho.add(scrollRegiones);

        // Ensamblar en el Frame principal
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);
    }

    private JPanel crearPanelProvincias() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("1. Cargar Provincias"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombreProvincia = new JTextField(15);
        btnAgregarProvincia = new JButton("Agregar Provincia");

        listModelProvincias = new DefaultListModel<>();
        listProvincias = new JList<>(listModelProvincias);
        JScrollPane scrollLista = new JScrollPane(listProvincias);
        scrollLista.setPreferredSize(new Dimension(150, 80));

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(txtNombreProvincia, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(btnAgregarProvincia, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(scrollLista, gbc);

        // Evento Botón
        btnAgregarProvincia.addActionListener(e -> {
            presenter.agregarProvincia(txtNombreProvincia.getText());
        });

        return panel;
    }

    private JPanel crearPanelAristas() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("2. Cargar Similaridad (Aristas)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboProvincia1 = new JComboBox<>();
        comboProvincia2 = new JComboBox<>();
        txtPeso = new JTextField(8);
        btnAgregarArista = new JButton("Conectar Provincias");

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Provincia 1:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(comboProvincia1, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Provincia 2:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(comboProvincia2, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Peso / Similaridad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtPeso, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnAgregarArista, gbc);

        // Evento Botón
        btnAgregarArista.addActionListener(e -> {
            try {
                Provincia p1 = (Provincia) comboProvincia1.getSelectedItem();
                Provincia p2 = (Provincia) comboProvincia2.getSelectedItem();
                double peso = Double.parseDouble(txtPeso.getText().trim());

                presenter.agregarSimilaridad(p1, p2, peso);

                // Actualizar tabla visual local
                if (p1 != null && p2 != null && !p1.equals(p2) && peso > 0) {
                    modelAristas.addRow(new Object[]{p1.getNombre(), p2.getNombre(), peso});
                }
            } catch (NumberFormatException ex) {
                mostrarError("Por favor ingrese un número válido para la similaridad/peso.");
            }
        });

        return panel;
    }

    private JPanel crearPanelEjecucion() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("3. Diseñar Regiones"));

        spinnerK = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        btnCalcularRegiones = new JButton("Calcular Regiones");
        btnCalcularRegiones.setFont(new Font("SansSerif", Font.BOLD, 12));

        panel.add(new JLabel("Cantidad de Regiones (k):"));
        panel.add(spinnerK);
        panel.add(btnCalcularRegiones);

        // Evento Botón
        btnCalcularRegiones.addActionListener(e -> {
            int k = (Integer) spinnerK.getValue();
            presenter.calcularRegiones(k);
        });

        return panel;
    }

    // --- MÉTODOS DE IVentanaPrincipal ---

    @Override
    public void mostrarProvincias(List<Provincia> provincias) {
        listModelProvincias.clear();
        comboProvincia1.removeAllItems();
        comboProvincia2.removeAllItems();

        for (Provincia p : provincias) {
            listModelProvincias.addElement(p);
            comboProvincia1.addItem(p);
            comboProvincia2.addItem(p);
        }
    }

    @Override
    public void mostrarRegiones(List<Region> regiones) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REGIONES GENERADAS (Total: ").append(regiones.size()).append(") ===\n\n");

        int nroRegion = 1;
        for (Region region : regiones) {
            sb.append("• Región ").append(nroRegion++).append(":\n");
            for (Provincia p : region.getProvincias()) {
                sb.append("   - ").append(p.getNombre()).append("\n");
            }
            sb.append("\n");
        }

        txtResultadoRegiones.setText(sb.toString());
    }

    @Override
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Atención / Error", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void limpiarCampos() {
        txtNombreProvincia.setText("");
        txtPeso.setText("");
    }

    // Método main de entrada
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new VentanaPrincipal().setVisible(true);
        });
    }
}
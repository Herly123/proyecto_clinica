/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import conexion.conexionbd;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Herly 
 */
public class Busqueda extends javax.swing.JFrame {
    
    
        conexionbd cnn = new conexionbd();
        Connection cn = cnn.conexion();
        
  //Metodo de ajuste de ancho de columas      
    public void ajustarAnchoColumnas(JTable table) {
      for (int columna = 0; columna < table.getColumnCount(); columna++) {
        TableColumn column = table.getColumnModel().getColumn(columna);
        int anchoMaximo = 50; // Ancho mínimo

        for (int fila = 0; fila < table.getRowCount(); fila++) {
            TableCellRenderer renderer = table.getCellRenderer(fila, columna);
            Component componente = table.prepareRenderer(renderer, fila, columna);
            anchoMaximo = Math.max(componente.getPreferredSize().width + 11, anchoMaximo);
        }

        column.setPreferredWidth(anchoMaximo);
    }
}

  
   //Metodo de mostrar datos del paciente
        
   public void Mostrar(String idPaciente) {
            
    ModeloTablaNoEditable modelo = new ModeloTablaNoEditable();
    jTable111.setModel(modelo);
   
    modelo.addColumn("ID");
    modelo.addColumn("Nombre");
    modelo.addColumn("Apellido");
    modelo.addColumn("Segundo Apellido");
    modelo.addColumn("Documento");
    modelo.addColumn("Fecha Nac.");
    modelo.addColumn("Edad");
    modelo.addColumn("Sexo");
    modelo.addColumn("Teléfono");
    modelo.addColumn("Correo");
    modelo.addColumn("Dirección");
    modelo.addColumn("Fecha Registro");

    jTable111.setModel(modelo);

    String sql;
    if (idPaciente.equals("")) {
        sql = "SELECT * FROM pacientes";
    } else {
        sql = "SELECT * FROM pacientes WHERE documento_identidad LIKE ? OR nombre LIKE ?";
    }

    try {
        PreparedStatement ps;
        if (idPaciente.equals("")) {
            ps = cn.prepareStatement(sql);
        } else {
            ps = cn.prepareStatement(sql);
            ps.setString(1, idPaciente + "%");
            ps.setString(2, idPaciente + "%");
        }

        ResultSet resul = ps.executeQuery();
        String[] paciente = new String[12];
        while (resul.next()) {
            paciente[0] = resul.getString("id_paciente");
            paciente[1] = resul.getString("nombre");
            paciente[2] = resul.getString("apellido");
            paciente[3] = resul.getString("apellido2");
            paciente[4] = resul.getString("documento_identidad");
            paciente[5] = resul.getString("fecha_nacimiento");
            paciente[6] = resul.getString("edad");
            paciente[7] = resul.getString("sexo");
            paciente[8] = resul.getString("telefono");
            paciente[9] = resul.getString("correo");
            paciente[10] = resul.getString("direccion");
            paciente[11] = resul.getString("fecha_registro");

            modelo.addRow(paciente);
        }

        jTable111.setModel(modelo);
    } catch (SQLException ex) {
        Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
    }
}

        //Metodo para Mostrar historial clinico
        
        public void mostrarHistorialClinicoPorDocumento(String documentoIdentidad) {
    String sqlPaciente = "SELECT id_paciente FROM pacientes WHERE documento_identidad = ?";
    String sqlHistorial = "SELECT id_historial, estado_actual, enfermedades_previas, alergias, medicamentos_actuales, antecedentes_familiares FROM historial_clinico WHERE id_paciente = ?";

    try {
        PreparedStatement psPaciente = cn.prepareStatement(sqlPaciente);
        psPaciente.setString(1, documentoIdentidad);
        ResultSet rsPaciente = psPaciente.executeQuery();

        if (rsPaciente.next()) {
            String idPaciente = rsPaciente.getString("id_paciente");

            PreparedStatement psHistorial = cn.prepareStatement(sqlHistorial);
            psHistorial.setString(1, idPaciente);
            ResultSet rsHistorial = psHistorial.executeQuery();

            if (rsHistorial.next()) {
                txtIdHistorial8.setText(rsHistorial.getString("id_historial"));
                txtEstadoActual8.setText(rsHistorial.getString("estado_actual"));
                txtEnfermedadesPrevias8.setText(rsHistorial.getString("enfermedades_previas"));
                txtAlergias8.setText(rsHistorial.getString("alergias"));
                txtMedicamentosActuales8.setText(rsHistorial.getString("medicamentos_actuales"));
                txtAntecedentesFamiliares8.setText(rsHistorial.getString("antecedentes_familiares"));
            } else {
                limpiarCampos();
                JOptionPane.showMessageDialog(null, "El paciente no tiene historial clínico registrado.");
            }
        } else {
            limpiarCampos();
            JOptionPane.showMessageDialog(null, "No se encontró un paciente con ese documento.");
        }

    } catch (SQLException ex) {
        Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    //Metodo para limpiar campos
        private void limpiarCampos() {
   
        txtIdHistorial8.setText("");
        txtEstadoActual8.setText("");
        txtEnfermedadesPrevias8.setText("");
        txtAlergias8.setText("");
        txtMedicamentosActuales8.setText("");
        txtAntecedentesFamiliares8.setText("");
    

        jTextFieldID.setText("");
        jTextFieldNombre.setText("");
        jTextFieldApellido.setText("");
        jTextFieldSegundoApe.setText("");
        jTextFieldDocumento.setText("");
        jTextFieldFechaNac.setText("");
        jTextFieldEdad.setText("");
        jTextFieldSexo.setText("");
        jTextFieldTelefono.setText("");
        jTextField16correo.setText("");
        jTextFieldDireccion.setText("");
        jTextFieldFechaRegistro.setText("");
}
        
        
        
     
      //Modificar
        
        public void Modificar(){
    int fila = jTable111.getSelectedRow();
    if (fila >= 0) {
        jTextFieldID.setText(jTable111.getValueAt(fila, 0).toString());           
        jTextFieldNombre.setText(jTable111.getValueAt(fila, 1).toString());       
        jTextFieldApellido.setText(jTable111.getValueAt(fila, 2).toString());    
        jTextFieldSegundoApe.setText(jTable111.getValueAt(fila, 3).toString());   
        jTextFieldDocumento.setText(jTable111.getValueAt(fila, 4).toString());     
        jTextFieldFechaNac.setText(jTable111.getValueAt(fila, 5).toString());     
        jTextFieldEdad.setText(jTable111.getValueAt(fila, 6).toString());         
        jTextFieldSexo.setText(jTable111.getValueAt(fila, 7).toString());        
        jTextFieldTelefono.setText(jTable111.getValueAt(fila, 8).toString());     
       jTextField16correo.setText(jTable111.getValueAt(fila, 9).toString());       
        jTextFieldDireccion.setText(jTable111.getValueAt(fila, 10).toString()); 
        jTextFieldFechaRegistro.setText(jTable111.getValueAt(fila, 11).toString());
    } else {
        JOptionPane.showMessageDialog(null, "Seleccione una fila");
    }
}

    // Metodo para modificar datos del historial clinico
    public void ModificarDatosHistorial() {
       try {
        PreparedStatement ps = cn.prepareStatement(
            "UPDATE historial_clinico SET " +
            "enfermedades_previas = ?, " +
            "alergias = ?, " +
            "medicamentos_actuales = ?, " +
            "antecedentes_familiares = ?, " +
            "estado_actual = ? " +
            "WHERE id_historial = ?"
        );

        ps.setString(1, txtEnfermedadesPrevias8.getText());
        ps.setString(2, txtAlergias8.getText());
        ps.setString(3, txtMedicamentosActuales8.getText());
        ps.setString(4, txtAntecedentesFamiliares8.getText());
        ps.setString(5, txtEstadoActual8.getText());
        ps.setString(6, txtIdHistorial8.getText());

        ps.executeUpdate();

        JOptionPane.showMessageDialog(null, "Historial clínico actualizado correctamente");

        // Limpiar campos si lo deseas
        txtIdHistorial8.setText("");
        txtEstadoActual8.setText("");
        txtEnfermedadesPrevias8.setText("");
        txtAlergias8.setText("");
        txtMedicamentosActuales8.setText("");
        txtAntecedentesFamiliares8.setText("");

    } catch (SQLException ex) {
        Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    

    //Metodo de bloqueo de jTextField
    public void BloquearCampos() {
    jTextFieldID.setEditable(false);
    jTextFieldNombre.setEditable(false);
    jTextFieldApellido.setEditable(false);
    jTextFieldSegundoApe.setEditable(false);
    jTextFieldDocumento.setEditable(false);
    jTextFieldFechaNac.setEditable(false);
    jTextFieldEdad.setEditable(false);
    jTextFieldSexo.setEditable(false);
    jTextFieldTelefono.setEditable(false);
    jTextField16correo.setEditable(false);
    jTextFieldDireccion.setEditable(false);
    jTextFieldFechaRegistro.setEditable(false);
    txtIdHistorial8.setEditable(false);
}
    
    //Metodo para que en la tabla no se puedan editar los datos

public class ModeloTablaNoEditable extends DefaultTableModel {

    public ModeloTablaNoEditable() {
          
    }
    // Sobrescribir este método para que ninguna celda sea editable
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

    
    
    
      
    ////////////////////////////////////////////////////////////////////////////
        
    /**
     * Creates new form Busqueda
     */
    public Busqueda() {
    initComponents();
    setExtendedState(JFrame.MAXIMIZED_BOTH);  
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    Mostrar("");
    ajustarAnchoColumnas(jTable111);
    BloquearCampos();

        
    }
    
      
    ////////////////////////////////////////////////////////////////////////////

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        SALIRV = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        HISP = new javax.swing.JPanel();
        HISP1 = new javax.swing.JPanel();
        volver1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        panelRound2 = new proyecto.PanelRound();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable111 = new javax.swing.JTable();
        Buscar = new javax.swing.JFormattedTextField();
        jLabel74 = new javax.swing.JLabel();
        panelRound3 = new proyecto.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        panelRound6 = new proyecto.PanelRound();
        jTextFieldDocumento = new javax.swing.JTextField();
        jTextFieldID = new javax.swing.JFormattedTextField();
        panelRound4 = new proyecto.PanelRound();
        panelRound7 = new proyecto.PanelRound();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares2 = new javax.swing.JTextArea();
        jScrollPane18 = new javax.swing.JScrollPane();
        txtAlergias2 = new javax.swing.JTextArea();
        jLabel43 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        txtMedicamentosActuales2 = new javax.swing.JTextArea();
        txtIdHistorial2 = new javax.swing.JTextField();
        jScrollPane20 = new javax.swing.JScrollPane();
        txtEstadoActual2 = new javax.swing.JTextArea();
        jScrollPane21 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias2 = new javax.swing.JTextArea();
        panelRound8 = new proyecto.PanelRound();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares3 = new javax.swing.JTextArea();
        jScrollPane22 = new javax.swing.JScrollPane();
        txtAlergias3 = new javax.swing.JTextArea();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        txtMedicamentosActuales3 = new javax.swing.JTextArea();
        txtIdHistorial3 = new javax.swing.JTextField();
        jScrollPane24 = new javax.swing.JScrollPane();
        txtEstadoActual3 = new javax.swing.JTextArea();
        jScrollPane25 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias3 = new javax.swing.JTextArea();
        panelRound9 = new proyecto.PanelRound();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares4 = new javax.swing.JTextArea();
        jScrollPane27 = new javax.swing.JScrollPane();
        txtAlergias4 = new javax.swing.JTextArea();
        jLabel55 = new javax.swing.JLabel();
        jScrollPane28 = new javax.swing.JScrollPane();
        txtMedicamentosActuales4 = new javax.swing.JTextArea();
        txtIdHistorial4 = new javax.swing.JTextField();
        jScrollPane29 = new javax.swing.JScrollPane();
        txtEstadoActual4 = new javax.swing.JTextArea();
        jScrollPane30 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias4 = new javax.swing.JTextArea();
        panelRound5 = new proyecto.PanelRound();
        jLabel24 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares1 = new javax.swing.JTextArea();
        jScrollPane14 = new javax.swing.JScrollPane();
        txtAlergias1 = new javax.swing.JTextArea();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        txtMedicamentosActuales1 = new javax.swing.JTextArea();
        txtIdHistorial1 = new javax.swing.JTextField();
        jScrollPane16 = new javax.swing.JScrollPane();
        txtEstadoActual1 = new javax.swing.JTextArea();
        jScrollPane17 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias1 = new javax.swing.JTextArea();
        panelRound10 = new proyecto.PanelRound();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jScrollPane31 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares5 = new javax.swing.JTextArea();
        jScrollPane32 = new javax.swing.JScrollPane();
        txtAlergias5 = new javax.swing.JTextArea();
        jLabel61 = new javax.swing.JLabel();
        jScrollPane33 = new javax.swing.JScrollPane();
        txtMedicamentosActuales5 = new javax.swing.JTextArea();
        txtIdHistorial5 = new javax.swing.JTextField();
        jScrollPane34 = new javax.swing.JScrollPane();
        txtEstadoActual5 = new javax.swing.JTextArea();
        jScrollPane35 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias5 = new javax.swing.JTextArea();
        panelRound11 = new proyecto.PanelRound();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jScrollPane36 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares6 = new javax.swing.JTextArea();
        jScrollPane37 = new javax.swing.JScrollPane();
        txtAlergias6 = new javax.swing.JTextArea();
        jLabel67 = new javax.swing.JLabel();
        jScrollPane38 = new javax.swing.JScrollPane();
        txtMedicamentosActuales6 = new javax.swing.JTextArea();
        txtIdHistorial6 = new javax.swing.JTextField();
        jScrollPane39 = new javax.swing.JScrollPane();
        txtEstadoActual6 = new javax.swing.JTextArea();
        jScrollPane40 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias6 = new javax.swing.JTextArea();
        panelRound12 = new proyecto.PanelRound();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jScrollPane41 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares7 = new javax.swing.JTextArea();
        jScrollPane42 = new javax.swing.JScrollPane();
        txtAlergias7 = new javax.swing.JTextArea();
        jLabel73 = new javax.swing.JLabel();
        jScrollPane43 = new javax.swing.JScrollPane();
        txtMedicamentosActuales7 = new javax.swing.JTextArea();
        txtIdHistorial7 = new javax.swing.JTextField();
        jScrollPane44 = new javax.swing.JScrollPane();
        txtEstadoActual7 = new javax.swing.JTextArea();
        jScrollPane45 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias7 = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTextFieldFechaNac = new javax.swing.JTextField();
        jTextFieldNombre = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextFieldEdad = new javax.swing.JTextField();
        jTextFieldApellido = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextFieldSegundoApe = new javax.swing.JFormattedTextField();
        jTextFieldTelefono = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTextField16correo = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextFieldDireccion = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jTextFieldFechaRegistro = new javax.swing.JTextField();
        panelRound13 = new proyecto.PanelRound();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jScrollPane46 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares8 = new javax.swing.JTextArea();
        jScrollPane47 = new javax.swing.JScrollPane();
        txtAlergias8 = new javax.swing.JTextArea();
        jLabel79 = new javax.swing.JLabel();
        jScrollPane48 = new javax.swing.JScrollPane();
        txtMedicamentosActuales8 = new javax.swing.JTextArea();
        txtIdHistorial8 = new javax.swing.JTextField();
        jScrollPane49 = new javax.swing.JScrollPane();
        txtEstadoActual8 = new javax.swing.JTextArea();
        jScrollPane50 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias8 = new javax.swing.JTextArea();
        panelRound14 = new proyecto.PanelRound();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jScrollPane51 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares9 = new javax.swing.JTextArea();
        jScrollPane52 = new javax.swing.JScrollPane();
        txtAlergias9 = new javax.swing.JTextArea();
        jLabel85 = new javax.swing.JLabel();
        jScrollPane53 = new javax.swing.JScrollPane();
        txtMedicamentosActuales9 = new javax.swing.JTextArea();
        txtIdHistorial9 = new javax.swing.JTextField();
        jScrollPane54 = new javax.swing.JScrollPane();
        txtEstadoActual9 = new javax.swing.JTextArea();
        jScrollPane55 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias9 = new javax.swing.JTextArea();
        panelRound15 = new proyecto.PanelRound();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jScrollPane56 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares10 = new javax.swing.JTextArea();
        jScrollPane57 = new javax.swing.JScrollPane();
        txtAlergias10 = new javax.swing.JTextArea();
        jLabel91 = new javax.swing.JLabel();
        jScrollPane58 = new javax.swing.JScrollPane();
        txtMedicamentosActuales10 = new javax.swing.JTextArea();
        txtIdHistorial10 = new javax.swing.JTextField();
        jScrollPane59 = new javax.swing.JScrollPane();
        txtEstadoActual10 = new javax.swing.JTextArea();
        jScrollPane60 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias10 = new javax.swing.JTextArea();
        panelRound16 = new proyecto.PanelRound();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jScrollPane61 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares11 = new javax.swing.JTextArea();
        jScrollPane62 = new javax.swing.JScrollPane();
        txtAlergias11 = new javax.swing.JTextArea();
        jLabel97 = new javax.swing.JLabel();
        jScrollPane63 = new javax.swing.JScrollPane();
        txtMedicamentosActuales11 = new javax.swing.JTextArea();
        txtIdHistorial11 = new javax.swing.JTextField();
        jScrollPane64 = new javax.swing.JScrollPane();
        txtEstadoActual11 = new javax.swing.JTextArea();
        jScrollPane65 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias11 = new javax.swing.JTextArea();
        panelRound17 = new proyecto.PanelRound();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jScrollPane66 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares12 = new javax.swing.JTextArea();
        jScrollPane67 = new javax.swing.JScrollPane();
        txtAlergias12 = new javax.swing.JTextArea();
        jLabel103 = new javax.swing.JLabel();
        jScrollPane68 = new javax.swing.JScrollPane();
        txtMedicamentosActuales12 = new javax.swing.JTextArea();
        txtIdHistorial12 = new javax.swing.JTextField();
        jScrollPane69 = new javax.swing.JScrollPane();
        txtEstadoActual12 = new javax.swing.JTextArea();
        jScrollPane70 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias12 = new javax.swing.JTextArea();
        panelRound18 = new proyecto.PanelRound();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jScrollPane71 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares13 = new javax.swing.JTextArea();
        jScrollPane72 = new javax.swing.JScrollPane();
        txtAlergias13 = new javax.swing.JTextArea();
        jLabel109 = new javax.swing.JLabel();
        jScrollPane73 = new javax.swing.JScrollPane();
        txtMedicamentosActuales13 = new javax.swing.JTextArea();
        txtIdHistorial13 = new javax.swing.JTextField();
        jScrollPane74 = new javax.swing.JScrollPane();
        txtEstadoActual13 = new javax.swing.JTextArea();
        jScrollPane75 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias13 = new javax.swing.JTextArea();
        panelRound19 = new proyecto.PanelRound();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jScrollPane76 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares14 = new javax.swing.JTextArea();
        jScrollPane77 = new javax.swing.JScrollPane();
        txtAlergias14 = new javax.swing.JTextArea();
        jLabel115 = new javax.swing.JLabel();
        jScrollPane78 = new javax.swing.JScrollPane();
        txtMedicamentosActuales14 = new javax.swing.JTextArea();
        txtIdHistorial14 = new javax.swing.JTextField();
        jScrollPane79 = new javax.swing.JScrollPane();
        txtEstadoActual14 = new javax.swing.JTextArea();
        jScrollPane80 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias14 = new javax.swing.JTextArea();
        panelRound20 = new proyecto.PanelRound();
        jLabel116 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jScrollPane81 = new javax.swing.JScrollPane();
        txtAntecedentesFamiliares15 = new javax.swing.JTextArea();
        jScrollPane82 = new javax.swing.JScrollPane();
        txtAlergias15 = new javax.swing.JTextArea();
        jLabel121 = new javax.swing.JLabel();
        jScrollPane83 = new javax.swing.JScrollPane();
        txtMedicamentosActuales15 = new javax.swing.JTextArea();
        txtIdHistorial15 = new javax.swing.JTextField();
        jScrollPane84 = new javax.swing.JScrollPane();
        txtEstadoActual15 = new javax.swing.JTextArea();
        jScrollPane85 = new javax.swing.JScrollPane();
        txtEnfermedadesPrevias15 = new javax.swing.JTextArea();
        Modificar = new javax.swing.JButton();
        jLabel123 = new javax.swing.JLabel();
        jTextFieldSexo = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        SALIRV1 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        volver = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 0, -1, 629));

        SALIRV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesion (4).png"))); // NOI18N
        SALIRV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SALIRV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SALIRVMouseClicked(evt);
            }
        });
        jPanel1.add(SALIRV, new org.netbeans.lib.awtextra.AbsoluteConstraints(1600, 10, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo.png"))); // NOI18N
        jLabel3.setText("jLabel1");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 210, 280));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 1500));

        HISP.setBackground(new java.awt.Color(0, 153, 255));
        HISP.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HISP1.setBackground(new java.awt.Color(0, 153, 255));
        HISP1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        volver1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/flecha-izquierda (1).png"))); // NOI18N
        volver1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        volver1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volver1MouseClicked(evt);
            }
        });
        volver1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                volver1KeyPressed(evt);
            }
        });
        HISP1.add(volver1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1630, 40, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("N° Documento");
        HISP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("ID paciente:");
        HISP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, -1, -1));

        panelRound2.setBackground(new java.awt.Color(255, 255, 255));
        panelRound2.setRoundBottomLeft(50);
        panelRound2.setRoundBottomRight(50);
        panelRound2.setRoundTopLeft(50);
        panelRound2.setRoundTopRight(50);
        panelRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable111.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable111.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable111MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable111);

        panelRound2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 960, 200));

        Buscar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        Buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuscarMouseClicked(evt);
            }
        });
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });
        Buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BuscarKeyReleased(evt);
            }
        });
        panelRound2.add(Buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 190, 40));

        jLabel74.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel74.setText("Busqueda");
        panelRound2.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        HISP1.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, 980, 660));

        panelRound3.setBackground(new java.awt.Color(255, 255, 255));
        panelRound3.setRoundBottomLeft(50);
        panelRound3.setRoundBottomRight(50);
        panelRound3.setRoundTopLeft(50);
        panelRound3.setRoundTopRight(50);
        panelRound3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario.png"))); // NOI18N
        panelRound3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 270));

        panelRound6.setBackground(new java.awt.Color(255, 255, 255));
        panelRound6.setRoundBottomLeft(50);
        panelRound6.setRoundBottomRight(50);
        panelRound6.setRoundTopLeft(50);
        panelRound6.setRoundTopRight(50);
        panelRound6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelRound3.add(panelRound6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 240, 240));

        HISP1.add(panelRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 240, 240));

        jTextFieldDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDocumentoActionPerformed(evt);
            }
        });
        HISP1.add(jTextFieldDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 170, 30));

        jTextFieldID.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jTextFieldID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIDActionPerformed(evt);
            }
        });
        HISP1.add(jTextFieldID, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 170, 30));

        panelRound4.setBackground(new java.awt.Color(255, 255, 255));
        panelRound4.setRoundBottomLeft(50);
        panelRound4.setRoundBottomRight(50);
        panelRound4.setRoundTopLeft(50);
        panelRound4.setRoundTopRight(50);
        panelRound4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound7.setBackground(new java.awt.Color(255, 255, 255));
        panelRound7.setRoundBottomLeft(50);
        panelRound7.setRoundBottomRight(50);
        panelRound7.setRoundTopLeft(50);
        panelRound7.setRoundTopRight(50);
        panelRound7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel38.setText("Id Historial:");
        panelRound7.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel39.setText("Medicamentos actuales:");
        panelRound7.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel40.setText("Antecedentes familiares:");
        panelRound7.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel41.setText("Alergias:");
        panelRound7.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel42.setText("Enfermedades previas:");
        panelRound7.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares2.setColumns(20);
        txtAntecedentesFamiliares2.setRows(5);
        jScrollPane8.setViewportView(txtAntecedentesFamiliares2);

        panelRound7.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias2.setColumns(20);
        txtAlergias2.setRows(5);
        jScrollPane18.setViewportView(txtAlergias2);

        panelRound7.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel43.setText("Estado actual:");
        panelRound7.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales2.setColumns(20);
        txtMedicamentosActuales2.setRows(5);
        jScrollPane19.setViewportView(txtMedicamentosActuales2);

        panelRound7.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial2ActionPerformed(evt);
            }
        });
        panelRound7.add(txtIdHistorial2, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual2.setColumns(20);
        txtEstadoActual2.setRows(5);
        jScrollPane20.setViewportView(txtEstadoActual2);

        panelRound7.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias2.setColumns(20);
        txtEnfermedadesPrevias2.setRows(5);
        jScrollPane21.setViewportView(txtEnfermedadesPrevias2);

        panelRound7.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound4.add(panelRound7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        panelRound8.setBackground(new java.awt.Color(255, 255, 255));
        panelRound8.setRoundBottomLeft(50);
        panelRound8.setRoundBottomRight(50);
        panelRound8.setRoundTopLeft(50);
        panelRound8.setRoundTopRight(50);
        panelRound8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel44.setText("Id Historial:");
        panelRound8.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel45.setText("Medicamentos actuales:");
        panelRound8.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel46.setText("Antecedentes familiares:");
        panelRound8.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel47.setText("Alergias:");
        panelRound8.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel48.setText("Enfermedades previas:");
        panelRound8.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares3.setColumns(20);
        txtAntecedentesFamiliares3.setRows(5);
        jScrollPane9.setViewportView(txtAntecedentesFamiliares3);

        panelRound8.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias3.setColumns(20);
        txtAlergias3.setRows(5);
        jScrollPane22.setViewportView(txtAlergias3);

        panelRound8.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel49.setText("Estado actual:");
        panelRound8.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales3.setColumns(20);
        txtMedicamentosActuales3.setRows(5);
        jScrollPane23.setViewportView(txtMedicamentosActuales3);

        panelRound8.add(jScrollPane23, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial3ActionPerformed(evt);
            }
        });
        panelRound8.add(txtIdHistorial3, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual3.setColumns(20);
        txtEstadoActual3.setRows(5);
        jScrollPane24.setViewportView(txtEstadoActual3);

        panelRound8.add(jScrollPane24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias3.setColumns(20);
        txtEnfermedadesPrevias3.setRows(5);
        jScrollPane25.setViewportView(txtEnfermedadesPrevias3);

        panelRound8.add(jScrollPane25, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound9.setBackground(new java.awt.Color(255, 255, 255));
        panelRound9.setRoundBottomLeft(50);
        panelRound9.setRoundBottomRight(50);
        panelRound9.setRoundTopLeft(50);
        panelRound9.setRoundTopRight(50);
        panelRound9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel50.setText("Id Historial:");
        panelRound9.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel51.setText("Medicamentos actuales:");
        panelRound9.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel52.setText("Antecedentes familiares:");
        panelRound9.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel53.setText("Alergias:");
        panelRound9.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel54.setText("Enfermedades previas:");
        panelRound9.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares4.setColumns(20);
        txtAntecedentesFamiliares4.setRows(5);
        jScrollPane26.setViewportView(txtAntecedentesFamiliares4);

        panelRound9.add(jScrollPane26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias4.setColumns(20);
        txtAlergias4.setRows(5);
        jScrollPane27.setViewportView(txtAlergias4);

        panelRound9.add(jScrollPane27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel55.setText("Estado actual:");
        panelRound9.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales4.setColumns(20);
        txtMedicamentosActuales4.setRows(5);
        jScrollPane28.setViewportView(txtMedicamentosActuales4);

        panelRound9.add(jScrollPane28, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial4ActionPerformed(evt);
            }
        });
        panelRound9.add(txtIdHistorial4, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual4.setColumns(20);
        txtEstadoActual4.setRows(5);
        jScrollPane29.setViewportView(txtEstadoActual4);

        panelRound9.add(jScrollPane29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias4.setColumns(20);
        txtEnfermedadesPrevias4.setRows(5);
        jScrollPane30.setViewportView(txtEnfermedadesPrevias4);

        panelRound9.add(jScrollPane30, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound8.add(panelRound9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        panelRound4.add(panelRound8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        panelRound5.setBackground(new java.awt.Color(255, 255, 255));
        panelRound5.setRoundBottomLeft(50);
        panelRound5.setRoundBottomRight(50);
        panelRound5.setRoundTopLeft(50);
        panelRound5.setRoundTopRight(50);
        panelRound5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setText("Id Historial:");
        panelRound5.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setText("Medicamentos actuales:");
        panelRound5.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel34.setText("Antecedentes familiares:");
        panelRound5.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel35.setText("Alergias:");
        panelRound5.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel36.setText("Enfermedades previas:");
        panelRound5.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares1.setColumns(20);
        txtAntecedentesFamiliares1.setRows(5);
        jScrollPane7.setViewportView(txtAntecedentesFamiliares1);

        panelRound5.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias1.setColumns(20);
        txtAlergias1.setRows(5);
        jScrollPane14.setViewportView(txtAlergias1);

        panelRound5.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel37.setText("Estado actual:");
        panelRound5.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales1.setColumns(20);
        txtMedicamentosActuales1.setRows(5);
        jScrollPane15.setViewportView(txtMedicamentosActuales1);

        panelRound5.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial1ActionPerformed(evt);
            }
        });
        panelRound5.add(txtIdHistorial1, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual1.setColumns(20);
        txtEstadoActual1.setRows(5);
        jScrollPane16.setViewportView(txtEstadoActual1);

        panelRound5.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias1.setColumns(20);
        txtEnfermedadesPrevias1.setRows(5);
        jScrollPane17.setViewportView(txtEnfermedadesPrevias1);

        panelRound5.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound10.setBackground(new java.awt.Color(255, 255, 255));
        panelRound10.setRoundBottomLeft(50);
        panelRound10.setRoundBottomRight(50);
        panelRound10.setRoundTopLeft(50);
        panelRound10.setRoundTopRight(50);
        panelRound10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel56.setText("Id Historial:");
        panelRound10.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel57.setText("Medicamentos actuales:");
        panelRound10.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel58.setText("Antecedentes familiares:");
        panelRound10.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel59.setText("Alergias:");
        panelRound10.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel60.setText("Enfermedades previas:");
        panelRound10.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares5.setColumns(20);
        txtAntecedentesFamiliares5.setRows(5);
        jScrollPane31.setViewportView(txtAntecedentesFamiliares5);

        panelRound10.add(jScrollPane31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias5.setColumns(20);
        txtAlergias5.setRows(5);
        jScrollPane32.setViewportView(txtAlergias5);

        panelRound10.add(jScrollPane32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel61.setText("Estado actual:");
        panelRound10.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales5.setColumns(20);
        txtMedicamentosActuales5.setRows(5);
        jScrollPane33.setViewportView(txtMedicamentosActuales5);

        panelRound10.add(jScrollPane33, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial5ActionPerformed(evt);
            }
        });
        panelRound10.add(txtIdHistorial5, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual5.setColumns(20);
        txtEstadoActual5.setRows(5);
        jScrollPane34.setViewportView(txtEstadoActual5);

        panelRound10.add(jScrollPane34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias5.setColumns(20);
        txtEnfermedadesPrevias5.setRows(5);
        jScrollPane35.setViewportView(txtEnfermedadesPrevias5);

        panelRound10.add(jScrollPane35, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound5.add(panelRound10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        panelRound11.setBackground(new java.awt.Color(255, 255, 255));
        panelRound11.setRoundBottomLeft(50);
        panelRound11.setRoundBottomRight(50);
        panelRound11.setRoundTopLeft(50);
        panelRound11.setRoundTopRight(50);
        panelRound11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel62.setText("Id Historial:");
        panelRound11.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel63.setText("Medicamentos actuales:");
        panelRound11.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel64.setText("Antecedentes familiares:");
        panelRound11.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel65.setText("Alergias:");
        panelRound11.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel66.setText("Enfermedades previas:");
        panelRound11.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares6.setColumns(20);
        txtAntecedentesFamiliares6.setRows(5);
        jScrollPane36.setViewportView(txtAntecedentesFamiliares6);

        panelRound11.add(jScrollPane36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias6.setColumns(20);
        txtAlergias6.setRows(5);
        jScrollPane37.setViewportView(txtAlergias6);

        panelRound11.add(jScrollPane37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel67.setText("Estado actual:");
        panelRound11.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales6.setColumns(20);
        txtMedicamentosActuales6.setRows(5);
        jScrollPane38.setViewportView(txtMedicamentosActuales6);

        panelRound11.add(jScrollPane38, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial6ActionPerformed(evt);
            }
        });
        panelRound11.add(txtIdHistorial6, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual6.setColumns(20);
        txtEstadoActual6.setRows(5);
        jScrollPane39.setViewportView(txtEstadoActual6);

        panelRound11.add(jScrollPane39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias6.setColumns(20);
        txtEnfermedadesPrevias6.setRows(5);
        jScrollPane40.setViewportView(txtEnfermedadesPrevias6);

        panelRound11.add(jScrollPane40, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound12.setBackground(new java.awt.Color(255, 255, 255));
        panelRound12.setRoundBottomLeft(50);
        panelRound12.setRoundBottomRight(50);
        panelRound12.setRoundTopLeft(50);
        panelRound12.setRoundTopRight(50);
        panelRound12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel68.setText("Id Historial:");
        panelRound12.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel69.setText("Medicamentos actuales:");
        panelRound12.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel70.setText("Antecedentes familiares:");
        panelRound12.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel71.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel71.setText("Alergias:");
        panelRound12.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel72.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel72.setText("Enfermedades previas:");
        panelRound12.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares7.setColumns(20);
        txtAntecedentesFamiliares7.setRows(5);
        jScrollPane41.setViewportView(txtAntecedentesFamiliares7);

        panelRound12.add(jScrollPane41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias7.setColumns(20);
        txtAlergias7.setRows(5);
        jScrollPane42.setViewportView(txtAlergias7);

        panelRound12.add(jScrollPane42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel73.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel73.setText("Estado actual:");
        panelRound12.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales7.setColumns(20);
        txtMedicamentosActuales7.setRows(5);
        jScrollPane43.setViewportView(txtMedicamentosActuales7);

        panelRound12.add(jScrollPane43, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial7ActionPerformed(evt);
            }
        });
        panelRound12.add(txtIdHistorial7, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual7.setColumns(20);
        txtEstadoActual7.setRows(5);
        jScrollPane44.setViewportView(txtEstadoActual7);

        panelRound12.add(jScrollPane44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias7.setColumns(20);
        txtEnfermedadesPrevias7.setRows(5);
        jScrollPane45.setViewportView(txtEnfermedadesPrevias7);

        panelRound12.add(jScrollPane45, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound11.add(panelRound12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        panelRound5.add(panelRound11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        panelRound4.add(panelRound5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        HISP1.add(panelRound4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 910, 620, 90));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Fecha de Nacimiento");
        HISP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, -1, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Nombre");
        HISP1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, -1, -1));

        jTextFieldFechaNac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFechaNacActionPerformed(evt);
            }
        });
        HISP1.add(jTextFieldFechaNac, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 170, 170, 30));

        jTextFieldNombre.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jTextFieldNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreActionPerformed(evt);
            }
        });
        HISP1.add(jTextFieldNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, 170, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("edad");
        HISP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 140, -1, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Primer Apellido");
        HISP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 80, -1, -1));

        jTextFieldEdad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEdadActionPerformed(evt);
            }
        });
        HISP1.add(jTextFieldEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 170, 170, 30));

        jTextFieldApellido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jTextFieldApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldApellidoActionPerformed(evt);
            }
        });
        HISP1.add(jTextFieldApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 170, 30));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("sexo");
        HISP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 140, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Segundo Apellido");
        HISP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 80, -1, -1));

        jTextFieldSegundoApe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jTextFieldSegundoApe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSegundoApeActionPerformed(evt);
            }
        });
        HISP1.add(jTextFieldSegundoApe, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 110, 170, 30));

        jTextFieldTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTelefonoActionPerformed(evt);
            }
        });
        HISP1.add(jTextFieldTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, 170, 30));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Email");
        HISP1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 200, -1, -1));

        jTextField16correo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16correoActionPerformed(evt);
            }
        });
        HISP1.add(jTextField16correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 230, 170, 30));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Direccion");
        HISP1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 200, -1, -1));

        jTextFieldDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDireccionActionPerformed(evt);
            }
        });
        HISP1.add(jTextFieldDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 230, 170, 30));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Fecha de Registro");
        HISP1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 200, -1, -1));

        jTextFieldFechaRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFechaRegistroActionPerformed(evt);
            }
        });
        HISP1.add(jTextFieldFechaRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 230, 170, 30));

        panelRound13.setBackground(new java.awt.Color(255, 255, 255));
        panelRound13.setRoundBottomLeft(50);
        panelRound13.setRoundBottomRight(50);
        panelRound13.setRoundTopLeft(50);
        panelRound13.setRoundTopRight(50);
        panelRound13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel75.setText("Medicamentos actuales:");
        panelRound13.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel76.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel76.setText("Antecedentes familiares:");
        panelRound13.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel77.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel77.setText("Alergias:");
        panelRound13.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel78.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel78.setText("Enfermedades previas:");
        panelRound13.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares8.setColumns(20);
        txtAntecedentesFamiliares8.setRows(5);
        jScrollPane46.setViewportView(txtAntecedentesFamiliares8);

        panelRound13.add(jScrollPane46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias8.setColumns(20);
        txtAlergias8.setRows(5);
        jScrollPane47.setViewportView(txtAlergias8);

        panelRound13.add(jScrollPane47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel79.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel79.setText("Estado actual:");
        panelRound13.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales8.setColumns(20);
        txtMedicamentosActuales8.setRows(5);
        jScrollPane48.setViewportView(txtMedicamentosActuales8);

        panelRound13.add(jScrollPane48, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial8ActionPerformed(evt);
            }
        });
        panelRound13.add(txtIdHistorial8, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual8.setColumns(20);
        txtEstadoActual8.setRows(5);
        jScrollPane49.setViewportView(txtEstadoActual8);

        panelRound13.add(jScrollPane49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias8.setColumns(20);
        txtEnfermedadesPrevias8.setRows(5);
        jScrollPane50.setViewportView(txtEnfermedadesPrevias8);

        panelRound13.add(jScrollPane50, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound14.setBackground(new java.awt.Color(255, 255, 255));
        panelRound14.setRoundBottomLeft(50);
        panelRound14.setRoundBottomRight(50);
        panelRound14.setRoundTopLeft(50);
        panelRound14.setRoundTopRight(50);
        panelRound14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel80.setText("Id Historial:");
        panelRound14.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel81.setText("Medicamentos actuales:");
        panelRound14.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel82.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel82.setText("Antecedentes familiares:");
        panelRound14.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel83.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel83.setText("Alergias:");
        panelRound14.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel84.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel84.setText("Enfermedades previas:");
        panelRound14.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares9.setColumns(20);
        txtAntecedentesFamiliares9.setRows(5);
        jScrollPane51.setViewportView(txtAntecedentesFamiliares9);

        panelRound14.add(jScrollPane51, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias9.setColumns(20);
        txtAlergias9.setRows(5);
        jScrollPane52.setViewportView(txtAlergias9);

        panelRound14.add(jScrollPane52, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel85.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel85.setText("Estado actual:");
        panelRound14.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales9.setColumns(20);
        txtMedicamentosActuales9.setRows(5);
        jScrollPane53.setViewportView(txtMedicamentosActuales9);

        panelRound14.add(jScrollPane53, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial9ActionPerformed(evt);
            }
        });
        panelRound14.add(txtIdHistorial9, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual9.setColumns(20);
        txtEstadoActual9.setRows(5);
        jScrollPane54.setViewportView(txtEstadoActual9);

        panelRound14.add(jScrollPane54, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias9.setColumns(20);
        txtEnfermedadesPrevias9.setRows(5);
        jScrollPane55.setViewportView(txtEnfermedadesPrevias9);

        panelRound14.add(jScrollPane55, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound13.add(panelRound14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        panelRound15.setBackground(new java.awt.Color(255, 255, 255));
        panelRound15.setRoundBottomLeft(50);
        panelRound15.setRoundBottomRight(50);
        panelRound15.setRoundTopLeft(50);
        panelRound15.setRoundTopRight(50);
        panelRound15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel86.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel86.setText("Id Historial:");
        panelRound15.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel87.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel87.setText("Medicamentos actuales:");
        panelRound15.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel88.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel88.setText("Antecedentes familiares:");
        panelRound15.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel89.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel89.setText("Alergias:");
        panelRound15.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel90.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel90.setText("Enfermedades previas:");
        panelRound15.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares10.setColumns(20);
        txtAntecedentesFamiliares10.setRows(5);
        jScrollPane56.setViewportView(txtAntecedentesFamiliares10);

        panelRound15.add(jScrollPane56, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias10.setColumns(20);
        txtAlergias10.setRows(5);
        jScrollPane57.setViewportView(txtAlergias10);

        panelRound15.add(jScrollPane57, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel91.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel91.setText("Estado actual:");
        panelRound15.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales10.setColumns(20);
        txtMedicamentosActuales10.setRows(5);
        jScrollPane58.setViewportView(txtMedicamentosActuales10);

        panelRound15.add(jScrollPane58, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial10ActionPerformed(evt);
            }
        });
        panelRound15.add(txtIdHistorial10, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual10.setColumns(20);
        txtEstadoActual10.setRows(5);
        jScrollPane59.setViewportView(txtEstadoActual10);

        panelRound15.add(jScrollPane59, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias10.setColumns(20);
        txtEnfermedadesPrevias10.setRows(5);
        jScrollPane60.setViewportView(txtEnfermedadesPrevias10);

        panelRound15.add(jScrollPane60, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound16.setBackground(new java.awt.Color(255, 255, 255));
        panelRound16.setRoundBottomLeft(50);
        panelRound16.setRoundBottomRight(50);
        panelRound16.setRoundTopLeft(50);
        panelRound16.setRoundTopRight(50);
        panelRound16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel92.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel92.setText("Id Historial:");
        panelRound16.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel93.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel93.setText("Medicamentos actuales:");
        panelRound16.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel94.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel94.setText("Antecedentes familiares:");
        panelRound16.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel95.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel95.setText("Alergias:");
        panelRound16.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel96.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel96.setText("Enfermedades previas:");
        panelRound16.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares11.setColumns(20);
        txtAntecedentesFamiliares11.setRows(5);
        jScrollPane61.setViewportView(txtAntecedentesFamiliares11);

        panelRound16.add(jScrollPane61, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias11.setColumns(20);
        txtAlergias11.setRows(5);
        jScrollPane62.setViewportView(txtAlergias11);

        panelRound16.add(jScrollPane62, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel97.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel97.setText("Estado actual:");
        panelRound16.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales11.setColumns(20);
        txtMedicamentosActuales11.setRows(5);
        jScrollPane63.setViewportView(txtMedicamentosActuales11);

        panelRound16.add(jScrollPane63, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial11ActionPerformed(evt);
            }
        });
        panelRound16.add(txtIdHistorial11, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual11.setColumns(20);
        txtEstadoActual11.setRows(5);
        jScrollPane64.setViewportView(txtEstadoActual11);

        panelRound16.add(jScrollPane64, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias11.setColumns(20);
        txtEnfermedadesPrevias11.setRows(5);
        jScrollPane65.setViewportView(txtEnfermedadesPrevias11);

        panelRound16.add(jScrollPane65, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound15.add(panelRound16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        panelRound13.add(panelRound15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        panelRound17.setBackground(new java.awt.Color(255, 255, 255));
        panelRound17.setRoundBottomLeft(50);
        panelRound17.setRoundBottomRight(50);
        panelRound17.setRoundTopLeft(50);
        panelRound17.setRoundTopRight(50);
        panelRound17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel98.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel98.setText("Id Historial:");
        panelRound17.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel99.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel99.setText("Medicamentos actuales:");
        panelRound17.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel100.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel100.setText("Antecedentes familiares:");
        panelRound17.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel101.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel101.setText("Alergias:");
        panelRound17.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel102.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel102.setText("Enfermedades previas:");
        panelRound17.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares12.setColumns(20);
        txtAntecedentesFamiliares12.setRows(5);
        jScrollPane66.setViewportView(txtAntecedentesFamiliares12);

        panelRound17.add(jScrollPane66, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias12.setColumns(20);
        txtAlergias12.setRows(5);
        jScrollPane67.setViewportView(txtAlergias12);

        panelRound17.add(jScrollPane67, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel103.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel103.setText("Estado actual:");
        panelRound17.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales12.setColumns(20);
        txtMedicamentosActuales12.setRows(5);
        jScrollPane68.setViewportView(txtMedicamentosActuales12);

        panelRound17.add(jScrollPane68, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial12ActionPerformed(evt);
            }
        });
        panelRound17.add(txtIdHistorial12, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual12.setColumns(20);
        txtEstadoActual12.setRows(5);
        jScrollPane69.setViewportView(txtEstadoActual12);

        panelRound17.add(jScrollPane69, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias12.setColumns(20);
        txtEnfermedadesPrevias12.setRows(5);
        jScrollPane70.setViewportView(txtEnfermedadesPrevias12);

        panelRound17.add(jScrollPane70, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound18.setBackground(new java.awt.Color(255, 255, 255));
        panelRound18.setRoundBottomLeft(50);
        panelRound18.setRoundBottomRight(50);
        panelRound18.setRoundTopLeft(50);
        panelRound18.setRoundTopRight(50);
        panelRound18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel104.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel104.setText("Id Historial:");
        panelRound18.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel105.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel105.setText("Medicamentos actuales:");
        panelRound18.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel106.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel106.setText("Antecedentes familiares:");
        panelRound18.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel107.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel107.setText("Alergias:");
        panelRound18.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel108.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel108.setText("Enfermedades previas:");
        panelRound18.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares13.setColumns(20);
        txtAntecedentesFamiliares13.setRows(5);
        jScrollPane71.setViewportView(txtAntecedentesFamiliares13);

        panelRound18.add(jScrollPane71, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias13.setColumns(20);
        txtAlergias13.setRows(5);
        jScrollPane72.setViewportView(txtAlergias13);

        panelRound18.add(jScrollPane72, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel109.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel109.setText("Estado actual:");
        panelRound18.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales13.setColumns(20);
        txtMedicamentosActuales13.setRows(5);
        jScrollPane73.setViewportView(txtMedicamentosActuales13);

        panelRound18.add(jScrollPane73, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial13ActionPerformed(evt);
            }
        });
        panelRound18.add(txtIdHistorial13, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual13.setColumns(20);
        txtEstadoActual13.setRows(5);
        jScrollPane74.setViewportView(txtEstadoActual13);

        panelRound18.add(jScrollPane74, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias13.setColumns(20);
        txtEnfermedadesPrevias13.setRows(5);
        jScrollPane75.setViewportView(txtEnfermedadesPrevias13);

        panelRound18.add(jScrollPane75, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound17.add(panelRound18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        panelRound19.setBackground(new java.awt.Color(255, 255, 255));
        panelRound19.setRoundBottomLeft(50);
        panelRound19.setRoundBottomRight(50);
        panelRound19.setRoundTopLeft(50);
        panelRound19.setRoundTopRight(50);
        panelRound19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel110.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel110.setText("Id Historial:");
        panelRound19.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel111.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel111.setText("Medicamentos actuales:");
        panelRound19.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel112.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel112.setText("Antecedentes familiares:");
        panelRound19.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel113.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel113.setText("Alergias:");
        panelRound19.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel114.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel114.setText("Enfermedades previas:");
        panelRound19.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares14.setColumns(20);
        txtAntecedentesFamiliares14.setRows(5);
        jScrollPane76.setViewportView(txtAntecedentesFamiliares14);

        panelRound19.add(jScrollPane76, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias14.setColumns(20);
        txtAlergias14.setRows(5);
        jScrollPane77.setViewportView(txtAlergias14);

        panelRound19.add(jScrollPane77, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel115.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel115.setText("Estado actual:");
        panelRound19.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales14.setColumns(20);
        txtMedicamentosActuales14.setRows(5);
        jScrollPane78.setViewportView(txtMedicamentosActuales14);

        panelRound19.add(jScrollPane78, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial14ActionPerformed(evt);
            }
        });
        panelRound19.add(txtIdHistorial14, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual14.setColumns(20);
        txtEstadoActual14.setRows(5);
        jScrollPane79.setViewportView(txtEstadoActual14);

        panelRound19.add(jScrollPane79, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias14.setColumns(20);
        txtEnfermedadesPrevias14.setRows(5);
        jScrollPane80.setViewportView(txtEnfermedadesPrevias14);

        panelRound19.add(jScrollPane80, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound20.setBackground(new java.awt.Color(255, 255, 255));
        panelRound20.setRoundBottomLeft(50);
        panelRound20.setRoundBottomRight(50);
        panelRound20.setRoundTopLeft(50);
        panelRound20.setRoundTopRight(50);
        panelRound20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel116.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel116.setText("Id Historial:");
        panelRound20.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel117.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel117.setText("Medicamentos actuales:");
        panelRound20.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 200, -1));

        jLabel118.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel118.setText("Antecedentes familiares:");
        panelRound20.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel119.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel119.setText("Alergias:");
        panelRound20.add(jLabel119, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 70, 20));

        jLabel120.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel120.setText("Enfermedades previas:");
        panelRound20.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

        txtAntecedentesFamiliares15.setColumns(20);
        txtAntecedentesFamiliares15.setRows(5);
        jScrollPane81.setViewportView(txtAntecedentesFamiliares15);

        panelRound20.add(jScrollPane81, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 300, 120));

        txtAlergias15.setColumns(20);
        txtAlergias15.setRows(5);
        jScrollPane82.setViewportView(txtAlergias15);

        panelRound20.add(jScrollPane82, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 270, 110));

        jLabel121.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel121.setText("Estado actual:");
        panelRound20.add(jLabel121, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtMedicamentosActuales15.setColumns(20);
        txtMedicamentosActuales15.setRows(5);
        jScrollPane83.setViewportView(txtMedicamentosActuales15);

        panelRound20.add(jScrollPane83, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, 110));

        txtIdHistorial15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdHistorial15ActionPerformed(evt);
            }
        });
        panelRound20.add(txtIdHistorial15, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 10, 100, 30));

        txtEstadoActual15.setColumns(20);
        txtEstadoActual15.setRows(5);
        jScrollPane84.setViewportView(txtEstadoActual15);

        panelRound20.add(jScrollPane84, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 110));

        txtEnfermedadesPrevias15.setColumns(20);
        txtEnfermedadesPrevias15.setRows(5);
        jScrollPane85.setViewportView(txtEnfermedadesPrevias15);

        panelRound20.add(jScrollPane85, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 110));

        panelRound19.add(panelRound20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        panelRound17.add(panelRound19, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        panelRound13.add(panelRound17, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        Modificar.setBackground(new java.awt.Color(0, 51, 204));
        Modificar.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        Modificar.setForeground(new java.awt.Color(0, 51, 102));
        Modificar.setText("Modificar");
        Modificar.setBorder(null);
        Modificar.setBorderPainted(false);
        Modificar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Modificar.setFocusPainted(false);
        Modificar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });
        panelRound13.add(Modificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 470, 100, 40));

        jLabel123.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel123.setText("Id Historial:");
        panelRound13.add(jLabel123, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        HISP1.add(panelRound13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 340, 620, 560));

        jTextFieldSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSexoActionPerformed(evt);
            }
        });
        HISP1.add(jTextFieldSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 170, 170, 30));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/escoba.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        HISP1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1175, 400, 440, 140));

        SALIRV1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesion (4).png"))); // NOI18N
        SALIRV1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SALIRV1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SALIRV1MouseClicked(evt);
            }
        });
        HISP1.add(SALIRV1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1690, 40, -1, -1));

        jLabel122.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(255, 255, 255));
        jLabel122.setText("N° Telefono");
        HISP1.add(jLabel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 200, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/escoba.png"))); // NOI18N
        jButton1.setText("Limpiar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        HISP1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 160, -1, 60));

        HISP.add(HISP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, -30, 1780, 1500));

        volver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/flecha-izquierda (1).png"))); // NOI18N
        volver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        volver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volverMouseClicked(evt);
            }
        });
        volver.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                volverKeyPressed(evt);
            }
        });
        HISP.add(volver, new org.netbeans.lib.awtextra.AbsoluteConstraints(1660, 10, -1, -1));

        getContentPane().add(HISP, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 1790, 1500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SALIRVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SALIRVMouseClicked
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿DESEA SALIR?",
            "Confirmar",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_SALIRVMouseClicked

    private void volverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volverMouseClicked

        
    }//GEN-LAST:event_volverMouseClicked

    private void volverKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_volverKeyPressed

    }//GEN-LAST:event_volverKeyPressed

    private void volver1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volver1MouseClicked

        
    }//GEN-LAST:event_volver1MouseClicked

    private void volver1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_volver1KeyPressed

    }//GEN-LAST:event_volver1KeyPressed

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarActionPerformed

    private void jTextFieldDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDocumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDocumentoActionPerformed

    private void jTextFieldIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIDActionPerformed

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        // TODO add your handling code here:
        ModificarDatosHistorial();
    }//GEN-LAST:event_ModificarActionPerformed

    private void BuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuscarMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_BuscarMouseClicked

    private void BuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarKeyReleased
        // TODO add your handling code here:
        Mostrar(Buscar.getText());
    }//GEN-LAST:event_BuscarKeyReleased

    private void jTextFieldFechaNacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFechaNacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFechaNacActionPerformed

    private void jTextFieldNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreActionPerformed

    private void jTextFieldEdadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEdadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEdadActionPerformed

    private void jTextFieldApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldApellidoActionPerformed

    private void jTextFieldSegundoApeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSegundoApeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSegundoApeActionPerformed

    private void jTextFieldTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTelefonoActionPerformed

    private void jTextField16correoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16correoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16correoActionPerformed

    private void jTextFieldDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDireccionActionPerformed

    private void jTextFieldFechaRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFechaRegistroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFechaRegistroActionPerformed

    private void jTable111MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable111MouseClicked
        // TODO add your handling code here:
            //Metodo selec fila
    
 jTable111.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int filaSeleccionada = jTable111.getSelectedRow();

        if (filaSeleccionada >= 0) {
            // Rellenar campos del paciente
            jTextFieldID.setText(jTable111.getValueAt(filaSeleccionada, 0).toString());
            jTextFieldNombre.setText(jTable111.getValueAt(filaSeleccionada, 1).toString());
            jTextFieldApellido.setText(jTable111.getValueAt(filaSeleccionada, 2).toString());
            jTextFieldSegundoApe.setText(jTable111.getValueAt(filaSeleccionada, 3).toString());
            jTextFieldDocumento.setText(jTable111.getValueAt(filaSeleccionada, 4).toString());
            jTextFieldFechaNac.setText(jTable111.getValueAt(filaSeleccionada, 5).toString());
            jTextFieldEdad.setText(jTable111.getValueAt(filaSeleccionada, 6).toString());
            jTextFieldSexo.setText(jTable111.getValueAt(filaSeleccionada, 7).toString());
            jTextFieldTelefono.setText(jTable111.getValueAt(filaSeleccionada, 8).toString());
            jTextField16correo.setText(jTable111.getValueAt(filaSeleccionada, 9).toString());
            jTextFieldDireccion.setText(jTable111.getValueAt(filaSeleccionada, 10).toString());
            jTextFieldFechaRegistro.setText(jTable111.getValueAt(filaSeleccionada, 11).toString());

            // Buscar y mostrar historial clínico
            String documentoIdentidad = jTable111.getValueAt(filaSeleccionada, 4).toString();
            mostrarHistorialClinicoPorDocumento(documentoIdentidad);
        }
    }
});
         Modificar();
    }//GEN-LAST:event_jTable111MouseClicked

    private void txtIdHistorial2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial2ActionPerformed

    private void txtIdHistorial3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial3ActionPerformed

    private void txtIdHistorial4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial4ActionPerformed

    private void txtIdHistorial1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial1ActionPerformed

    private void txtIdHistorial5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial5ActionPerformed

    private void txtIdHistorial6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial6ActionPerformed

    private void txtIdHistorial7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial7ActionPerformed

    private void txtIdHistorial8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial8ActionPerformed

    private void txtIdHistorial9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial9ActionPerformed

    private void txtIdHistorial10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial10ActionPerformed

    private void txtIdHistorial11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial11ActionPerformed

    private void txtIdHistorial12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial12ActionPerformed

    private void txtIdHistorial13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial13ActionPerformed

    private void txtIdHistorial14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial14ActionPerformed

    private void txtIdHistorial15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdHistorial15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdHistorial15ActionPerformed

    private void jTextFieldSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSexoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void SALIRV1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SALIRV1MouseClicked
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿DESEA SALIR?",
            "Confirmar",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_SALIRV1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Busqueda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Busqueda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Busqueda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Busqueda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Busqueda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField Buscar;
    private javax.swing.JPanel HISP;
    private javax.swing.JPanel HISP1;
    private javax.swing.JButton Modificar;
    private javax.swing.JLabel SALIRV;
    private javax.swing.JLabel SALIRV1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane32;
    private javax.swing.JScrollPane jScrollPane33;
    private javax.swing.JScrollPane jScrollPane34;
    private javax.swing.JScrollPane jScrollPane35;
    private javax.swing.JScrollPane jScrollPane36;
    private javax.swing.JScrollPane jScrollPane37;
    private javax.swing.JScrollPane jScrollPane38;
    private javax.swing.JScrollPane jScrollPane39;
    private javax.swing.JScrollPane jScrollPane40;
    private javax.swing.JScrollPane jScrollPane41;
    private javax.swing.JScrollPane jScrollPane42;
    private javax.swing.JScrollPane jScrollPane43;
    private javax.swing.JScrollPane jScrollPane44;
    private javax.swing.JScrollPane jScrollPane45;
    private javax.swing.JScrollPane jScrollPane46;
    private javax.swing.JScrollPane jScrollPane47;
    private javax.swing.JScrollPane jScrollPane48;
    private javax.swing.JScrollPane jScrollPane49;
    private javax.swing.JScrollPane jScrollPane50;
    private javax.swing.JScrollPane jScrollPane51;
    private javax.swing.JScrollPane jScrollPane52;
    private javax.swing.JScrollPane jScrollPane53;
    private javax.swing.JScrollPane jScrollPane54;
    private javax.swing.JScrollPane jScrollPane55;
    private javax.swing.JScrollPane jScrollPane56;
    private javax.swing.JScrollPane jScrollPane57;
    private javax.swing.JScrollPane jScrollPane58;
    private javax.swing.JScrollPane jScrollPane59;
    private javax.swing.JScrollPane jScrollPane60;
    private javax.swing.JScrollPane jScrollPane61;
    private javax.swing.JScrollPane jScrollPane62;
    private javax.swing.JScrollPane jScrollPane63;
    private javax.swing.JScrollPane jScrollPane64;
    private javax.swing.JScrollPane jScrollPane65;
    private javax.swing.JScrollPane jScrollPane66;
    private javax.swing.JScrollPane jScrollPane67;
    private javax.swing.JScrollPane jScrollPane68;
    private javax.swing.JScrollPane jScrollPane69;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane70;
    private javax.swing.JScrollPane jScrollPane71;
    private javax.swing.JScrollPane jScrollPane72;
    private javax.swing.JScrollPane jScrollPane73;
    private javax.swing.JScrollPane jScrollPane74;
    private javax.swing.JScrollPane jScrollPane75;
    private javax.swing.JScrollPane jScrollPane76;
    private javax.swing.JScrollPane jScrollPane77;
    private javax.swing.JScrollPane jScrollPane78;
    private javax.swing.JScrollPane jScrollPane79;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane80;
    private javax.swing.JScrollPane jScrollPane81;
    private javax.swing.JScrollPane jScrollPane82;
    private javax.swing.JScrollPane jScrollPane83;
    private javax.swing.JScrollPane jScrollPane84;
    private javax.swing.JScrollPane jScrollPane85;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable111;
    private javax.swing.JTextField jTextField16correo;
    private javax.swing.JFormattedTextField jTextFieldApellido;
    private javax.swing.JTextField jTextFieldDireccion;
    private javax.swing.JTextField jTextFieldDocumento;
    private javax.swing.JTextField jTextFieldEdad;
    private javax.swing.JTextField jTextFieldFechaNac;
    private javax.swing.JTextField jTextFieldFechaRegistro;
    private javax.swing.JFormattedTextField jTextFieldID;
    private javax.swing.JFormattedTextField jTextFieldNombre;
    private javax.swing.JFormattedTextField jTextFieldSegundoApe;
    private javax.swing.JTextField jTextFieldSexo;
    private javax.swing.JTextField jTextFieldTelefono;
    private proyecto.PanelRound panelRound10;
    private proyecto.PanelRound panelRound11;
    private proyecto.PanelRound panelRound12;
    private proyecto.PanelRound panelRound13;
    private proyecto.PanelRound panelRound14;
    private proyecto.PanelRound panelRound15;
    private proyecto.PanelRound panelRound16;
    private proyecto.PanelRound panelRound17;
    private proyecto.PanelRound panelRound18;
    private proyecto.PanelRound panelRound19;
    private proyecto.PanelRound panelRound2;
    private proyecto.PanelRound panelRound20;
    private proyecto.PanelRound panelRound3;
    private proyecto.PanelRound panelRound4;
    private proyecto.PanelRound panelRound5;
    private proyecto.PanelRound panelRound6;
    private proyecto.PanelRound panelRound7;
    private proyecto.PanelRound panelRound8;
    private proyecto.PanelRound panelRound9;
    private javax.swing.JTextArea txtAlergias1;
    private javax.swing.JTextArea txtAlergias10;
    private javax.swing.JTextArea txtAlergias11;
    private javax.swing.JTextArea txtAlergias12;
    private javax.swing.JTextArea txtAlergias13;
    private javax.swing.JTextArea txtAlergias14;
    private javax.swing.JTextArea txtAlergias15;
    private javax.swing.JTextArea txtAlergias2;
    private javax.swing.JTextArea txtAlergias3;
    private javax.swing.JTextArea txtAlergias4;
    private javax.swing.JTextArea txtAlergias5;
    private javax.swing.JTextArea txtAlergias6;
    private javax.swing.JTextArea txtAlergias7;
    private javax.swing.JTextArea txtAlergias8;
    private javax.swing.JTextArea txtAlergias9;
    private javax.swing.JTextArea txtAntecedentesFamiliares1;
    private javax.swing.JTextArea txtAntecedentesFamiliares10;
    private javax.swing.JTextArea txtAntecedentesFamiliares11;
    private javax.swing.JTextArea txtAntecedentesFamiliares12;
    private javax.swing.JTextArea txtAntecedentesFamiliares13;
    private javax.swing.JTextArea txtAntecedentesFamiliares14;
    private javax.swing.JTextArea txtAntecedentesFamiliares15;
    private javax.swing.JTextArea txtAntecedentesFamiliares2;
    private javax.swing.JTextArea txtAntecedentesFamiliares3;
    private javax.swing.JTextArea txtAntecedentesFamiliares4;
    private javax.swing.JTextArea txtAntecedentesFamiliares5;
    private javax.swing.JTextArea txtAntecedentesFamiliares6;
    private javax.swing.JTextArea txtAntecedentesFamiliares7;
    private javax.swing.JTextArea txtAntecedentesFamiliares8;
    private javax.swing.JTextArea txtAntecedentesFamiliares9;
    private javax.swing.JTextArea txtEnfermedadesPrevias1;
    private javax.swing.JTextArea txtEnfermedadesPrevias10;
    private javax.swing.JTextArea txtEnfermedadesPrevias11;
    private javax.swing.JTextArea txtEnfermedadesPrevias12;
    private javax.swing.JTextArea txtEnfermedadesPrevias13;
    private javax.swing.JTextArea txtEnfermedadesPrevias14;
    private javax.swing.JTextArea txtEnfermedadesPrevias15;
    private javax.swing.JTextArea txtEnfermedadesPrevias2;
    private javax.swing.JTextArea txtEnfermedadesPrevias3;
    private javax.swing.JTextArea txtEnfermedadesPrevias4;
    private javax.swing.JTextArea txtEnfermedadesPrevias5;
    private javax.swing.JTextArea txtEnfermedadesPrevias6;
    private javax.swing.JTextArea txtEnfermedadesPrevias7;
    private javax.swing.JTextArea txtEnfermedadesPrevias8;
    private javax.swing.JTextArea txtEnfermedadesPrevias9;
    private javax.swing.JTextArea txtEstadoActual1;
    private javax.swing.JTextArea txtEstadoActual10;
    private javax.swing.JTextArea txtEstadoActual11;
    private javax.swing.JTextArea txtEstadoActual12;
    private javax.swing.JTextArea txtEstadoActual13;
    private javax.swing.JTextArea txtEstadoActual14;
    private javax.swing.JTextArea txtEstadoActual15;
    private javax.swing.JTextArea txtEstadoActual2;
    private javax.swing.JTextArea txtEstadoActual3;
    private javax.swing.JTextArea txtEstadoActual4;
    private javax.swing.JTextArea txtEstadoActual5;
    private javax.swing.JTextArea txtEstadoActual6;
    private javax.swing.JTextArea txtEstadoActual7;
    private javax.swing.JTextArea txtEstadoActual8;
    private javax.swing.JTextArea txtEstadoActual9;
    private javax.swing.JTextField txtIdHistorial1;
    private javax.swing.JTextField txtIdHistorial10;
    private javax.swing.JTextField txtIdHistorial11;
    private javax.swing.JTextField txtIdHistorial12;
    private javax.swing.JTextField txtIdHistorial13;
    private javax.swing.JTextField txtIdHistorial14;
    private javax.swing.JTextField txtIdHistorial15;
    private javax.swing.JTextField txtIdHistorial2;
    private javax.swing.JTextField txtIdHistorial3;
    private javax.swing.JTextField txtIdHistorial4;
    private javax.swing.JTextField txtIdHistorial5;
    private javax.swing.JTextField txtIdHistorial6;
    private javax.swing.JTextField txtIdHistorial7;
    private javax.swing.JTextField txtIdHistorial8;
    private javax.swing.JTextField txtIdHistorial9;
    private javax.swing.JTextArea txtMedicamentosActuales1;
    private javax.swing.JTextArea txtMedicamentosActuales10;
    private javax.swing.JTextArea txtMedicamentosActuales11;
    private javax.swing.JTextArea txtMedicamentosActuales12;
    private javax.swing.JTextArea txtMedicamentosActuales13;
    private javax.swing.JTextArea txtMedicamentosActuales14;
    private javax.swing.JTextArea txtMedicamentosActuales15;
    private javax.swing.JTextArea txtMedicamentosActuales2;
    private javax.swing.JTextArea txtMedicamentosActuales3;
    private javax.swing.JTextArea txtMedicamentosActuales4;
    private javax.swing.JTextArea txtMedicamentosActuales5;
    private javax.swing.JTextArea txtMedicamentosActuales6;
    private javax.swing.JTextArea txtMedicamentosActuales7;
    private javax.swing.JTextArea txtMedicamentosActuales8;
    private javax.swing.JTextArea txtMedicamentosActuales9;
    private javax.swing.JLabel volver;
    private javax.swing.JLabel volver1;
    // End of variables declaration//GEN-END:variables
}

package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conexionbd {

    public static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/clinica_santa_lucia";
    String user = "root";
    String password = "";  // Cambia aquí si tu MySQL tiene contraseña
    Connection cn = null;

    public Connection conexion() {
        try {
            Class.forName(driver);
            cn = DriverManager.getConnection(url, user, password);
            // ✅ Mensaje cuando la conexión es exitosa

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, " Error de conexión: ");
        }
        return cn;
    }
}

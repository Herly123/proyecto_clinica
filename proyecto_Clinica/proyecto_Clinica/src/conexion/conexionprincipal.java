package conexion;

import java.sql.Connection;

public class conexionprincipal {
      public static void main(String[] args) {
        // Crear una instancia de CONEXION para establecer la conexión a la base de datos
        //CONEXION CONEXION = new CONEXION();
        conexionbd cnn = new conexionbd();
        Connection cn = cnn.conexion();

    }

  
    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
   
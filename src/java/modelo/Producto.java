/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Leo
 */
public class Producto extends Articulo{
    private int id_marca;
    Conexion cn;
    public Producto() {}
    
        
    public Producto (int id_marca, int id, String producto, String descripcion, float precio_costo, float precio_venta, int existencias) {
        super(id, producto, descripcion, precio_costo, precio_venta, existencias);
        this.id_marca = id_marca;
    }

    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }
    
    public DefaultTableModel leer(){
        DefaultTableModel tabla = new DefaultTableModel();
        try{
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "SELECT e.id_producto as id,e.producto,e.descripcion,e.precio_costo,e.precio_venta,e.existencia,p.marca,p.id_marca FROM productos as e inner join marcas as p on e.idmarca = p.id_marca;";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
            String encabezado[] = {"id_producto","producto","descripcion","precio_costo","precio_venta","existencia","marca","id_marca"};
            tabla.setColumnIdentifiers(encabezado);
            String datos[] = new String[8];
            while (consulta.next()){
                datos[0] = consulta.getString("id_producto");
                datos[1] = consulta.getString("producto");
                datos[2] = consulta.getString("id_marca");
                datos[3] = consulta.getString("descripcion");
                datos[4] = consulta.getString("precio_costo");
                datos[5] = consulta.getString("precio_venta");
                datos[6] = consulta.getString("existencia");
                datos[7] = consulta.getString("marca");
                tabla.addRow(datos);
            }
      
            cn.cerrar_conexion();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        return tabla;
    }
    
    public int agregar(){
        int retorno =0;
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            String query = "insert into productos(id_producto,producto,id_marca,descripcion,precio_costo,precio_venta,existencia,marca) values(?,?,?,?,?,?,?,?);";
            cn.abrir_conexion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setInt(1,getId());
            parametro.setString(2,getProducto());
            parametro.setInt(3, getId_marca());
            parametro.setString(4,getDescripcion());
            parametro.setFloat(5,getPrecio_costo());
            parametro.setFloat(6,getPrecio_venta());
            parametro.setInt(7,getExistencias());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    return retorno;
    }
    
        public int modificar (){
        int retorno =0;
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            String query = "update productos set id_producto = ?,producto= ?,id_marca= ? ,descripcion= ?,precio_costo= ?,precio_venta= ?,existencia= ? where id_producto = ?;";
            cn.abrir_conexion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setInt(1,getId());
            parametro.setString(2,getProducto());
            parametro.setInt(3,getId_marca());
            parametro.setString(4,getDescripcion());
            parametro.setFloat(5,getPrecio_costo());
            parametro.setFloat(6,getPrecio_venta());
            parametro.setInt(7, getExistencias());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    return retorno;
    }
        
        public int eliminar (){
        int retorno =0;
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            String query = "delete from productos  where id_producto = ?;";
            cn.abrir_conexion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    return retorno;
    }
}

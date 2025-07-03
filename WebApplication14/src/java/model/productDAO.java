/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.DbUtils;

/**
 *
 * @author admin
 */
public class productDAO {

    public static final String GET_ALL_PRODUCT = "SELECT * FROM tblProducts";
    public static final String GET_PRODUCT_BY_ID = "SELECT id, name, image, description, price, size, status FROM tblProducts WHERE id = ?";
    public static final String CREATE_PRODCT = "INSERT INTO tblProducts(id,name,image,description,price,size,status) VALUES (?,?,?,?,?,?,?)";
    public static final String UPDATE_PRODCT = "UPDATE tblProducts SET name = ?,image=?,description = ?,price = ?,size = ?,status = ? WHERE id = ?";
    public static final String DELETE_PRODCT = "DELETE FROM tblProducts WHERE id = ?";
    
    public List<productDTO> getAll(){
        List<productDTO> listProduct = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_ALL_PRODUCT);
            rs = pr.executeQuery();
            
            while(rs.next()) {
                productDTO product = new  productDTO();
                product.setId(rs.getString("id"));
                product.setNamwe(rs.getString("name"));
                product.setImage(rs.getString("iamge"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getFloat("price"));
                product.setSize(rs.getString("size"));
                product.setStatus(rs.getBoolean("status"));
                
                listProduct.add(product);
            }
        } catch (Exception e) {
            System.out.println("error in getAll()"+e.getMessage());
            e.printStackTrace();
        }finally{
            closeResources(conn, pr, rs);
        }
        return listProduct;
    }
    
     public productDTO getProductByID(String id) {
        productDTO product = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PRODUCT_BY_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                product = new productDTO();
                product.setId(rs.getString("id"));
                product.setNamwe(rs.getString("name"));
                product.setImage(rs.getString("image"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setSize(rs.getString("size"));
                product.setStatus(rs.getBoolean("status"));
            }
        } catch (Exception e) {
            System.err.println("Error in getProductByID(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return product;
    }
    
    public boolean getCreateProduct(productDTO product){
        boolean success = false;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(CREATE_PRODCT);
            pr.setString(1, product.getId());
            pr.setString(2, product.getName());
            pr.setString(3, product.getImage());
            pr.setString(4, product.getDescription());
            pr.setDouble(5, product.getPrice());
            pr.setString(6, product.getSize());
            pr.setBoolean(7, product.isStatus());
            
            int rowsAffected = pr.executeUpdate();
             success = (rowsAffected>0);
          
        } catch (Exception e) {
            System.out.println("error in add"+e.getMessage());
            e.printStackTrace();
        }finally{
            closeResources(conn, pr, null);
        }
        return success;
    }
    
    public boolean getUpdateProduct(productDTO product){
        boolean success = false;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(UPDATE_PRODCT);
            pr.setString(1, product.getName());
            pr.setString(2, product.getImage());
            pr.setString(3, product.getDescription());
            pr.setDouble(4, product.getPrice());
            pr.setString(5, product.getSize());
            pr.setBoolean(6, product.isStatus());
            pr.setString(7, product.getId());
            
            int rowsAffected = pr.executeUpdate();
             success = (rowsAffected>0);
          
        } catch (Exception e) {
            System.out.println("error in update"+e.getMessage());
            e.printStackTrace();
        }finally{
            closeResources(conn, pr, null);
        }
        return success;
    }
    
    public boolean getDeteleProduct(String id){
        boolean success = false;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(DELETE_PRODCT);
            pr.setString(1, id);
            
            int rowsAffected = pr.executeUpdate();
             success = (rowsAffected>0);
          
        } catch (Exception e) {
            System.out.println("Error in detele"+ e.getMessage());
            e.printStackTrace();
        }finally{
            closeResources(conn, pr, null);
        }
        return success;
    }
    
    private void closeResources(Connection conn,PreparedStatement pr, ResultSet rs){
        try {
            if(conn!=null){
                conn.close();
            }else if(pr!=null){
                pr.close();
            }else{
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing resources: "+ e.getMessage());
            e.printStackTrace();
        }
    }
    
    public boolean isProductExists(String id){
        return getProductByID(id)!=null;
    }
    
    public productDTO getStatusProduct(Boolean status){
        productDTO product = null;
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_ALL_PRODUCT+"WHERE status=?");
            pr.setBoolean(1, status);
            rs = pr.executeQuery();
            
            if(rs.next()) {
                product.setId(rs.getString("id"));
                product.setNamwe(rs.getString("name"));
                product.setImage(rs.getString("image"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getFloat("price"));
                product.setSize(rs.getString("size"));
                product.setStatus(rs.getBoolean("status"));
            }
        } catch (Exception e) {
            System.out.println("error in getProductById()"+e.getMessage());
            e.printStackTrace();
        }finally{
            closeResources(conn, pr, rs);
        }
        return product;
    }
    
    public List<productDTO> getProductByName(String name){
        List<productDTO> listProduct = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement("SELECT * FROM tblProducts WHERE name like ?");
            pr.setString(1, "%"+name+"%");
            
            rs = pr.executeQuery();
            
            while(rs.next()) {
                productDTO product = new  productDTO();
                product.setId(rs.getString("id"));
                product.setNamwe(rs.getString("name"));
                product.setImage(rs.getString("image"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getFloat("price"));
                product.setSize(rs.getString("size"));
                product.setStatus(rs.getBoolean("status"));
                
                listProduct.add(product);
            }
        } catch (Exception e) {
            System.out.println("error in getAll()"+e.getMessage());
            e.printStackTrace();
        }finally{
            closeResources(conn, pr, rs);
        }
        return listProduct;
    }
    
    public List<productDTO> getActiveProductByName(String name){
        List<productDTO> listProduct = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement("SELECT * FROM tblProducts WHERE name like ? and status=1");
            pr.setString(1, "%"+name+"%");
            
            rs = pr.executeQuery();
            
            while(rs.next()) {
                productDTO product = new  productDTO();
                product.setId(rs.getString("id"));
                product.setNamwe(rs.getString("name"));
                product.setImage(rs.getString("image"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getFloat("price"));
                product.setSize(rs.getString("size"));
                product.setStatus(rs.getBoolean("status"));
                
                listProduct.add(product);
            }
        } catch (Exception e) {
            System.out.println("error in getAll()"+e.getMessage());
            e.printStackTrace();
        }finally{
            closeResources(conn, pr, rs);
        }
        return listProduct;
    }
    
    public boolean setStatusProduct(String productID ,boolean b){
        productDTO product = getProductByID(productID);
        if(product!=null){
            product.setStatus(false);
            return getUpdateProduct(product);
        }
        return false;
    }
}
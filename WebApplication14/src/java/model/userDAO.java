/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import utils.DbUtils;
import java.sql.PreparedStatement;
import java.util.List;
import sun.text.normalizer.UBiDiProps;
/**
 *
 * @author admin
 */
public class userDAO {
    
    private static final String ALL_USER = "SELECT * FROM tblUsers";
    private static final String USER_NAME = "SELECT * FROM tblUsers WHERE Username=?";
    private static final String UPDATE_PASSWORD = "UPDATE tblUsers SET password=? WHERE Username=?";

    public userDAO() {
        
    }
    
    public boolean login(String userID, String password){
        userDTO user = getUserById(userID);
        if(user!=null){
            if(user.getPassword().equals(password)){
                if(user.isStatus()){
                    return true;
                }
            }
        } return false;
    }
    
    
    public userDTO getUserById(String id){
        userDTO user = null;
        try{
            String sql  = "SELECT * FROM tblUsers where userID = ?";
            
            Connection conn = DbUtils.getConnection();
            
//          Statement st = conn.createStatement(); nó thoải mái hon có thể cộng nhiều hon


/*              Statement                                       PreparedStatement
Câu lệnh SQL	Viết trực tiếp, chèn giá trị vào chuỗi SQL	Dùng dấu ? để đại diện cho giá trị cần chèn
Hiệu suất	Kém hơn nếu lặp lại nhiều lần                   Tốt hơn vì được biên dịch một lần và dùng lại
Bảo mật         Dễ bị tấn công SQL Injection                    An toàn hơn vì tự động xử lý dữ liệu đầu vào
Dễ sử dụng	Đơn giản khi câu SQL tĩnh, ít thay đổi          Tốt cho câu SQL có tham số hoặc lặp lại nhiều lần

Trường hợp                                                      Nên dùng gì
Câu SQL đơn giản, không có tham số và không lặp lại             Statement
Câu SQL có biến đầu vào từ người dùng                           PreparedStatement
Truy vấn giống nhau lặp lại nhiều lần                           PreparedStatement
Cần bảo mật cao (như đăng nhập, tìm kiếm theo dữ liệu nhập vào)	PreparedStatement
*/          

            PreparedStatement pr = conn.prepareStatement(sql);//nó sẽ cố định dòng code sql
            pr.setString(1, id);
            
            ResultSet rs = pr.executeQuery();
            
            while(rs.next()){
                String userID = rs.getString("userID");
                String fullName = rs.getString("fullName");
                String password = rs.getString("password");
                String roleID = rs.getString("roleID");
                boolean status = rs.getBoolean("status");
                
                user = new userDTO(userID, password, fullName, roleID, status);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return user;
    }

    public List<userDTO> getAllUsers() {
        List<userDTO> user = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(ALL_USER);
            rs = pr.executeQuery();
            while (rs.next()) {                
                userDTO uDTO = new userDTO();
                uDTO.setUserID(rs.getString("userID"));
                uDTO.setPassword(rs.getString("password"));
                uDTO.setFullName(rs.getString("fullName"));
                uDTO.setRoleID(rs.getString("roleID"));
                uDTO.setStatus(rs.getBoolean("status"));
                
                user.add(uDTO);
            }
        } catch (Exception e) {
             System.out.println("Error getAllUsers: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean updatePassword(String id, String passwordUser){
        boolean success = true;
        userDTO uDTO = null;
        Connection conn =null;
        PreparedStatement pr= null;
        ResultSet rs =null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement("UPDATE tblUsers SET password = ? WHERE userID = ?");
            pr.setString(1, passwordUser);
            pr.setString(2, id);
            
            int result = pr.executeUpdate();
            success = (result>0);
            
        } catch (Exception e) {
            System.out.println("Error to getAllUser: "+e.getMessage());
            e.printStackTrace();
        }
        return success;
    }
}

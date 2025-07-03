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

/**
 *
 * @author Admin
 */
public class UserDAO {

    public UserDAO() {
    }

    public boolean login(String userID, String password) {
        UserDTO user = getUserByID(userID);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                if (user.isStatus()) {
                    return true;
                }
            }
        }
        return false;
    }

    public UserDTO getUserByID(String id) {
        UserDTO user = null;
        try {
            //B1:
            String sql = "SELECT * FROM tblUsers WHERE userID='" + id + "'";
            //B2:
            Connection conn = DbUtils.getConnection();
            //B3:
            Statement st = conn.createStatement();
            //B4:
            ResultSet rs = st.executeQuery(sql);

            //B5:
            while (rs.next()) {
                String userID = rs.getString("userID");
                String fullName = rs.getString("fullName");
                String password = rs.getString("password");
                String roleID = rs.getString("roleID");
                boolean status = rs.getBoolean("status");

                user = new UserDTO(userID, password, fullName, roleID, status);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return user;
    }

}

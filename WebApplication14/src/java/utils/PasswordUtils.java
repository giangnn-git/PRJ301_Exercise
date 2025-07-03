/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import model.userDAO;
import model.userDTO;

/**
 *
 * @author admin
 */
public class PasswordUtils {

    public static String encrypeSHA256(String password) {
        if(password.isEmpty()||password==null){
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder hexString = new StringBuilder();
            for(byte hashByte:hashBytes){
                String hex = Integer.toHexString(0xff & hashByte);
                if(hex.length()==1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not available: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error during SHA-256 encryption: " + e.getMessage());
            return null;
        }
    }
    
    public static void main(String[] args) {
//        userDAO uDAO = new userDAO();
//        List<userDTO> list = uDAO.getAllUsers();
//        if(list!=null){
//            for (userDTO u : list) {
//                uDAO.updatePassword(u.getUserID(),encrypeSHA256(u.getPassword()));
//            }
//        }
System.out.println(encrypeSHA256("1"));
    }
}
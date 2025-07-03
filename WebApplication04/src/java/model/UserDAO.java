/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class UserDAO {

    ArrayList<UserDTO> list;

    public UserDAO() {
        this.list = new ArrayList<>();
        list.add(new UserDTO("admin", "admin", "Adminstrator", "AD", true));
        list.add(new UserDTO("se123", "111", "Con Cho keu Meow Meow", "MB", true));
        list.add(new UserDTO("se456", "222", "U I A", "MB", true));
    }

    public boolean login(String userID, String password) {
        for (UserDTO userDTO : list) {
            if (userDTO.getUserID().equalsIgnoreCase(userID)
                    && userDTO.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public UserDTO getUserByID(String id) {
        for (UserDTO userDTO : list) {
            if (userDTO.getUserID().equalsIgnoreCase(id)) {
                return userDTO;
            }
        }
        return null;
    }

}

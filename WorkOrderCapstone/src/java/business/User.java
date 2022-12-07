/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author majle
 */
public class User implements Serializable {
    private int CustomerID;
    private String UserName;
    private String Password;
    private LocalDate AccountAge;
    private String Email;
    private String FirstName;
    private String LastName;
    private String Phone;
    private int RoleID;
    private String Street;
    private String City;
    private String State;
    private int Zip;

    public User() {
    }

    public User(int CustomerID, String UserName, String Password, LocalDate AccountAge, String Email, String FirstName, String LastName, String Phone, int RoleID, String Street, String City, String State, int Zip) {
        this.CustomerID = CustomerID;
        this.UserName = UserName;
        this.Password = Password;
        this.AccountAge = AccountAge;
        this.Email = Email;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Phone = Phone;
        this.RoleID = RoleID;
        this.Street = Street;
        this.City = City;
        this.State = State;
        this.Zip = Zip;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public LocalDate getAccountAge() {
        return AccountAge;
    }

    public void setAccountAge(LocalDate AccountAge) {
        this.AccountAge = AccountAge;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public int getRoleID() {
        return RoleID;
    }

    public void setRoleID(int RoleID) {
        this.RoleID = RoleID;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String Street) {
        this.Street = Street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public int getZip() {
        return Zip;
    }

    public void setZip(int Zip) {
        this.Zip = Zip;
    }
    
    
}

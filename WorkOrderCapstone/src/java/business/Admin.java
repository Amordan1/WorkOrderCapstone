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
public class Admin implements Serializable {
    private int EmployeeID;
    private String FirstName;
    private String LastName;
    private String UserName;
    private String Password;
    private String Phone;
    private int RoleID;
    private LocalDate EmpHireDate;
    private LocalDate EmpBirthDate;
    private String SSN;

    public Admin() {
    }

    public Admin(int EmployeeID, String FirstName, String LastName, String UserName, String Password, String Phone, int RoleID, LocalDate EmpHireDate, LocalDate EmpBirthDate, String SSN) {
        this.EmployeeID = EmployeeID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.UserName = UserName;
        this.Password = Password;
        this.Phone = Phone;
        this.RoleID = RoleID;
        this.EmpHireDate = EmpHireDate;
        this.EmpBirthDate = EmpBirthDate;
        this.SSN = SSN;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public int getRoleID() {
        return RoleID;
    }

    public void setRoleID(int RoleID) {
        this.RoleID = RoleID;
    }

    public LocalDate getEmpHireDate() {
        return EmpHireDate;
    }

    public void setEmpHireDate(LocalDate EmpHireDate) {
        this.EmpHireDate = EmpHireDate;
    }

    public LocalDate getEmpBirthDate() {
        return EmpBirthDate;
    }

    public void setEmpBirthDate(LocalDate EmpBirthDate) {
        this.EmpBirthDate = EmpBirthDate;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }
    
    
}

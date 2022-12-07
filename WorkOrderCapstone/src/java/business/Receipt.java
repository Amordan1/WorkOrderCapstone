/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;

/**
 *
 * @author majle
 */
public class Receipt implements Serializable {
    private int ReceiptID;
    private int WorkOrderID;
    private int CustomerID;
    private float Cost;

    public Receipt() {
    }

    
    
    public Receipt(int ReceiptID, int WorkOrderID, int CustomerID, float Cost) {
        this.ReceiptID = ReceiptID;
        this.WorkOrderID = WorkOrderID;
        this.CustomerID = CustomerID;
        this.Cost = Cost;
    }

    public int getReceiptID() {
        return ReceiptID;
    }

    public void setReceiptID(int ReceiptID) {
        this.ReceiptID = ReceiptID;
    }

    public int getWorkOrderID() {
        return WorkOrderID;
    }

    public void setWorkOrderID(int WorkOrderID) {
        this.WorkOrderID = WorkOrderID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public float getCost() {
        return Cost;
    }

    public void setCost(float Cost) {
        this.Cost = Cost;
    }
    
    
}

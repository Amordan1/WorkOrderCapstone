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
public class WorkOrder implements Serializable {
    private int WorkOrderID;
    private int CustomerID;
    private String WorkOrderDesc;
    private LocalDate WorkOrderStart;
    private LocalDate WordOrderEstEnd;
    private LocalDate WorkOrderEnd;
    private Float EstPrice;
    private Boolean Complete;
    private int AssignedAdmin;

    public WorkOrder() {
    }

    public WorkOrder(int workOrderID, int customerID, String workOrderDesc, LocalDate workOrderStart, LocalDate wordOrderEstEnd, LocalDate workOrderEnd, float estPrice, Boolean complete, int assignedAdmin) {
        this.WorkOrderID = workOrderID;
        this.CustomerID = customerID;
        this.WorkOrderDesc = workOrderDesc;
        this.WorkOrderStart = workOrderStart;
        this.WordOrderEstEnd = wordOrderEstEnd;
        this.WorkOrderEnd = workOrderEnd;
        this.EstPrice = estPrice;
        this.Complete = complete;
        this.AssignedAdmin = assignedAdmin;
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

    public String getWorkOrderDesc() {
        return WorkOrderDesc;
    }

    public void setWorkOrderDesc(String WorkOrderDes) {
        this.WorkOrderDesc = WorkOrderDes;
    }

    public LocalDate getWorkOrderStart() {
        return WorkOrderStart;
    }

    public void setWorkOrderStart(LocalDate WorkOrderStart) {
        this.WorkOrderStart = WorkOrderStart;
    }

    public LocalDate getWorkOrderEstEnd() {
        return WordOrderEstEnd;
    }

    public void setWorkOrderEstEnd(LocalDate WordOrderEstEnd) {
        this.WordOrderEstEnd = WordOrderEstEnd;
    }

    public LocalDate getWorkOrderEnd() {
        return WorkOrderEnd;
    }

    public void setWorkOrderEnd(LocalDate WorkOrderEnd) {
        this.WorkOrderEnd = WorkOrderEnd;
    }

    public float getEstPrice() {
        return EstPrice;
    }

    public void setEstPrice(float EstPrice) {
        this.EstPrice = EstPrice;
    }

    public Boolean getComplete() {
        return Complete;
    }

    public void setComplete(Boolean Complete) {
        this.Complete = Complete;
    }

    public int getAssignedAdmin() {
        return AssignedAdmin;
    }

    public void setAssignedAdmin(int AssignedAdmin) {
        this.AssignedAdmin = AssignedAdmin;
    }
    
    
}

package com.es.phoneshop.web.dataview;

import java.util.Map;

public class OrderData {

    private String firstName;


    private String lastName;


    private String deliveryAddress;


    private String contactPhoneNo;

    private Map<Long, Integer> phoneIdToQuantity;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }

    public Map<Long, Integer> getPhoneIdToQuantity() {
        return phoneIdToQuantity;
    }

    public void setPhoneIdToQuantity(Map<Long, Integer> phoneIdToQuantity) {
        this.phoneIdToQuantity = phoneIdToQuantity;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", contactPhoneNo='" + contactPhoneNo + '\'' +
                ", phoneIdToQuantity=" + phoneIdToQuantity +
                '}';
    }
}

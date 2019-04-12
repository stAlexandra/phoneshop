package com.es.core.model.order;


import org.hibernate.validator.constraints.NotBlank;

public class CustomerInfo {
    private String firstName;

    private String lastName;

    private String deliveryAddress;

    private String contactPhoneNo;

    public CustomerInfo() {
    }

    public CustomerInfo(String firstName, String lastName, String deliveryAddress, String contactPhoneNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deliveryAddress = deliveryAddress;
        this.contactPhoneNo = contactPhoneNo;
    }

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
}

package com.es.core.dao;

import com.es.core.model.phone.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract class AbstractPhoneTest {
    protected static List<Phone> getPhones(int numPhones){
        Random random = new Random();
        List<Phone> phoneList = new ArrayList<>();
        for(int i = 1; i<=numPhones; i++){
            Phone phone = new Phone();
            phone.setBrand("brand" + random.nextInt(100));
            phone.setModel("model" + random.nextInt(100));

            phoneList.add(phone);
        }
        return phoneList;
    }

    protected static Phone getPhone(){
        Random random = new Random();
        Phone phone = new Phone();
        phone.setBrand("brand" + random.nextInt(100));
        phone.setModel("model" + random.nextInt(100));
        return phone;
    }
}

package com.es.core.dao;

import com.es.core.dao.impl.JdbcColorDao;
import com.es.core.dao.impl.JdbcPhoneDao;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationContext-core.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JdbcPhoneDaoIntTest extends AbstractPhoneTest{

    @Autowired
    JdbcPhoneDao jdbcPhoneDao;
    @Autowired
    JdbcColorDao jdbcColorDao;

    @Test
    public void testSaveUpdateGet() {
        Phone givenPhone = getPhone();

        jdbcPhoneDao.save(givenPhone);

        String brandName = "NewBrand";
        givenPhone.setBrand(brandName);
        jdbcPhoneDao.save(givenPhone);

        Phone receivedPhone = jdbcPhoneDao.get(givenPhone.getId());
        assertEquals(brandName, receivedPhone.getBrand());
    }

    @Test
    public void testSave() {
        Phone givenPhone = getPhone();

        jdbcPhoneDao.save(givenPhone);

        assertNotNull(givenPhone);
        Long phoneId = givenPhone.getId();
        assertNotNull(phoneId);
    }

    @Test
    public void testSaveDelete(){
        List<Phone> phones = jdbcPhoneDao.findAll(0, 10);
        assertEquals(0, phones.size());
    }

    @Test
    public void testSaveListFindAll() {
        List<Phone> testPhones = getPhones(5);
        testPhones.forEach(phone -> jdbcPhoneDao.save(phone));

        List<Phone> receivedPhoneList = jdbcPhoneDao.findAll(0, testPhones.size());

        assertEquals(testPhones.size(), receivedPhoneList.size());
    }

    @Test
    public void testGetColorsForPhone(){
        Set<Color> colors = new HashSet<>();
        colors.add(new Color("1111"));
        colors.add(new Color("2222"));
        Phone phone = getPhone();
        phone.setColors(colors);
        jdbcPhoneDao.save(phone);

        Phone receivedPhone = jdbcPhoneDao.get(phone.getId());

        assertEquals(colors.size(), receivedPhone.getColors().size());
        receivedPhone.getColors().forEach(color -> assertNotNull(color.getId()));
    }

    @Test
    public void testGetColorsForManyPhones(){
        Set<Color> colors1 = new HashSet<>();
        colors1.add(new Color("1111"));
        colors1.add(new Color("2222"));
        Set<Color> colors2 = new HashSet<>();
        colors2.add(new Color("3333"));
        colors2.add(new Color("4444"));

        Phone phone1 = getPhone();
        phone1.setColors(colors1);
        Phone phone2 = getPhone();
        phone2.setColors(colors2);

        jdbcPhoneDao.save(phone1);
        jdbcPhoneDao.save(phone2);

        Map<Long, Set<Color>> mapPhoneIdToColors = jdbcColorDao.getColors(Arrays.asList(phone1.getId(), phone2.getId()));

        assertNotNull(mapPhoneIdToColors);
        assertEquals(2, mapPhoneIdToColors.keySet().size());
        assertEquals(colors1.size() + colors2.size(), mapPhoneIdToColors.values().size());
    }
}
package com.es.core.dao;

import com.es.core.dao.impl.JdbcColorDao;
import com.es.core.dao.impl.JdbcPhoneDao;
import com.es.core.exception.PhonesNotFoundException;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationContext-core.xml")
@Sql("classpath:db/testdata-phones.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JdbcPhoneDaoIntTest extends AbstractPhoneTest {
    @Autowired
    private JdbcPhoneDao jdbcPhoneDao;
    @Autowired
    private JdbcColorDao jdbcColorDao;

    @Test(expected = PhonesNotFoundException.class)
    public void testGetPhoneNotExist() {
        jdbcPhoneDao.get(12345L);
    }

    @Test
    public void testSave() {
        Phone givenPhone = getPhone();

        jdbcPhoneDao.save(givenPhone);

        Long phoneId = givenPhone.getId();
        assertNotNull(phoneId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveEmptyPhone() {
        Phone phone = new Phone();

        jdbcPhoneDao.save(phone);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSavePhoneWithoutRequiredFields() {
        Phone phone = new Phone();
        phone.setModel("model");

        jdbcPhoneDao.save(phone);
    }

    @Test
    public void testUpdate() {
        Phone phone = jdbcPhoneDao.get(1000L);

        String brandName = "NewBrandName";
        phone.setBrand(brandName);
        jdbcPhoneDao.save(phone);

        Phone receivedPhone = jdbcPhoneDao.get(1000L);
        assertEquals(phone.getId(), receivedPhone.getId());
        assertEquals(brandName, receivedPhone.getBrand());
    }

    @Test
    public void testFindAll() {
        List<Phone> receivedPhoneList = jdbcPhoneDao.findAll(0, 10);

        assertNotNull(receivedPhoneList);
        receivedPhoneList.stream().map(Phone::getId).forEach(Assert::assertNotNull);
    }

    @Test
    public void testGetColorsForPhone() {
        Phone receivedPhone = jdbcPhoneDao.get(1001L);
        Set<Color> colors = receivedPhone.getColors();

        assertEquals(1, colors.size());
        assertEquals("White", colors.iterator().next().getCode());
    }

    @Test
    public void testGetColorsForManyPhones() {
        Map<Long, Set<Color>> mapPhoneIdToColors = jdbcColorDao.getColors(Arrays.asList(1002L, 1006L));

        assertNotNull(mapPhoneIdToColors);
        assertEquals(2, mapPhoneIdToColors.keySet().size());
        assertEquals(3, mapPhoneIdToColors.values().stream().mapToInt(Collection::size).sum());
    }

    @Test
    public void testFindAllReturnsColors() {
        List<Phone> phones = jdbcPhoneDao.findAll(0, 10);

        assertNotNull(phones);
        for (Phone phone : phones) {
            assertNotNull(phone.getColors());
        }
    }

    @Test
    public void testFindAllWithPositiveStock() {
        int initValidPhonesListSize = jdbcPhoneDao.findAllValid(0, 10).size();
        jdbcPhoneDao.save(getPhone());
        assertEquals(initValidPhonesListSize, jdbcPhoneDao.findAllValid(0, 10).size());
    }

    @Test
    public void testFindValidPhonesTotalCount() {
        assertEquals(jdbcPhoneDao.findAllValid(0, 10).size(), jdbcPhoneDao.findValidPhonesTotalCount());
    }


}
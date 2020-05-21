package com.es.core.service.user;

import com.es.core.dao.LevelDao;
import com.es.core.dao.UserDao;
import com.es.core.dao.UserLevelDiscountDao;
import com.es.core.model.user.Level;
import com.es.core.model.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserLevelServiceTest {
    private static final int CURRENT_MIN_XP = 0;
    private static final int CURRENT_MAX_XP = 10;
    private static final int NEXT_MIN_XP = 10;
    private static final int NEXT_MAX_XP = 20;

    @InjectMocks
    private UserLevelServiceImpl userLevelService;
    @Mock
    private UserDao userDao;
    @Mock
    private LevelDao levelDao;
    @Mock
    private UserLevelDiscountDao userLevelDiscountDao;
    @Mock
    private UserDiscountsService userDiscountsService;

    private User testUser;

    private Level currentLevel;
    private Level nextLevel;

    @Before
    public void setUp(){
        User user = new User();
        this.currentLevel = new Level(1, CURRENT_MIN_XP, CURRENT_MAX_XP);
        this.nextLevel = new Level(2, NEXT_MIN_XP, NEXT_MAX_XP);
        user.setLevel(currentLevel);
        user.setXp(CURRENT_MIN_XP);
        this.testUser = user;

        when(levelDao.getByNumber(2)).thenReturn(nextLevel);
    }

    @Test
    public void testWhenMaxXpLevelUp() {
        userLevelService.addXP(testUser, CURRENT_MAX_XP);
        assertEquals(nextLevel.getNumber(), testUser.getLevel().getNumber());
    }

    @Test
    public void testWhenNextLevelMinXpLevelUp() {
        userLevelService.addXP(testUser, NEXT_MIN_XP);
        assertEquals(nextLevel.getNumber(), testUser.getLevel().getNumber());
    }

    @Test
    public void testWhenLessThanMaxXpNoLevelUp() {
        userLevelService.addXP(testUser, CURRENT_MAX_XP - 1);
        assertEquals(currentLevel.getNumber(), testUser.getLevel().getNumber());
    }

}
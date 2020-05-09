package com.es.core.dao;

public final class DbContract {
    private DbContract() {
    }

    public static class UsersTable {
        public static final String TABLE = "users";
        public static final String USERNAME = "username";
        public static final String ENABLED = "enabled";
        public static final String LEVEL = "level";
        public static final String XP = "xp";
    }

    public static class DiscountsTable {
        public static final String TABLE = "discounts";
        public static final String ID = "id";
        public static final String VALUE = "value";
        public static final String VALUE_TYPE = "valueType";
        public static final String APPLICABLE_FOR = "applicableFor";
        public static final String ENABLED = "enabled";
    }

    // TODO: check if needed
//    public static class Discounts2UserLevelTable {
//        public static final String TABLE = "discounts2userLevel";
//        public static final String DISCOUNT_ID = "discountId";
//        public static final String USER_LEVEL = "userLevel";
//    }
}

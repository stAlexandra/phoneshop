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

    public static class BooksTable {
        public static final String TABLE = "discounts";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String IMAGE_URL = "imageUrl";
        public static final String PRICE = "price";
        public static final String AUTHOR = "author";
        public static final String GENRE = "genre";
        public static final String DATE_OF_PUBLISHING = "dateOfPublishing";
        public static final String PUBLISHER = "publisher";
        public static final String PAGE_COUNT = "pageCount";
    }
    
    // TODO: check if needed
//    public static class Discounts2UserLevelTable {
//        public static final String TABLE = "discounts2userLevel";
//        public static final String DISCOUNT_ID = "discountId";
//        public static final String USER_LEVEL = "userLevel";
//    }
}

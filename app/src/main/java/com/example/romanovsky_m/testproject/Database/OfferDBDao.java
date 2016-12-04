package com.example.romanovsky_m.testproject.Database;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "OFFER_DB".
*/
public class OfferDBDao extends AbstractDao<OfferDB, Long> {

    public static final String TABLENAME = "OFFER_DB";

    /**
     * Properties of entity OfferDB.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "ID", true, "_id");
        public final static Property NAME = new Property(1, String.class, "NAME", false, "NAME");
        public final static Property URL = new Property(2, String.class, "URL", false, "URL");
        public final static Property PRICE = new Property(3, Double.class, "PRICE", false, "PRICE");
        public final static Property DESCRIPTION = new Property(4, String.class, "DESCRIPTION", false, "DESCRIPTION");
        public final static Property PICTURE_URL = new Property(5, String.class, "PICTURE_URL", false, "PICTURE__URL");
        public final static Property NUMBER = new Property(6, Integer.class, "NUMBER", false, "NUMBER");
        public final static Property CATEGORY_ID = new Property(7, Long.class, "CATEGORY_ID", false, "CATEGORY__ID");
    }

    private DaoSession daoSession;

    private Query<OfferDB> categoryDB_MOffersListQuery;

    public OfferDBDao(DaoConfig config) {
        super(config);
    }
    
    public OfferDBDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"OFFER_DB\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: ID
                "\"NAME\" TEXT," + // 1: NAME
                "\"URL\" TEXT," + // 2: URL
                "\"PRICE\" REAL," + // 3: PRICE
                "\"DESCRIPTION\" TEXT," + // 4: DESCRIPTION
                "\"PICTURE__URL\" TEXT," + // 5: PICTURE_URL
                "\"NUMBER\" INTEGER," + // 6: NUMBER
                "\"CATEGORY__ID\" INTEGER NOT NULL );"); // 7: CATEGORY_ID
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"OFFER_DB\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, OfferDB entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        String NAME = entity.getNAME();
        if (NAME != null) {
            stmt.bindString(2, NAME);
        }
 
        String URL = entity.getURL();
        if (URL != null) {
            stmt.bindString(3, URL);
        }
 
        Double PRICE = entity.getPRICE();
        if (PRICE != null) {
            stmt.bindDouble(4, PRICE);
        }
 
        String DESCRIPTION = entity.getDESCRIPTION();
        if (DESCRIPTION != null) {
            stmt.bindString(5, DESCRIPTION);
        }
 
        String PICTURE_URL = entity.getPICTURE_URL();
        if (PICTURE_URL != null) {
            stmt.bindString(6, PICTURE_URL);
        }
 
        Integer NUMBER = entity.getNUMBER();
        if (NUMBER != null) {
            stmt.bindLong(7, NUMBER);
        }
        stmt.bindLong(8, entity.getCATEGORY_ID());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, OfferDB entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        String NAME = entity.getNAME();
        if (NAME != null) {
            stmt.bindString(2, NAME);
        }
 
        String URL = entity.getURL();
        if (URL != null) {
            stmt.bindString(3, URL);
        }
 
        Double PRICE = entity.getPRICE();
        if (PRICE != null) {
            stmt.bindDouble(4, PRICE);
        }
 
        String DESCRIPTION = entity.getDESCRIPTION();
        if (DESCRIPTION != null) {
            stmt.bindString(5, DESCRIPTION);
        }
 
        String PICTURE_URL = entity.getPICTURE_URL();
        if (PICTURE_URL != null) {
            stmt.bindString(6, PICTURE_URL);
        }
 
        Integer NUMBER = entity.getNUMBER();
        if (NUMBER != null) {
            stmt.bindLong(7, NUMBER);
        }
        stmt.bindLong(8, entity.getCATEGORY_ID());
    }

    @Override
    protected final void attachEntity(OfferDB entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public OfferDB readEntity(Cursor cursor, int offset) {
        OfferDB entity = new OfferDB( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // NAME
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // URL
            cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3), // PRICE
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // DESCRIPTION
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // PICTURE_URL
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // NUMBER
            cursor.getLong(offset + 7) // CATEGORY_ID
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, OfferDB entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNAME(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setURL(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPRICE(cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3));
        entity.setDESCRIPTION(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPICTURE_URL(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setNUMBER(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setCATEGORY_ID(cursor.getLong(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(OfferDB entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(OfferDB entity) {
        if(entity != null) {
            return entity.getID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(OfferDB entity) {
        return entity.getID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "mOffersList" to-many relationship of CategoryDB. */
    public List<OfferDB> _queryCategoryDB_MOffersList(Long CATEGORY_ID) {
        synchronized (this) {
            if (categoryDB_MOffersListQuery == null) {
                QueryBuilder<OfferDB> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.CATEGORY_ID.eq(null));
                categoryDB_MOffersListQuery = queryBuilder.build();
            }
        }
        Query<OfferDB> query = categoryDB_MOffersListQuery.forCurrentThread();
        query.setParameter(0, CATEGORY_ID);
        return query.list();
    }

}

package com.zhangyp.develop.HappyLittleBook.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zhangyp.develop.HappyLittleBook.bean.IncomeLevelOneCate;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "INCOME_LEVEL_ONE_CATE".
*/
public class IncomeLevelOneCateDao extends AbstractDao<IncomeLevelOneCate, Long> {

    public static final String TABLENAME = "INCOME_LEVEL_ONE_CATE";

    /**
     * Properties of entity IncomeLevelOneCate.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CateName = new Property(1, String.class, "cateName", false, "CATE_NAME");
    }


    public IncomeLevelOneCateDao(DaoConfig config) {
        super(config);
    }
    
    public IncomeLevelOneCateDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"INCOME_LEVEL_ONE_CATE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CATE_NAME\" TEXT);"); // 1: cateName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"INCOME_LEVEL_ONE_CATE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, IncomeLevelOneCate entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String cateName = entity.getCateName();
        if (cateName != null) {
            stmt.bindString(2, cateName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, IncomeLevelOneCate entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String cateName = entity.getCateName();
        if (cateName != null) {
            stmt.bindString(2, cateName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public IncomeLevelOneCate readEntity(Cursor cursor, int offset) {
        IncomeLevelOneCate entity = new IncomeLevelOneCate( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // cateName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, IncomeLevelOneCate entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCateName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(IncomeLevelOneCate entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(IncomeLevelOneCate entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(IncomeLevelOneCate entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

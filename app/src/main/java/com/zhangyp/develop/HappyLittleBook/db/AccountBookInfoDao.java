package com.zhangyp.develop.HappyLittleBook.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zhangyp.develop.HappyLittleBook.bean.AccountBookInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ACCOUNT_BOOK_INFO".
*/
public class AccountBookInfoDao extends AbstractDao<AccountBookInfo, Long> {

    public static final String TABLENAME = "ACCOUNT_BOOK_INFO";

    /**
     * Properties of entity AccountBookInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Money = new Property(1, double.class, "money", false, "MONEY");
        public final static Property TimeStr = new Property(2, String.class, "timeStr", false, "TIME_STR");
        public final static Property NoteStr = new Property(3, String.class, "noteStr", false, "NOTE_STR");
        public final static Property CateStr = new Property(4, String.class, "cateStr", false, "CATE_STR");
        public final static Property WalletType = new Property(5, String.class, "walletType", false, "WALLET_TYPE");
        public final static Property BookType = new Property(6, int.class, "bookType", false, "BOOK_TYPE");
    }


    public AccountBookInfoDao(DaoConfig config) {
        super(config);
    }
    
    public AccountBookInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ACCOUNT_BOOK_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"MONEY\" REAL NOT NULL ," + // 1: money
                "\"TIME_STR\" TEXT," + // 2: timeStr
                "\"NOTE_STR\" TEXT," + // 3: noteStr
                "\"CATE_STR\" TEXT," + // 4: cateStr
                "\"WALLET_TYPE\" TEXT," + // 5: walletType
                "\"BOOK_TYPE\" INTEGER NOT NULL );"); // 6: bookType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ACCOUNT_BOOK_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AccountBookInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getMoney());
 
        String timeStr = entity.getTimeStr();
        if (timeStr != null) {
            stmt.bindString(3, timeStr);
        }
 
        String noteStr = entity.getNoteStr();
        if (noteStr != null) {
            stmt.bindString(4, noteStr);
        }
 
        String cateStr = entity.getCateStr();
        if (cateStr != null) {
            stmt.bindString(5, cateStr);
        }
 
        String walletType = entity.getWalletType();
        if (walletType != null) {
            stmt.bindString(6, walletType);
        }
        stmt.bindLong(7, entity.getBookType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AccountBookInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getMoney());
 
        String timeStr = entity.getTimeStr();
        if (timeStr != null) {
            stmt.bindString(3, timeStr);
        }
 
        String noteStr = entity.getNoteStr();
        if (noteStr != null) {
            stmt.bindString(4, noteStr);
        }
 
        String cateStr = entity.getCateStr();
        if (cateStr != null) {
            stmt.bindString(5, cateStr);
        }
 
        String walletType = entity.getWalletType();
        if (walletType != null) {
            stmt.bindString(6, walletType);
        }
        stmt.bindLong(7, entity.getBookType());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AccountBookInfo readEntity(Cursor cursor, int offset) {
        AccountBookInfo entity = new AccountBookInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getDouble(offset + 1), // money
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // timeStr
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // noteStr
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // cateStr
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // walletType
            cursor.getInt(offset + 6) // bookType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AccountBookInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMoney(cursor.getDouble(offset + 1));
        entity.setTimeStr(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNoteStr(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCateStr(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setWalletType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBookType(cursor.getInt(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AccountBookInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AccountBookInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AccountBookInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

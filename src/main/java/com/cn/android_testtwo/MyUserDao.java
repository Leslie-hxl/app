package com.cn.android_testtwo;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MY_USER".
*/
public class MyUserDao extends AbstractDao<MyUser, Long> {

    public static final String TABLENAME = "MY_USER";

    /**
     * Properties of entity MyUser.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property SongName = new Property(1, String.class, "songName", false, "SONG_NAME");
        public final static Property SongId = new Property(2, Integer.class, "songId", false, "SONG_ID");
    }


    public MyUserDao(DaoConfig config) {
        super(config);
    }
    
    public MyUserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MY_USER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"SONG_NAME\" TEXT," + // 1: songName
                "\"SONG_ID\" INTEGER);"); // 2: songId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MY_USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MyUser entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String songName = entity.getSongName();
        if (songName != null) {
            stmt.bindString(2, songName);
        }
 
        Integer songId = entity.getSongId();
        if (songId != null) {
            stmt.bindLong(3, songId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MyUser entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String songName = entity.getSongName();
        if (songName != null) {
            stmt.bindString(2, songName);
        }
 
        Integer songId = entity.getSongId();
        if (songId != null) {
            stmt.bindLong(3, songId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MyUser readEntity(Cursor cursor, int offset) {
        MyUser entity = new MyUser( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // songName
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2) // songId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MyUser entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSongName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSongId(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MyUser entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MyUser entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MyUser entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

package network.rembo.rss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news";
    public static final String TABLE_NEWS = "news";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_PUBDATE = "pubdate";

    public DBHandler(Context context, String name, CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_NEWS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_LINK + " TEXT," +
                COLUMN_DESC + " TEXT," +
                COLUMN_PUBDATE + " TEXT" +
                ");";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        onCreate(db);
    }

    public void addNews(Application application) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * from " + TABLE_NEWS + " where " + COLUMN_LINK + " = \"" + application.getLink() + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, application.getTitle());
            values.put(COLUMN_LINK, application.getLink());
            values.put(COLUMN_DESC, application.getDescription());
            values.put(COLUMN_PUBDATE, application.getPubDate());

            db.insert(TABLE_NEWS, null, values);
            db.close();
        }
    }

    public void deleteNews(Application application) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NEWS + " WHERE " + COLUMN_LINK + " = \"" + application.getLink() + "\"";
        db.execSQL(query);
    }

    public ArrayList<Application> getData() {
        ArrayList<Application> Data = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NEWS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            Application curApp = new Application();
            if (c.getString(c.getColumnIndex(COLUMN_TITLE)) != null){
                curApp.setTitle(c.getString(c.getColumnIndex(COLUMN_TITLE)));
            }
            if (c.getString(c.getColumnIndex(COLUMN_LINK)) != null){
                curApp.setLink(c.getString(c.getColumnIndex(COLUMN_LINK)));
            }
            if (c.getString(c.getColumnIndex(COLUMN_DESC)) != null){
                curApp.setDescription(c.getString(c.getColumnIndex(COLUMN_DESC)));
            }
            if (c.getString(c.getColumnIndex(COLUMN_PUBDATE)) != null){
                curApp.setPubDate(c.getString(c.getColumnIndex(COLUMN_PUBDATE)));
            }
            Data.add(curApp);
            c.moveToNext();
        }

        return Data;
    }
    public void emptyDB(){
        String query = "DELETE FROM " + TABLE_NEWS + "WHERE 1";
        SQLiteDatabase db = getWritableDatabase();
        Log.d("DBHANDLER ==> ", "here");
        db.execSQL("DELETE FROM "+ TABLE_NEWS);
        //db.execSQL("TRUNCATE table " + TABLE_NEWS);
        Log.d("DBHANDLER ==> ", "@");
        db.close();
    }
}

package invenz.roy.ngtc.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import invenz.roy.ngtc.models.MyItem;

public class MySqliteDbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "item_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "items_table";


    /*###               Table columns               ##*/
    private static final String ID = "id";
    private static final String ITEM_NAME = "name";
    private static final String ITEM_IMAGE = "image";

    /*###               Queries       ###*/

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +
                                                "("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ITEM_NAME+" VARCHAR, "+ITEM_IMAGE+" BLOB)  ";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String RETRIEVE_QUERY = "SELECT * FROM "+TABLE_NAME;



    public MySqliteDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }


    /*######               Inserting               ######*/
    public int insertItem(MyItem myItem) {


        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME, myItem.getItemName());
        contentValues.put(ITEM_IMAGE, myItem.getImageByte());

        int id = (int) sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return id;
    }




    /*####                   Fetching                  ###*/
    public List<MyItem> fetcheAllItems(){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(RETRIEVE_QUERY, null);

        List<MyItem> myItemList = new ArrayList<>();

        if (cursor!=null){
            if (cursor.moveToFirst()){
                do{

                    MyItem myItem = new MyItem();
                    myItem.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                    myItem.setItemName(cursor.getString(cursor.getColumnIndex(ITEM_NAME)));
                    myItem.setImageByte(cursor.getBlob(cursor.getColumnIndex(ITEM_IMAGE)));

                    myItemList.add(myItem);
                }while (cursor.moveToNext());
            }
        }


        return myItemList;

    }


    public int deleteItem(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME, ID+"=?", new String[]{String.valueOf(id)});

        return i;
    }
}

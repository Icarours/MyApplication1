package com.syl.myapplication1aid.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.syl.myapplication1aid.db.BankDbOpenHelper;

/**
 * 内容提供者,向外部APP提供本APP数据库的访问
 */
public class BankContentProvider extends ContentProvider {
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CODE_ACCOUNT = 10;
    private static final String TAG = BankContentProvider.class.getSimpleName();

    static {
        sURIMatcher.addURI("com.syl.myapplication1aid", "account", CODE_ACCOUNT);
    }

    public BankContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG,"delete()...");
        int account;
        if (sURIMatcher.match(uri) == CODE_ACCOUNT) {
            BankDbOpenHelper helper = new BankDbOpenHelper(getContext());
            SQLiteDatabase database = helper.getWritableDatabase();
            account = database.delete("account", selection, selectionArgs);
            /**
             * 参数一 ： 具体哪一个数据发生了变化.
             *
             * 参数二： 具体的观察者，如果给的null ,那么并没有具体哪一个观察者会收到信息，
             * 当然如果有观察者一直观察着这个位置的数据，那么它也能收到通知。
             */
            getContext().getContentResolver().notifyChange(uri,null);
        } else {
            throw new IllegalArgumentException("口令错误..");
        }
        return account;
    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG,"getType()...");
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG,"insert()...");
        // content://com.syl.myapplication1aid/account
        if (sURIMatcher.match(uri) == CODE_ACCOUNT) {
            BankDbOpenHelper helper = new BankDbOpenHelper(getContext());
            SQLiteDatabase database = helper.getWritableDatabase();
            database.insert("account", null, values);
            database.close();
            /**
             * 参数一 ： 具体哪一个数据发生了变化.
             *
             * 参数二： 具体的观察者，如果给的null ,那么并没有具体哪一个观察者会收到信息，
             * 当然如果有观察者一直观察着这个位置的数据，那么它也能收到通知。
             */
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new IllegalArgumentException("口令错误..");
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG,"onCreate()...");
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(TAG,"query()...");
        Cursor cursor;
        if (sURIMatcher.match(uri) == CODE_ACCOUNT) {
            BankDbOpenHelper helper = new BankDbOpenHelper(getContext());
            SQLiteDatabase database = helper.getWritableDatabase();
            cursor = database.query("account", projection, selection, selectionArgs, null, null, sortOrder);
            //这地方不能关闭资源,如果关闭,后面的cursor返回的就会使空
//            cursor.close();
//            database.close();
            /**
             * 参数一 ： 具体哪一个数据发生了变化.
             *
             * 参数二： 具体的观察者，如果给的null ,那么并没有具体哪一个观察者会收到信息，
             * 当然如果有观察者一直观察着这个位置的数据，那么它也能收到通知。
             */
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new IllegalArgumentException("口令错误..");
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d(TAG,"update()...");
        int account;
        if (sURIMatcher.match(uri) == CODE_ACCOUNT) {
            BankDbOpenHelper helper = new BankDbOpenHelper(getContext());
            SQLiteDatabase database = helper.getWritableDatabase();
            account = database.update("account", values, selection, selectionArgs);
            database.close();
            /**
             * 参数一 ： 具体哪一个数据发生了变化.
             *
             * 参数二： 具体的观察者，如果给的null ,那么并没有具体哪一个观察者会收到信息，
             * 当然如果有观察者一直观察着这个位置的数据，那么它也能收到通知。
             */
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new IllegalArgumentException("口令错误..");
        }
        return account;
    }
}

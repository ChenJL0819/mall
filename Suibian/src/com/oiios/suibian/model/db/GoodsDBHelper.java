package com.oiios.suibian.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GoodsDBHelper extends SQLiteOpenHelper {
	private static GoodsDBHelper dbHelper;
	
	private GoodsDBHelper(Context context) {
		super(context, "suibian.db", null, 1);
	}
	public synchronized static GoodsDBHelper getInstance(Context context){
		if (dbHelper == null) {
			dbHelper = new GoodsDBHelper(context);
		}
		return dbHelper;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql1 = "create table if not exists goods ("
				+ "_id integer primary key autoincrement," 
				+ "store_id integer not null,"
				+ "name varchar(30),"
				+ "icon blob,"
				+ "photo blob,"
				+ "old_price decimal,"
				+ "now_price decimal not null,"
				+ "sell_count int,"
				+ "freight decimal,"
				+ "describe varchar(60),"//��Ʒ����
				+ "type int,"//��Ʒ����
				+ "color text,"//��Ʒ��ɫ
				+ "size text,"//��Ʒ�ߴ�
				+ "foreign key(store_id) references store(_id),"
				+ "foreign key(_id) references shopping_car(goods_id),"
				+ "foreign key(_id) references comment(goods_id))";
		//���ﳵ
		String sql2 = "create table if not exists shopping_car("
				+ "_id integer primary key autoincrement,"
				+ "goods_id integer not null,"
				+ "goods_count int not null,"
				+ "phone varchar(11) not null)";
		//����
		String sql3 = "create table if not exists store("
				+ "_id integer primary key autoincrement,"
				+ "name varchar(30) not null,"
				+ "photo blob,"
				+ "address varchar(40))";
		//��Ʒ����
		String sql4 = "create table if not exists type("
				+ "_id integer primary key autoincrement,"
				+ "type_name varchar(20))";
		//����
		String sql5 = "create table if not exists comment("
				+ "_id integer primary key autoincrement,"
				+ "goods_id integer,"//��Ʒid
				+ "comment_level int,"//���۵ĵȼ�
				+ "comment_content text,"//���۵�����
				+ "username varchar(20))";
		//�û�
		String sql6 = "create table if not exists user("
				+ "_id integer primary key autoincrement,"
				+ "nickname varchar(20),"
				+ "phone varchar(11) not null,"//�绰������Ϊ�˺�
				+ "password varchar(16) not null,"
				+ "email text,"
				+ "foreign key(phone) references shopping_car(phone))";
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
		db.execSQL(sql5);
		db.execSQL(sql6);
		
		
		String sql7 = "create table if not exists category("
				+ "_id integer primary key autoincrement,"
				+ "name text,"
				+ "url text)";
		db.execSQL(sql7);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
	
	}

}

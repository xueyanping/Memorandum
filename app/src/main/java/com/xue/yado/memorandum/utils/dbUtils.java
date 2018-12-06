package com.xue.yado.memorandum.utils;

import android.content.ContentValues;
import android.util.Log;

import com.xue.yado.memorandum.entity.Memoire;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * 数据库相关
 * Created by Administrator on 2018/11/29.
 */


public class dbUtils {

    public static void createDatabase(){
        Connector.getDatabase();
    }

    public static boolean saveOrUpdateData(Memoire memoire){
         boolean isSaved = memoire.isSaved();
        boolean save =  false;
        int num = -1;
        if(!isSaved){
            save = memoire.save();
            AppCache.getMemireList().add(memoire);
        }else{
           // Log.i( "saveOrUpdateData: ","tag=="+memoire.getTag()+"");
            num = memoire.updateAll("tag = ?", memoire.getTag() + "");
        }
        if(save || num > 0 ){
            return true;
        }else {
            return false;
        }

    }

    public static List<Memoire> findAllData(Class clazz){
        List<Memoire> list = DataSupport.findAll(clazz);
        AppCache.setMemireList(list);
        return list;
    }

    public static List<Memoire> findData(Class clazz,String selectSql,String whereSql,String whereSql2/*,String orderSql*/){
        List<Memoire> memoireList = DataSupport.select(selectSql)
                                             .where(whereSql,whereSql2)
                                            // .limit(limitNum)
                                            // .order(orderSql)
                                             .find(clazz);
        return memoireList;
    }

    public static List<Memoire> findSearchData(Class clazz,String whereSql,String whereSql2){
        List<Memoire> memoireList = DataSupport.where(whereSql,whereSql2).find(clazz);
        return memoireList;
    }

    public static int deleteData(Class clazz,String... sql){
        int deleteCount = DataSupport.deleteAll(clazz, sql);
        return deleteCount;
    }

    public static int updateData(Class clazz, ContentValues values, String... sql){
        int updateCount = DataSupport.updateAll(clazz, values, sql);
        return updateCount;
    }

}

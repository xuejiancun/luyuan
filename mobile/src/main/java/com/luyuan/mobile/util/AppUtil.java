/**
 * 
 */
package com.luyuan.mobile.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.WindowManager;

import com.luyuan.mobile.model.User;

public class AppUtil {
    
	/**
     * 获取屏幕分辨率
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
		int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
		int result[] = { width, height };
		return result;
	}
    public static void changeStatus(Context context,String lockPwd){
        SharedPreferences sharedPreferences = context.getSharedPreferences("lockInfo", Context.MODE_APPEND);
        String status =sharedPreferences.getString("status", "");//status:on;off 开关
        SharedPreferences.Editor editor = sharedPreferences.edit();
        User user= MyGlobal.getUser();
        editor.putString("sob", user.getSob());
        editor.putString("username", user.getUsername());
        editor.putString("pwd", user.getPassword());
        editor.putString("lockPwd",lockPwd);
        editor.putString("status", "on");
        editor.commit();
    }



    //得到用户的手势密码
    public static String  getPwd(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("lockInfo", Context.MODE_APPEND);
        String lockPwd =sharedPreferences.getString("lockPwd", "");//status:on;off 开关
        return lockPwd;


    }
}

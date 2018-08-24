package com.acteur.admin.utilslibrary.ToolUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ${Riven} on ${rabbit}.
 */
public class SynUtils {
    /**
     * 检查当前网络是否可用
     *
     * @param
     * @return
     */
    public static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）/^0?1[3|4|5|7|8][0-9]\d{8}$/
		总结起来就是第一位必定为1，第二位必定为3或5或8或7（电信运营商），其他位置的可以为0-9
		*/
        String telRegex = "[1]\\d{10}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);

//        String telRegex = "[1][345789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
//        if (TextUtils.isEmpty(mobiles))
//            return false;
//        else
//            return mobiles.matches(telRegex);
    }
    /**
     * 邮箱验证
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }
    /**
     * 保存图片
     */
    public static Boolean saveBitmap(String picName, Bitmap bm, Context context) {
        Log.e("rabbit", "保存图片");
        if (picName == null) {
            picName = "rabbit" + System.currentTimeMillis() + ".png";
        }
        File f = new File(Environment.getExternalStorageDirectory().getPath()
                + "/" + picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i("rabbit", "已经保存" + picName);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(f);
            intent.setData(uri);
            context.sendBroadcast(intent);
            Log.i("rabbit", "已经刷新" + picName);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 横向的自定义分割线(listview类recycleview)
     *
     * @param mContext
     * @param color
     * @param height
     * @return
     */
    public static RecycleViewDivider getVERTICALLineDivider(Context mContext, int color, int height) {
        return new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, DensityUtils.dp2px(mContext, height), mContext.getResources().getColor(color));
    }

    /**
     * 纵向的自定义分割线(listview类recycleview)
     *
     * @param mContext
     * @param color
     * @param height
     * @return
     */
    public static RecycleViewDivider getHORIZONTALLineDivider(Context mContext, int color, int height) {
        return new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, DensityUtils.dp2px(mContext, height), mContext.getResources().getColor(color));
    }
}

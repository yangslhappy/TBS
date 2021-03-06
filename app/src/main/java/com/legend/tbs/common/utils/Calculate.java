package com.legend.tbs.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.legend.tbs.R;

import java.util.HashMap;

/**
 * @author Legend
 * @data by on 2018/4/6.
 * @description
 */
//function o() {
//        for (var t = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : "", e = 5381, n = 0, r = t.length; n < r; ++n)
//        e += (e << 5) + t.charAt(n).charCodeAt();
//        return 2147483647 & e
//        }
//        console.log(o('@WDFlsZeEG'));

public class Calculate {

    private static final String[] words = new String[]
            {"oe", "n", "z",
                    "oK", "6", "5",
                    "ow", "-", "A",
                    "oi", "o", "i",
                    "7e", "v", "P",
                    "7K", "4", "k",
                    "7w", "C", "s",
                    "7i", "S", "l",
                    "Ne", "c", "F",
                    "NK", "E", "q"};

    private static HashMap<String, Integer>[] maps = new HashMap[3];

    static {
        for (int Dici = 0; Dici < 3; Dici++) {
            maps[Dici] = new HashMap<>();
            for (int Wordi = 0; Wordi < 10; Wordi++) {
                maps[Dici].put(words[Dici + 3 * Wordi], Wordi);
            }
        }
        maps[0].put("on",0);
        maps[0].put("ov",1);
        maps[0].put("oc",2);
        maps[0].put("oz",3);
        maps[0].put("7n",4);
        maps[0].put("7v",5);
        maps[0].put("7c",6);
        maps[0].put("7z",7);
        maps[0].put("Nn",8);
        maps[0].put("Nv",9);
    }

    // 解密
    public static String decode(String str) {
        StringBuilder sb = new StringBuilder();
        char[] chars = str.substring(4, str.length()).toCharArray();
        int flag = 0;
        for (int i = 0; i < chars.length; i++) {
            String key;
            if (flag == 0)
            {
                key = String.valueOf(chars[i]) + chars[i + 1];
                i++;
            } else {
                key = String.valueOf(chars[i]);
            }
            if(maps[flag].containsKey(key)){
                sb.append(maps[flag].get(key));
            }else{
                sb.append("0");
            }
            flag = (flag < 2) ? flag + 1 : 0;
        }
        return sb.toString();
    }

    public static Dialog createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = v.findViewById(R.id.img);
        TextView tipTextView = v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
        return loadingDialog;
    }

    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}

package com.zhangyp.develop.HappyLittleBook.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.zhangyp.develop.HappyLittleBook.BuildConfig;

import java.io.File;

/**
 * Created by v on 2019/8/14.
 */
public class InstallApkUtil {


    /**
     * 安装apk
     *
     * @param context
     * @param apkFile
     */
    public static void installApk(Context context, File apkFile) {
        if (!apkFile.exists()) {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            Uri apkUri = null;
//            //判断版本是否是 7.0 及 7.0 以上
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
//                //添加对目标应用临时授权该Uri所代表的文件
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            } else {
//                apkUri = Uri.fromFile(apkFile);
//            }
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//            //查询所有符合 intent 跳转目标应用类型的应用，注意此方法必须放置setDataAndType的方法之后
//            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//            //然后全部授权
//            for (ResolveInfo resolveInfo : resInfoList) {
//                String packageName = resolveInfo.activityInfo.packageName;
//                context.grantUriPermission(packageName, apkUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            }
//            context.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 通过Intent安装APK文件
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, FileProviderName, apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);

//        System.exit(0);
    }

    public static String FileProviderName = "com.zhangyp.develop.HappyLittleBook.fileProvider";


    /**
     * 卸载
     */
    public static void uninstallApk(Context context) {
        Uri uri = Uri.parse("package:com.zhangyp.develop.HappyLittleBook");
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        context.startActivity(intent);
    }
}

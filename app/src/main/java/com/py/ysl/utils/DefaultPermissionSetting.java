package com.py.ysl.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.py.ysl.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.SettingService;

import java.util.List;

public class DefaultPermissionSetting {
    private final Context mContext;

    public DefaultPermissionSetting(Context context) {
        this.mContext = context;
    }

    public void showSetting(final List<String> permissions,String title) {
        List<String> permissionNames = Permission.transformText(mContext, permissions);
        final SettingService settingService = AndPermission.permissionSetting(mContext);
        new AlertDialog.Builder(mContext)
                .setMessage(title)
                .setPositiveButton(R.string.button_allow,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        settingService.execute();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        settingService.cancel();
                    }
                })
                .show();
    }
}

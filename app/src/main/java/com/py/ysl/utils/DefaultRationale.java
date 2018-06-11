package com.py.ysl.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.py.ysl.R;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

public class DefaultRationale implements Rationale{
    @Override
    public void showRationale(Context context, List<String> permissions,final RequestExecutor executor) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.permission_write_rationale)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        executor.execute();
                    }
                })
                .setNegativeButton(R.string.button_deny,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        executor.cancel();
                    }
                })
                .show();
    }
}

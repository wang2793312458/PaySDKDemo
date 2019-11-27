package com.example.testpaysdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.icbc.paysdk.constants.Constants;

import java.util.ArrayList;

/**
 * Created by kfzx-xuyh on 2017/9/30.
 */

//Demo入口activity，用于展示地址列表和选择接入方式

public class PayEntryActivity extends Activity implements View.OnClickListener{

    Button btn_icbcpay;
    Button btn_wxpay;
    Button btn_alipay_test;
    Button btn_alipay_release;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sdkmode);

         btn_icbcpay = (Button) findViewById(R.id.icbcpay);
         btn_wxpay = (Button)findViewById(R.id.wxpay);
         btn_alipay_test = (Button)findViewById(R.id.alipay_test);
         btn_alipay_release = (Button)findViewById(R.id.alipay_release);

         btn_icbcpay.setOnClickListener(this);
         btn_wxpay.setOnClickListener(this);
         btn_alipay_test.setOnClickListener(this);
         btn_alipay_release.setOnClickListener(this);

        AddressDialog2().show();

    }

    @Override
    public void onClick(View view) {
         Intent intent=new Intent(PayEntryActivity.this,MainActivity.class);
        switch (view.getId()){

            case R.id.icbcpay:
                intent.putExtra("flag","1");
                startActivity(intent);
                break;
            case R.id.wxpay:
                intent.putExtra("flag","2");
                startActivity(intent);
                break;
            case R.id.alipay_test:
                intent.putExtra("flag","3");
                startActivity(intent);
                break;
            case R.id.alipay_release:
                intent.putExtra("flag","4");
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    private String testServerURL = "";
    private String[] arrs = null;

    private AlertDialog AddressDialog2() {

        arrs = getResources().getStringArray(R.array.TestServerIP);

        final ArrayList<String> arrsDisplay = new ArrayList<String>();
        for (String s : arrs) {

            if (s.length() >0)
                arrsDisplay.add("支付平台地址:\n" + s);
            else
                arrsDisplay.add("手工添加一个");

        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(PayEntryActivity.this);
//			dialog.setIcon(R.drawable.small_icon);
        dialog.setTitle("测试环境地址");
        dialog.setSingleChoiceItems(arrsDisplay.toArray(new String[arrsDisplay.size()]), 0, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0)
                    testServerURL = "";
                else
                    testServerURL = arrs[which];
            }
        });
        dialog.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if ("".equals(testServerURL)) {
                    AddressDialog1().show();
                } else {
                    Constants.PAY_LIST_IP = testServerURL.trim();
                }
                arg0.dismiss();
            }
        });
        return dialog.create();
    }

    private AlertDialog AddressDialog1() {
        AlertDialog.Builder MessageDialog = null;
        MessageDialog = new AlertDialog.Builder(PayEntryActivity.this);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) layoutInflater.inflate(R.layout.dialog_edittext, null);
        final EditText input1 = (EditText) view.findViewById(R.id.edittext1);
        input1.setText("http://");
        MessageDialog.setView(view);
        MessageDialog.setTitle("测试环境地址");
        MessageDialog.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String value1 = input1.getText().toString();
                Constants.PAY_LIST_IP = value1.trim();
                arg0.dismiss();
            }
        });
        return MessageDialog.create();
    }


}

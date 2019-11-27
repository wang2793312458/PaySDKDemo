package com.example.testpaysdk.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.testpaysdk.R;
import com.icbc.paysdk.WXPayAPI;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by kfzx-xuyh on 2017/10/13.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    TextView result_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result_handler_layout);
        result_text = (TextView) findViewById(R.id.pay_result);
        if (WXPayAPI.getInstance() != null) {
            WXPayAPI.getInstance().getWXApi().handleIntent(getIntent(), this);
        } else {
            finish();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (WXPayAPI.getInstance() != null) {
            WXPayAPI.getInstance().getWXApi().handleIntent(intent, this);
        }
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            switch (baseResp.errCode) {
                case 0:
                    result_text.setText("支付成功");
                    break;
                case -1:
                    result_text.setText("支付失败");
                    break;
                case -2:
                    result_text.setText("支付取消");
                    break;
                default:
                    break;
            }

        }

    }

}

package com.example.testpaysdk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.icbc.paysdk.model.PayReq;
import com.icbc.paysdk.model.ShopInfo;
import com.icbc.paysdk.model.ThirdPayReq;
import com.icbc.paysdk.services.ReqManager;


 //测试工行收银台，如需调用工行收银台，数据接入请参考此Activity



public class TestMultiPayActivity extends Activity {

    EditText edit_interfaceName;
    EditText edit_interfaceVersion;

    EditText edit_shopCode; //工行商户代码

    EditText edit_shopName; //店铺名称
    EditText edit_totalAmount;//订单金额


    //接工银e支付数据

    EditText edit_tranData1;
    EditText edit_MerSignMsg1;
    EditText edit_MerCert1;

    //接微信/支付宝数据

    EditText edit_WXappid; //非必输项
    EditText edit_tranData2;
    EditText edit_MerSignMsg2;
    EditText edit_MerCert2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_all);

        Button pay_btn = (Button) findViewById(R.id.pay_btn);
        pay_btn.setOnClickListener(pay_btn_ClickListener);

        //工行商户id
        edit_shopCode = (EditText) findViewById(R.id.edit_shopcode);
        edit_shopName=(EditText)findViewById(R.id.edit_shopname);
        edit_totalAmount=(EditText) findViewById(R.id.edit_totalamount);

        //工行数据
        edit_tranData1 = (EditText) findViewById(R.id.edit_tranData1);
        edit_MerSignMsg1 = (EditText) findViewById(R.id.edit_MerSignMsg1);
        edit_MerCert1 = (EditText) findViewById(R.id.edit_MerCert1);

        //微信参数
        edit_WXappid = (EditText) findViewById(R.id.edit_WXappid);

        edit_tranData2 = (EditText) findViewById(R.id.edit_tranData2);
        edit_MerSignMsg2 = (EditText) findViewById(R.id.edit_MerSignMsg2);
        edit_MerCert2 = (EditText) findViewById(R.id.edit_MerCert2);


    }


    private View.OnClickListener pay_btn_ClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO 自动生成的方法存根

            Log.i("pay", "pay_btn");

            String tranData1 = edit_tranData1.getText().toString();
            String merSignMsg1 = edit_MerSignMsg1.getText().toString();
            String merCert1 = edit_MerCert1.getText().toString();

            String tranData2 = edit_tranData2.getText().toString();
            String merSignMsg2 = edit_MerSignMsg2.getText().toString();
            String merCert2 = edit_MerCert2.getText().toString();


            //若支持工银e支付，传入数据

            PayReq req=new PayReq();
            req.setInterfaceName("ICBC_WAPB_THIRD");
            req.setInterfaceVersion("1.0.0.0");
            req.setTranData(tranData1);
            req.setMerCert(merCert1);
            req.setMerSignMsg(merSignMsg1);
            ReqManager.getInstance().setICBCPayReq(req);

            //若接入第三方支付，传入数据
            ThirdPayReq thirdreq=new ThirdPayReq();
            thirdreq.setInterfaceName("ICBC_WAPB_THIRD");
            thirdreq.setInterfaceVersion("1.0.0.0");
            thirdreq.setTranData(tranData2);
            thirdreq.setMerCert(merCert2);
            thirdreq.setMerSignMsg(merSignMsg2);
            ReqManager.getInstance().setThirdPayReq(thirdreq);


            String shopname=edit_shopName.getText().toString();
            String totalamount=edit_totalAmount.getText().toString();
            String shopcode=edit_shopCode.getText().toString();
            String wxappid=edit_WXappid.getText().toString();

            ShopInfo shopinfo=new ShopInfo();
            shopinfo.setShopName(shopname);
            shopinfo.setTotalAmount(totalamount);
            shopinfo.setShopCode(shopcode);
            shopinfo.setWXAppid(wxappid); //选输

            ReqManager.getInstance().handleReq(TestMultiPayActivity.this,shopinfo);



        }

    };


}









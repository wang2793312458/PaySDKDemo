package com.example.testpaysdk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.icbc.paysdk.AliPayAPI;
import com.icbc.paysdk.ICBCAPI;
import com.icbc.paysdk.WXPayAPI;
import com.icbc.paysdk.model.PayReq;
import com.icbc.paysdk.model.ThirdPayReq;


//示例工银e支付/微信支付的接入数据

public class MainActivity extends Activity {

	EditText edit_interfaceName ;
	EditText edit_interfaceVersion;
	EditText edit_tranData;
	EditText edit_MerSignMsg;
	EditText edit_MerCert;
	TextView tv_WXappid;
	EditText edit_WXappid;


	String flag = ""; // flag,1 工银e支付，2,微信支付,3,支付宝沙箱，4，支付宝正式环境

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		Button pay_btn = (Button) findViewById(R.id.pay_btn);
		pay_btn.setOnClickListener(pay_btn_ClickListener);

		edit_interfaceName = (EditText)findViewById(R.id.edit_interfaceName);
		edit_interfaceVersion = (EditText)findViewById(R.id.edit_interfaceVersion);
		edit_tranData = (EditText)findViewById(R.id.edit_tranData);
		edit_MerSignMsg = (EditText)findViewById(R.id.edit_MerSignMsg);
		edit_MerCert = (EditText)findViewById(R.id.edit_MerCert);
		edit_WXappid = (EditText)findViewById(R.id.edit_WXappid);
		tv_WXappid=(TextView)findViewById(R.id.WXappid);

		 flag = getIntent().getStringExtra("flag");
		if("1".equals(flag)){
			tv_WXappid.setVisibility(View.GONE);
			edit_WXappid.setVisibility(View.GONE);
		}else if("2".equals(flag)){
			tv_WXappid.setVisibility(View.VISIBLE);
			edit_WXappid.setVisibility(View.VISIBLE);
		}else if("3".equals(flag)){
			EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
			tv_WXappid.setVisibility(View.GONE);
			edit_WXappid.setVisibility(View.GONE);
		}else if("4".equals(flag)){
			EnvUtils.setEnv(EnvUtils.EnvEnum.ONLINE);
			tv_WXappid.setVisibility(View.GONE);
			edit_WXappid.setVisibility(View.GONE);
		}
									
	}
	
	
	private OnClickListener pay_btn_ClickListener = new View.OnClickListener()
    {

		@Override
		public void onClick(View arg0) {
			// TODO 自动生成的方法存根

			Log.i("pay","pay_btn");

			String tranData = edit_tranData.getText().toString();
			String merSignMsg = edit_MerSignMsg.getText().toString();
			String merCert = edit_MerCert.getText().toString();
			String interfaceName=edit_interfaceName.getText().toString();
			String interfaceVersion=edit_interfaceVersion.getText().toString();

			switch(flag){

				case "1":

					PayReq req = new PayReq();
					req.setInterfaceName("ICBC_WAPB_THIRD");
					req.setInterfaceVersion("1.0.0.0");
					req.setTranData(tranData);
					req.setMerSignMsg(merSignMsg);
					req.setMerCert(merCert);
					ICBCAPI.getInstance().sendReq(MainActivity.this,req);

					break;
				case "2":
					String wx_appid=edit_WXappid.getText().toString();
					WXPayAPI.init(getApplicationContext(),wx_appid); //注册appid
					ThirdPayReq wxreq = new ThirdPayReq();
					wxreq.setInterfaceName("ICBC_WAPB_THIRD");
					wxreq.setInterfaceVersion("1.0.0.0");
					wxreq.setTranData(tranData);
					wxreq.setMerSignMsg(merSignMsg);
					wxreq.setMerCert(merCert);
					wxreq.setClientType("23");
					WXPayAPI.getInstance().doWXPay(MainActivity.this,wxreq);
					break;
				case "3":
					ThirdPayReq alireq=new ThirdPayReq();
					alireq.setInterfaceName("ICBC_WAPB_THIRD");
					alireq.setInterfaceVersion("1.0.0.0");
					alireq.setTranData(tranData);
					alireq.setMerSignMsg(merSignMsg);
					alireq.setMerCert(merCert);
					alireq.setClientType("24");
					AliPayAPI.getInstance().doAliPay2(MainActivity.this, alireq, new AliPayAPI.AliPayResultCallBack() {
						@Override
						public void onResp(String resultcode) {
							if("9000".equals(resultcode)){
								Toast.makeText(MainActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
								//支付成功

							}else if("6001".equals(resultcode)){
								Toast.makeText(MainActivity.this,"支付取消",Toast.LENGTH_SHORT).show();
								//支付取消

							}else{
								Toast.makeText(MainActivity.this,"支付失败",Toast.LENGTH_SHORT).show();
								//支付失败
							}

						}
					});
					break;
				case "4":
					ThirdPayReq alireq2=new ThirdPayReq();
					alireq2.setInterfaceName("ICBC_WAPB_THIRD");
					alireq2.setInterfaceVersion("1.0.0.0");
					alireq2.setTranData(tranData);
					alireq2.setMerSignMsg(merSignMsg);
					alireq2.setMerCert(merCert);
					alireq2.setClientType("24");
					AliPayAPI.getInstance().doAliPay2(MainActivity.this, alireq2, new AliPayAPI.AliPayResultCallBack() {
						@Override
						public void onResp(String resultcode) {
							if("9000".equals(resultcode)){
								Toast.makeText(MainActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
								//支付成功

							}else if("6001".equals(resultcode)){
								Toast.makeText(MainActivity.this,"支付取消",Toast.LENGTH_SHORT).show();
								//支付取消

							}else{
								Toast.makeText(MainActivity.this,"支付失败",Toast.LENGTH_SHORT).show();
								//支付失败
							}

						}
					});
					break;
				default:
					break;
			}

		}

    };



}

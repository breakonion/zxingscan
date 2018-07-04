package com.example.lenovo.complite;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.TextView;
import com.xys.libzxing.zxing.activity.CaptureActivity;



public class SecondActivity extends Activity {
    private TextView scanResult;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        scanResult=(TextView) findViewById(R.id.scanresult);
    }
    public void scan(View view){
        startActivityForResult(new Intent(SecondActivity.this, CaptureActivity.class),0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.i("TAG","resultCode: "+resultCode+" result_ok: "+RESULT_OK);
        if (resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            String result= bundle.getString("result");
            if (Patterns.WEB_URL.matcher(result).matches() || URLUtil.isValidUrl(result))
                { Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(result));
                startActivity(intent);}
            else scanResult.setText(result);
        } if(resultCode == RESULT_CANCELED) {
            scanResult.setText("扫描出错");
        }
    }
}

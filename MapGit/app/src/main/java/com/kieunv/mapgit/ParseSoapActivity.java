package com.kieunv.mapgit;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ParseSoapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_soap);
        new pay().execute();
    }

    class pay extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(ParseSoapActivity.this);
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            final String NAMESPACE = "http://tempuri.org/";
            final String METHOD_NAME = "Login";
            final String SOAP_ACTION = "http://tempuri.org/Login";
            final String URL = "http://203.190.173.22:9002/MobileService.svc?wsdl";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("UserName", "aaaaa");
            request.addProperty("Password", "123456");
//            request.addProperty("sTaxNo", no);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;

            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                String responseJSON = response.toString();
                JSONArray jarray = new JSONArray(responseJSON);

//                TextView tv = (TextView)findViewById(R.id.tvaaaa);
//                tv.setText(jarray.toString());
                System.out.println(jarray.toString());
                System.out.println(request);
                System.out.println(responseJSON);

            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
        }
    }


}

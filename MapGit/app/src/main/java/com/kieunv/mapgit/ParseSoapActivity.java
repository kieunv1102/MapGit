package com.kieunv.mapgit;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ParseSoapActivity extends AppCompatActivity {

    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String METHOD_NAME = "Login";
    private static final String SOAP_ACTION = "http://tempuri.org/IMobileService/Login";
    private static final String URL = "http://203.190.173.22:9002/MobileService.svc?wsdl";
    private String response;
    private TextView txtResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_soap);
        txtResponse = (TextView)findViewById(R.id.txtResponse);
//        new pay().execute();
        new GetObjectService().execute();
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

    private class GetObjectService extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("TypeClient", "1");
            request.addProperty("ClientVersion", "1.0");
            request.addProperty("Username", "sysadmin");
            request.addProperty("Password", "123456");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(URL);

            httpTransport.debug = true;
            try {
                httpTransport.call(SOAP_ACTION, envelope);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } //send request
            SoapObject result = null;
            try {
                result = (SoapObject) envelope.getResponse();
//                Log.e("result", result.toString());
            } catch (SoapFault e) {
                e.printStackTrace();
            }
            response = "ID: " + result.getProperty(0).toString() + ", Name: " + result.getProperty(1).toString() + ", Action: " + result.getProperty(2).toString() + ", Description: " + result.getProperty(3).toString() + ", Locale key: " + result.getProperty(4).toString();
            Log.e("Object", "" + response);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            txtResponse.setText(response);
        }
    }


}

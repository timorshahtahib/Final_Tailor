package com.hmdapp.finaltailor;
/**
 * Created by King on 4/25/2018.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;


import com.hmdapp.finaltailor.database.LocalData;

import java.util.ArrayList;


public class SMSBReceiver extends BroadcastReceiver {
    String phnumber, message;
    ArrayList<String> spaceToken;
    Context context;
    LocalData localData;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        localData = new LocalData(context);

        // Get Bundle object contained in the SMS intent passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm = null;
        String sms_str = "";
        if (bundle != null) {
            // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            smsm = new SmsMessage[pdus.length];
            for (int i = 0; i < smsm.length; i++) {
                smsm[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                phnumber = smsm[i].getOriginatingAddress();
                message = smsm[i].getMessageBody();
                sms_str += "Sent From: " + smsm[i].getOriginatingAddress();
                sms_str += "\r\nMessage: ";
                sms_str += smsm[i].getMessageBody();
                sms_str += "\r\n";
            }
        }
//String sims[]=new String [localData.get_number_set_reply().size()];

        if (localData.get_customer_reply()) {
            // if(localData.get_rosahn_reply()&& localData.get_etisalat_reply()&&localData.get_salam_reply()&&localData.get_mtn_reply()&&localData.get_awc_reply()){

            if (is_rosha(phnumber)) {

                if (localData.get_rosahn_reply()) {


                    if (message.equalsIgnoreCase("as-11")) {
                        Toast.makeText(context, "msg is" + message, Toast.LENGTH_SHORT).show();
                        send_sms("i  reply to customer", phnumber);
                    }


                } else {
                    Toast.makeText(context, "sim not avalible", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "sim not roshan", Toast.LENGTH_SHORT).show();

            }

        }


    }


    public void send_sms(String msg, String phone) {
        //Getting intent and PendingIntent instance


//Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone, null, msg, null, null);

    }

    public boolean is_rosha(String phone) {
        boolean state = false;
        String[] roshan = {"+9379", "079", "0728", "+93728"};

        for (int i = 0; i < roshan.length; i++) {

            if (phone.startsWith(roshan[i])) {
                state = true;
                break;
            }
        }

        return state;
    }

    public String d(){

        return null;
    }


}



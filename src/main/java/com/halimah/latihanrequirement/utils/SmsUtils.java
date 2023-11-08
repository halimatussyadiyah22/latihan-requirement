package com.halimah.latihanrequirement.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.yaml.snakeyaml.tokens.Token;

import static com.twilio.rest.api.v2010.account.Message.creator;

public class SmsUtils {
    public static final String FROM_NUMBER = "+14848134354";
    public static final String SID_KEY = "AC176d7f02322e1e1e736ba99db0b71e58";
    public static final String TOKEN_KEY = "2a13272ae00a68e9312d3a8bc4e597b6";

    public static void sendSMS(String to, String messageBody) {
        Twilio.init(SID_KEY, TOKEN_KEY);
        Message message = creator(new PhoneNumber("+" + to), new PhoneNumber(FROM_NUMBER), messageBody).create();
        System.out.println(message);
    }
}

//public class SmsUtils {
//    public static final String FROM_NUMBER = "+14848134354";
//    public static final  String SID_KEY = "AC176d7f02322e1e1e736ba99db0b71e58";
//    public static final String TOKEN_KEY = "2a13272ae00a68e9312d3a8bc4e597b6";
//
//    public static void sendSMS(String to, String massageBody) {
//        Twilio.init(SID_KEY, TOKEN_KEY);
//        Message message = creator(new PhoneNumber("+" + to), new PhoneNumber(FROM_NUMBER),massageBody).create();
//    }
//
//}

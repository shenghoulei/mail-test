package com.zlpc;

import com.zlpc.utils.SendMailUtil;

public class SendMail {
	public static void main(String[] args) throws Exception {
		String topic = args[0];
		String msg = args[1];
		SendMailUtil.send("shenghoulei@163.com", topic, msg);
	}
}

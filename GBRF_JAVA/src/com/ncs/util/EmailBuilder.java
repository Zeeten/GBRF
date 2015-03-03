package com.ncs.util;

import java.util.HashMap;

/**
 * Class that build Application Email messages
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 * 
 */

public class EmailBuilder {
	/**
	 * Returns Successful User Registration Message
	 * 
	 * @param map
	 *            : Message parameters
	 * @return
	 */
	public static String getUserRegistrationMessage(HashMap<String, String> map) {
		StringBuilder msg = new StringBuilder();

		msg.append("<HTML><BODY>");
		msg.append("Registration is successful for GBRF");
		msg.append("<H1>Hi! Greetings from GBRF!</H1>");
		msg.append("<P>Congratulations for registering on GBRF! You can now access your GBRF account online - anywhere, anytime and  enjoy the flexibility to check the Book Details.</P>");
		msg.append("<P>Log in today at <a href='http://globalbookreleaseforum.com'>http://globalbookreleaseforum.com</a> with your following credentials:</P>");
		msg.append("<P><B>Login Id : " + map.get("login") + "<BR>"
				+ " Password : " + map.get("password") + "</B></p>");

		msg.append("<P> As a security measure, we recommended that you change your password after you first log in.</p>");
		msg.append("<p>For any assistance, please feel free to call us at +91 9827568186 orr 011-4000000 helpline numbers.</p>");
		msg.append("<p>You may also write to us at infogbrf@gmail.com</p>");
		msg.append("<p>We assure you the best service at all times and look forward to a warm and long-standing association with you.</p>");
		msg.append("<P><a href='http://www.globalbookreleaseforum.com' >-Zeetan Services</a></P>");
		msg.append("</BODY></HTML>");

		return msg.toString();
	}

}
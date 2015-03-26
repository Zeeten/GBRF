package com.ncs.ctl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.ncs.util.DataValidator;
import com.ncs.util.PropertyReader;
import com.ncs.util.ServletUtility;

public class ForgetPasswordCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletUtility.forward("ForgetPassword.jsp", request, response);
	}
	
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email",
					PropertyReader.getValue("error.require", "Email"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email",
					PropertyReader.getValue("error.email", "Email"));
			pass = false;
		}

		return pass;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String operation = request.getParameter("operation");
		if ("Send".equalsIgnoreCase(operation)) {
			if (validate(request)) {
				try {
					String email = request.getParameter("email");
					  URL url = new URL("http://kissmatinternational.com/KIP_TEST/api/rest/forgetpwd");
				        Map<String,Object> params = new LinkedHashMap<>();
				        params.put("email", email);
				    
				        StringBuilder postData = new StringBuilder();
				        for (Map.Entry<String,Object> param : params.entrySet()) {
				            if (postData.length() != 0) postData.append('&');
				            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				            postData.append('=');
				            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
				        }
				        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

				        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				        conn.setRequestMethod("POST");
				        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
				        conn.setDoOutput(true);
				        conn.getOutputStream().write(postDataBytes);

				        InputStream is = conn.getInputStream();
				        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
				        String line;
				        StringBuffer response1 = new StringBuffer(); 
				        while((line = rd.readLine()) != null) {
				          response1.append(line);
				          response1.append('\r');
				        }
				        rd.close();
				       // System.out.println(response1.toString()); 
				        System.out.println(response1.substring(17, 24));
				        JSONObject jsonObj = new JSONObject(response1.toString());
				        if(response1.substring(17, 24).equals("message")){
					        ServletUtility.setSuccessMessage(
									"New password has been sent to your Email Id", request);
							ServletUtility.forward("login.jsp", request, response);
				        }
			        else {
						ServletUtility.setErrorMessage(
								"User does not exist", request);
						ServletUtility.forward("ForgetPassword.jsp", request, response);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				ServletUtility.forward("ForgetPassword.jsp", request, response);
			}
		}
	}

}

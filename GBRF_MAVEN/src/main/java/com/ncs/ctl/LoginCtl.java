package com.ncs.ctl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.ncs.bean.UserBean;
import com.ncs.model.UserModel;
import com.ncs.util.DataValidator;
import com.ncs.util.PropertyReader;
import com.ncs.util.ServletUtility;

public class LoginCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletUtility.forward("login.jsp", request, response);
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
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password",
					PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}

		return pass;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String operation = request.getParameter("operation");
		if ("Sign In".equalsIgnoreCase(operation)) {
			if (validate(request)) {
				try {
					String email = request.getParameter("email");
					String password = request.getParameter("password");
					UserModel model=new UserModel();
					UserBean bean=new UserBean();
					if(email.equals("admin@nenosystems.com")){
						bean = model.authenticate(email, password);

						if (bean != null) {
							session.setAttribute("firstname", bean.getName());
				        	session.setAttribute("lastname", bean.getSurname());
				        	session.setAttribute("email", bean.getEmail());
				        	session.setAttribute("password", bean.getPassword());
				        	session.setAttribute("session", bean);
				        	session.setAttribute("telephone", bean.getMobileNo());
				        	ServletUtility.redirect("AdminCtl.do", request,
									response);
						} else {
							ServletUtility.setErrorMessage(
									"Invalid Email / Password", request);
							ServletUtility.forward("login.jsp", request, response);
						}
					}else{
					
					  URL url = new URL("http://kissmatinternational.com/KIP_TEST/api/rest/login");
				        Map<String,Object> params = new LinkedHashMap<>();
				        params.put("email", email);
				        params.put("password", password);
				    
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
				       // System.out.println(response1.substring(11, 15));
				        JSONObject jsonObj = new JSONObject(response1.toString());
				        System.out.println(jsonObj.getString("success"));  
	
				        if(jsonObj.getString("success").equals("true")){
					        JSONObject jsonObj1 = new JSONObject(jsonObj.getString("data"));
					       // System.out.println(jsonObj1.getString("firstname"));
				        	session.setAttribute("firstname", jsonObj1.getString("firstname"));
				        	session.setAttribute("lastname", jsonObj1.getString("lastname"));
				        	session.setAttribute("email", jsonObj1.getString("email"));
				        	session.setAttribute("password", jsonObj1.getString("salt"));
				        	session.setAttribute("session", jsonObj1.getString("session"));
				        	session.setAttribute("telephone", jsonObj1.getString("telephone"));
						ServletUtility.redirect("AdminCtl.do", request,
								response);
				        }
			        else {
						ServletUtility.setErrorMessage(
								"Invalid Email / Password", request);
						ServletUtility.forward("login.jsp", request, response);
					}
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				ServletUtility.forward("login.jsp", request, response);
			}
		}
	}
}
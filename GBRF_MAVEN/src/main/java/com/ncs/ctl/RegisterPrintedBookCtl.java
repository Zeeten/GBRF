package com.ncs.ctl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.ncs.bean.RegisterPrintedBookBean;
import com.ncs.bean.UserBean;
import com.ncs.exception.ApplicationException;
import com.ncs.exception.DuplicateRecordException;
import com.ncs.model.BooksModel;
import com.ncs.model.RegisterPrintedBookModel;
import com.ncs.model.UserModel;
import com.ncs.util.DataUtility;
import com.ncs.util.DataValidator;
import com.ncs.util.PropertyReader;
import com.ncs.util.ServletUtility;

public class RegisterPrintedBookCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			BooksModel model = new BooksModel();
			List list = model.list();
			ServletUtility.setList(list, request);
			//request.setAttribute("BList", list);
			ServletUtility
					.forward("RegisterPrintedBook.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String dateofpurchase = request.getParameter("dateofpurchase");
		if (DataValidator.isNull(request.getParameter("bookName"))) {
			request.setAttribute("bookName",
					PropertyReader.getValue("error.require", "Book Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("bookId"))) {
			request.setAttribute("bookId",
					PropertyReader.getValue("error.require", "Book ID"));
			pass = false;
		}

		if (DataValidator.isNull(dateofpurchase)) {
			request.setAttribute("dateofpurchase", PropertyReader.getValue(
					"error.require", "Date Of Purchase"));
			pass = false;
		} else if (!DataValidator.isDate(dateofpurchase)) {
			request.setAttribute("dateofpurchase",
					PropertyReader.getValue("error.date", "Date Of Purchase"));
			pass = false;
		}
/*		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo",
					PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		}
*/
		return pass;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		String operation = request.getParameter("operation");
		// get model
		RegisterPrintedBookModel model = new RegisterPrintedBookModel();
         UserModel userModel=new UserModel();
         
		if ("Register".equalsIgnoreCase(operation)) {
			if (validate(request)) {
				try {
				RegisterPrintedBookBean bean = new RegisterPrintedBookBean();
				UserBean userBean=new UserBean();
				bean.setBookName(DataUtility.getString(request
						.getParameter("bookName")));
				bean.setBookId(DataUtility.getString(request
						.getParameter("bookId")));
		
				bean.setDate(DataUtility.getDate(request.getParameter("dateofpurchase")));
			if(session.getAttribute("session")!=null){
				bean.setEmail((session.getAttribute("email").toString()));
				bean.setPassword((session.getAttribute("password").toString()));
				bean.setMobileno(( session.getAttribute("telephone").toString()));
				model.add(bean);
				ServletUtility.redirect("MyRegisteredBooksCtl", request, response);	
			}else{
				String firstname = request.getParameter("firstName");
				String lastname = request.getParameter("lastName");
				String email = request.getParameter("email");
				String mobileNo = request.getParameter("mobileNo");
				String password = request.getParameter("password");
				URL url = new URL("http://kissmatinternational.com/KIP_TEST/api/rest/registeruser");
		        Map<String,Object> params = new LinkedHashMap<>();
		        params.put("firstname", firstname);
		        params.put("lastname", lastname);
		        params.put("email", email);
		        params.put("telephone", mobileNo);
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
				bean.setEmail(DataUtility.getString(request
						.getParameter("email")));
				bean.setPassword(DataUtility.getString(request
						.getParameter("password")));
		        	bean.setMobileno(DataUtility.getString(request
							.getParameter("mobileNo")));
					model.add(bean);
		        	
		        	userBean.setName(DataUtility.getString(request
							.getParameter("firstName")));
		        	userBean.setSurname(DataUtility.getString(request
							.getParameter("lastName")));
		        	userBean.setEmail(DataUtility.getString(request
							.getParameter("email")));
		        	userBean.setPassword(DataUtility.getString(request
							.getParameter("password")));
		        	userBean.setMobileNo(DataUtility.getString(request
							.getParameter("mobileNo")));
		        	userModel.add(userBean);
					ServletUtility.redirect("LoginCtl", request, response);
					}else{
		        	 JSONObject jsonObj1 = new JSONObject(jsonObj.getString("error"));
		        	if(jsonObj1.getString("warning").equals("Warning: E-Mail Address is already registered!")){
		        		ServletUtility.setErrorMessage(
								"E-Mail Address is already registered!", request);
		        		doGet(request, response);
		        		
		        	}
		        	
					}
		        	
			}

				} 
	
				catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					return;
				} catch (DuplicateRecordException e) {
					ServletUtility.setErrorMessage(
							"Book Id already exists", request);
					doGet(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				doGet(request, response);
			}
			}if("My Registered Books".equalsIgnoreCase(operation)){
				ServletUtility.redirect("MyRegisteredBooksCtl", request, response);	
			}
		}
	

}

package com.ncs.ctl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		// get model
		UserModel model = new UserModel();

		if ("SignIn".equalsIgnoreCase(operation)) {
			if (validate(request)) {
				try {
					String email = request.getParameter("email");
					String password = request.getParameter("password");

					UserBean bean = model.authenticate(email, password);
					PrintWriter out = response.getWriter();
					if (bean != null) {
						session.setAttribute("user", bean);
						ServletUtility.redirect("AdminCtl.do", request,
								response);
						// String json = new Gson().toJson(bean);
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						// response.getWriter().write(json);
						// out.println("{\"Messages\":" + json + "}");
					} else {
						ServletUtility.setErrorMessage(
								"Invalid Email / Password", request);
						ServletUtility.forward("login.jsp", request, response);
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
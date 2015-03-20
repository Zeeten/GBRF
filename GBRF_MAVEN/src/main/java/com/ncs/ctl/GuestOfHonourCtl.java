package com.ncs.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncs.bean.GuestOfHonourBean;
import com.ncs.model.GuestOfHonourModel;
import com.ncs.util.DataValidator;
import com.ncs.util.PropertyReader;
import com.ncs.util.ServletUtility;

public class GuestOfHonourCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		GuestOfHonourModel model = new GuestOfHonourModel();
		List dtoList = null;
		try {
			dtoList = model.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("dtoList", dtoList);
		ServletUtility.forward("GuestofHonour.jsp", request, response);
	}
	
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name",
					PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}

		return pass;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		String operation = request.getParameter("operation");
		if ("Save".equalsIgnoreCase(operation)) {
			if (validate(request)) {
			try {
				String name = request.getParameter("name");
				GuestOfHonourModel model = new GuestOfHonourModel();
				GuestOfHonourBean bean = new GuestOfHonourBean();
					bean.setName(name);
					model.add(bean);
				doGet(request, response); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else{
			doGet(request, response); 
		}

	}
	}
}
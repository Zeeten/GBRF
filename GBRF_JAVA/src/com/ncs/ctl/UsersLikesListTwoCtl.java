package com.ncs.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncs.model.LikesModel;
import com.ncs.util.ServletUtility;

public class UsersLikesListTwoCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get model
		LikesModel model = new LikesModel();
		List dtoList = null;
		try {
			dtoList = model.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("dtoList", dtoList);
		ServletUtility.forward("UsersLikesListTwo.jsp", request, response);
	}
}
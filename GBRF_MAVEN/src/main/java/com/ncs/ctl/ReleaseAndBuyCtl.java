package com.ncs.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncs.model.ReleaseAndBuyModel;

public class ReleaseAndBuyCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ReleaseAndBuyModel model = new ReleaseAndBuyModel();
		List dtoList = null;
		try {
			dtoList = model.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("dtoList", dtoList);
		RequestDispatcher rd = request
				.getRequestDispatcher("ReleaseAndBuy.jsp");
		rd.forward(request, response);
	}


}

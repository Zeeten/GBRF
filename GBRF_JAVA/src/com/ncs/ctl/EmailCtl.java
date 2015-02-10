package com.ncs.ctl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncs.bean.UserBean;
import com.ncs.model.UserModel;
import com.ncs.util.EmailMessage;
import com.ncs.util.EmailUtility;

public class EmailCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String op = request.getParameter("operation");

		// get model
		UserModel model = new UserModel();

		System.out.println("in if " + op);

		if (op.equalsIgnoreCase("Please wait, sending confirmation...")) {

			System.out.println("in if  " + email);
			System.out.println("in if " + op);

			UserBean bean = new UserBean();
			bean.setEmail(email);

			try {
				model.add(bean);

				System.out.println("Added in database");
				EmailMessage msg = new EmailMessage();
				msg.setTo(email);
				msg.setSubject("I Accept Invitaion");
				msg.setMessage("<html><body><b>Dear Guest of Honour,</b><br>Thank you for showing your interest and accepting the Invite.<br>We will stay in touch with you for the updates.<br><br><b>Administrator,</b><br>Global Book Release Forum<br><img src=http://globalbookreleaseforum.com/GBRF/img/logo.png height=67 width=184><br><a href=http://globalbookreleaseforum.com/GBRF/ target=_blank>www.globalbookreleaseforum.com</a></body></html>.");
				msg.setMessageType(EmailMessage.HTML_MSG);
				EmailUtility.sendMail(msg);

				RequestDispatcher rd = request
						.getRequestDispatcher("index-wl.jsp");
				rd.forward(request, response);

			} catch (Exception e) {
			//	e.printStackTrace();
				RequestDispatcher rd = request
						.getRequestDispatcher("index-wl.jsp");
				rd.include(request, response);
			}
		}
	}
}

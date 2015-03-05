package com.ncs.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncs.bean.InviteBean;
import com.ncs.model.InviteModel;
import com.ncs.util.DataValidator;
import com.ncs.util.EmailMessage;
import com.ncs.util.EmailUtility;
import com.ncs.util.PropertyReader;
import com.ncs.util.ServletUtility;

public class InviteCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String op = request.getParameter("operation");
		if (op.equalsIgnoreCase("Please wait, sending confirmation...")) {
			// get model
			InviteModel model = new InviteModel();
			InviteBean bean = new InviteBean();
			bean.setEmail(email);
			try {
				model.add(bean);
				EmailMessage msg = new EmailMessage();
				msg.setTo(email);
				msg.setSubject("I Accept Invitaion");
				msg.setMessage("<html><body><b>Dear Guest of Honour,</b><br>Thank you for showing your interest and accepting the Invite.<br>We will stay in touch with you for the updates.<br><br><b>Administrator,</b><br>Global Book Release Forum<br><img src=http://globalbookreleaseforum.com/GBRF/img/logo.png height=67 width=184><br><a href=http://globalbookreleaseforum.com/GBRF/ target=_blank>www.globalbookreleaseforum.com</a></body></html>.");
				msg.setMessageType(EmailMessage.HTML_MSG);
				EmailUtility.sendMail(msg);
				ServletUtility.forward("index-wl.jsp", request, response);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.forward("index-wl.jsp", request, response);
			}
		}
	}
}

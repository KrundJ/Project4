package ua.training.project4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author hengyunabc
 *
 */
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 5701117482475493759L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		req.getRequestDispatcher("test.jsp").forward(req, res);
	}
}
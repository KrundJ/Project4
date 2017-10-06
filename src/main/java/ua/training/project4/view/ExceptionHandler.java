package ua.training.project4.view;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ua.training.project4.model.service.ServiceException;

public class ExceptionHandler extends TagSupport {
	
	private RuntimeException exception;

	public void setException(RuntimeException exception) {
		this.exception = exception;
	}
	
	public int doStartTag() throws JspException {
		
		try {
			doWrite();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	private void doWrite() throws Exception {
		JspWriter out = pageContext.getOut();
		ResourceBundle bundle = ResourceBundle.getBundle(
				"ua.training.project4.messages", 
				(Locale) pageContext.getSession().getAttribute("locale"));
		out.println("<h3>");
//		out.println(exception.getClass().getSimpleName());
		if (exception.getClass().equals(ServiceException.class)) {
			String key = ((ServiceException) exception).getBundleKey();
			String value = ((ServiceException) exception).getData();
			out.println(bundle.getString(key) + " " + value);
		} else {
			out.println(bundle.getString(exception.getMessage()));
		}
		out.println("</h3>");
	}
}

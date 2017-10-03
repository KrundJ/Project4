package ua.training.project4.view;

import java.util.Map;
import java.util.Objects;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ua.training.project4.model.entities.Horse;

public class RaceResults extends TagSupport {

	private static final long serialVersionUID = 5471885885236845927L;
	
	private static final int TOTAL_HORSES = 5;

	private Map<Horse, Integer> results;
	
	private  Map<Integer, String> selection;

	public void setResults(Map<Horse, Integer> results) {
		this.results = results;
	}

	public void setSelection(Map<Integer, String> selection) {
		this.selection = selection;
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
		for (int i = 1; i <= TOTAL_HORSES; i++) {
			out.println("<tr>");
				out.println("<td align=\"center\">");
					out.println("<b>" + i + "</b>");
				out.println("</td>");
				out.println("<td>");
					out.println("<select name=\"" + i + "\">");
					for (Horse h : results.keySet()) {
						if (Objects.nonNull(selection) && selection.values().contains(h.getName())
								&& h.getName().equals(selection.get(i))) {
							out.println(String.format("<option value=\"%s\" selected=\"selected\">%s</option>", h.getName(),
									h.getName()));
						} else {
							out.println(String.format("<option value=\"%s\">%s</option>", h.getName(), h.getName()));
						}
					}
					out.println("</select>");
				out.println("</td>");
			out.println("</tr>");
  		}
	}
}


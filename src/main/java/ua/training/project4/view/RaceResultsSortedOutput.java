package ua.training.project4.view;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ua.training.project4.model.entities.Horse;

public class RaceResultsSortedOutput extends TagSupport {

	private static final long serialVersionUID = -8128120300894095434L;
	
	private Map<Horse, Integer> results;

	public void setResults(Map<Horse, Integer> results) {
		this.results = results;
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
		List<Integer> places = new LinkedList<>(results.values());
		Collections.sort(places);
		for (Integer p : places) {
			 String name = results.entrySet().stream()
					.filter(e -> e.getValue().equals(p))
					.map(Entry::getKey).map(Horse::getName).findFirst().get();
			out.write(p + " " + name);
			out.write("<br>");
		}
	}
}

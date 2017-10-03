package ua.training.project4.view;

import java.util.Objects;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;

public class HorsesInRace extends TagSupport {

	private static final long serialVersionUID = -4258868580580773838L;

	private static final int ITEMS_TOTAL = 5;
	
	private static final int ITEMS_IN_ROW = 3;
	
	private Set<Horse> allHorses;
	
	private Race raceToEdit;

	public void setAllHorses(Set<Horse> allHorses) {
		this.allHorses = allHorses;
	}


	public void setRaceToEdit(Race raceToEdit) {
		this.raceToEdit = raceToEdit;
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
		try {
			Set<Horse> horsesInRace = null;
			if (raceToEdit != null) {
				horsesInRace = raceToEdit.getRaceResults().keySet();
			}
			int itemsPrinted = 0;
			boolean needMoreItems = true;
			while (needMoreItems) {
				out.println("<tr>");
				for (int j = 1; j <= ITEMS_IN_ROW; j++) {
					out.println("<td>");
						out.println("<select name=\"horseNames\">");
						boolean selectionSet = false;
						for (Horse h : allHorses) {
							if (Objects.nonNull(horsesInRace) && horsesInRace.contains(h) && !selectionSet) {
								out.println(String.format("<option value=\"%s\" selected=\"selected\">%s</option>",
										h.getName(), h.getName()));
								horsesInRace.remove(h);
								selectionSet = true;
							} else {
								out.println(String.format("<option value=\"%s\">%s</option>", h.getName(), h.getName()));
							}
						}
						out.println("</select>");
					out.println("</td>");
					itemsPrinted++;
					if (itemsPrinted == ITEMS_TOTAL) {
						needMoreItems = false;
						break;
					}
				}
				out.println("</tr>");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}


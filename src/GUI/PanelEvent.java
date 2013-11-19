package GUI;

import java.util.EventObject;

public class PanelEvent extends EventObject {
	
	
	private static final long serialVersionUID = 1557057419930193114L;
	private String text;

	public PanelEvent(Object source, String text) {
		super(source);
		this.text = text;
}
	
	public String getText() {
		return text;
	}
	
}

package GUI;

import java.util.EventListener;

public interface Listener extends EventListener {
	public void filmEvent(PanelEvent e);
	public void pathEvent(PanelEvent e);
}

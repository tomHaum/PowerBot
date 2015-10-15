package tbhizzle.rt6.cowkiller.gui;

import java.awt.*;

import org.powerbot.script.PaintListener;
import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;

public class Paint extends ClientAccessor implements PaintListener {
	Component chatBox = ctx.widgets.component(1477, 66);
	private long startTime;
	public int banked = 0;
	public Paint(ClientContext ctx) {
		super(ctx);
		startTime = System.currentTimeMillis();
	}

	@Override
	public void repaint(Graphics g) {
		Rectangle rect = chatBox.boundingRect();
		int x = (int)rect.getX();
		int y = (int)rect.getY();
		int width = (int)rect.getWidth();
		int height = (int)rect.getHeight();

		g.setColor(new Color(232, 232, 232, 255));
		g.fillRect(x,y,width,height);
		g.setColor(new Color(156, 156, 156,255));
		g.drawRect(x,y,width,height);
		g.drawString("Cow Killer", x + 20, y+=15);
//		g.drawString("Hides banked: " + (28*((Bank)(tasks[1])).inventories), 4, 25);
		g.drawString("Run Time: " + getRunTime(), x + 20,y+=20);
		g.drawString("Items Banked: " + banked, x + 20, y+=20);
	}
	
	private String getRunTime(){
		long diff = System.currentTimeMillis() - startTime;
		int seconds = (int) (diff / 1000) % 60 ;
		int minutes = (int) ((diff / (1000*60)) % 60);
		int hours   = (int) ((diff / (1000*60*60)) % 24);
		String result = "";
		result = String.format("%02d:%02d", minutes,seconds);
		if(hours > 0)result = hours + ":" + result;
		return result;
	}
}

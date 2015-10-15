package tbhizzle.rt6.cowkiller.util;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import tbhizzle.util.Task;

public abstract class WidgetTask extends Task<ClientContext> {
	public int[] windowOpenIds;
	public int[] clickIds;
	
	public Component windowOpenWidget, clickComponent;

	public WidgetTask(ClientContext ctx, int[] windowOpenIds, int[] clickIds){
		super(ctx);
		this.windowOpenIds = windowOpenIds;
		this.clickIds = clickIds;
//		this.checkForIds = checkForIds;
		windowOpenWidget = findComponent(windowOpenIds);
		clickComponent = findComponent(clickIds);

	}

//		if(null == windowOpenWidget){
//			windowOpenWidget = findComponent(windowOpenIds);
//			if(windowOpenWidget==null)return false;
//		}
//		return windowOpenWidget.visible();
//	}

	@Override
	public void execute() {

		doStuff();
		Condition.wait(new Callable<Boolean>(){
			@Override
			public Boolean call() throws Exception {
				return checkFor();
			}
		},100, 20);
	}

	@Override
	public abstract boolean activate ();

	public abstract boolean doStuff();
	
	public abstract boolean checkFor();

	public Component findComponent(int... ids){
		if(ids == null)return null;
		if(ids.length < 2){
			return null;
		}
		Component base = ctx.widgets.component(ids[0], ids[1]);
		if(ids.length > 2){
			Component temp = base;
			for(int i = 2; i < ids.length;i++){
				temp = base.component(ids[i]);
				base = temp;
			}
			return base;
		}
		return base;
	}
}

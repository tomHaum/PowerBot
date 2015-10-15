package tbhizzle.oldschool.script.chopandburn;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.*;

import org.powerbot.script.*;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;


@Script.Manifest(name = "THis is my file", description = "Kills cows and collects hides")
public class ChopAndBurn extends PollingScript<ClientContext> implements PaintListener{
	private static final Tile BANKTILE = new Tile(0,0,0);
	private static final Tile FURNACETILE = new Tile(0,0,0);

	@Override
	public void poll(){
		if(nearTeller()){
			if(needToBank()){
				bank();
			}else{
				walkToFurnace();
			}
		}else{
			if(needToBank()){
				walkToBank();
			}else{
				if(nearFurnace()){
					smelt();
				}else{
					waltToFurnace();
				}
			}
		}
	}

	@Override
	public void repaint(Graphics g){
		g.fill3DRect(0,0,100,100,false);
		return;
	}

	private boolean nearTeller(){
		return true;
	}
	private boolean needToBank(){
		return true;
	}
	private boolean nearFurnace(){
		return true;
	}
	private void bank(){
		return;
	}
	private void walkToFurnace() {
		walk(FURNACETILE);
	}
	private void walkToBank(){
		walk(BANKTILE);
	}
	private void smelt(){
		return;
	}
	private void waltToFurnace(){
		return;
	}
	private void walk(Tile t){
		return;
	}

}

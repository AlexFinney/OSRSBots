package lib.task;

import org.osbot.rs07.api.model.Item;

import lib.BabushkaScript;

public class EatWhenBelowHealthTask extends Task{
	int min_health = 50;
	String[] foodTypes = {"Shark", "Swordfish", "Lobster",  "Tuna", "Salmon"};
	public EatWhenBelowHealthTask(BabushkaScript scr, int min_health) {
		super(scr);
	}
	
	@Override
	public boolean canExecute() {
		script.log(script.myPlayer().getCurrentHealth());
		return  script.myPlayer().getCurrentHealth() < min_health;
	}
	
	@Override
	public int execute() {
		// TODO Auto-generated method stub
		Item food = null;
		for(String s : foodTypes) {
			if(script.inventory.getItem(s) != null) {
				food = script.inventory.getItem(s);
				food.interact("Eat");
				return 2000;
			}
		}
		return 0;
	}
	
}

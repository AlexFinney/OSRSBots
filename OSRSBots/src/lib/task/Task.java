package lib.task;

import java.util.ArrayList;
import java.util.List;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;

import lib.BabushkaScript;

public abstract class Task {

	protected BabushkaScript script = null;
	public Task(BabushkaScript scr) {
		this.script = scr;
	}
	
	
	public abstract boolean canExecute();
	public abstract int execute();
	
	protected List<RS2Object> getObjects(String name, int x, int y) {
		List<RS2Object> objs = new ArrayList<>();
		
		objs = script.getObjects().get(x, y);
		
		return objs;
	}
	
	protected int random(int n1, int n2) {
		return Script.random(n1, n2);
	}
}

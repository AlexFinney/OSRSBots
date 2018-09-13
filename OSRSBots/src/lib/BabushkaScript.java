package lib;

import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.osbot.rs07.script.Script;

import lib.task.Task;

public abstract class BabushkaScript extends Script{

	protected List<Task> tasks = null;
	
	protected abstract List<Task> createTasks();
	protected abstract void collectWorldState();
	public Camera camera = null;
	@Override
	public void onStart() throws InterruptedException {
		super.onStart();
		
		camera = new Camera(this);
		
		tasks = createTasks();
	}
	
	
	@Override
	public int onLoop() throws InterruptedException {
		if(tasks == null || tasks.size() == 0) {
			this.logger.error("No tasks assigned to current script.  Exiting...");
			stop(false);
		}
		
		collectWorldState();
		
		int delay = 250;
		if(isBotBusy())
			return delay;
		
		for(Task t : tasks) {
			if(t.canExecute())
				delay = t.execute();
		}
		
		return delay;
	}
	
	public boolean isBotBusy() {
		return this.myPlayer().isAnimating() || myPlayer().isMoving();
	}
}

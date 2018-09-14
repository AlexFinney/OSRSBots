package lib;

import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.osbot.rs07.script.Script;

import lib.globals.Stage;
import lib.globals.Variables;
import lib.task.Task;

public abstract class BabushkaScript extends Script{

	protected List<Task> tasks = null;
	
	protected abstract List<Task> createTasks() throws InterruptedException;
	protected abstract void collectWorldState();
	public Camera camera = null;
	public Stage current_stage = null;
	@Override
	public void onStart() throws InterruptedException {
		super.onStart();
		camera = new Camera(this);
		
		tasks = createTasks();
		Variables.TIME_STARTED = System.currentTimeMillis();
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
		
		return 250;
	}
	
	protected String timeRunningFormatted(float hoursRunning) {
		float mins = (hoursRunning - (int)hoursRunning) * 60f;
		float secs = (mins - (int)mins) * 60f;
		int hours = (int)hoursRunning / (60 * 60 * 1000);
		
		log(secs + ", " + mins);
		return String.format("%d", hours) + ":" +
			   String.format("%d", (int)mins) + ":" +
			   String.format("%d", (int)secs);
		
	}
	
	public float hoursRunning() {
		long millis_running = System.currentTimeMillis() - Variables.TIME_STARTED;
		return millis_running / (1000f * 60f * 60f);
	}
	
	public boolean isBotBusy() {
		return this.myPlayer().isAnimating() || myPlayer().isMoving();
	}
}

package bots.lumby_flax_spinner;

import java.util.ArrayList;
import java.util.List;

import org.osbot.rs07.script.ScriptManifest;

import lib.BabushkaScript;
import lib.globals.Objects;
import lib.task.Task;

@ScriptManifest(author="TheGreatBabushka",  name="Lumbridge Flax Spinner", info="Spins flax at lumbridge bank.", version=0.1, logo="")
public class LumbyFlaxSpinner extends BabushkaScript{

	@Override
	protected List<Task> createTasks() throws InterruptedException {
		List<Task> tasks = new ArrayList<Task>();
		
		//tasks.add(new GetItemsFromBank(this, 28, "Flax"));
		
		return tasks;
	}

	@Override
	protected void collectWorldState() {
		Objects.LUMBRUDGE_SPINNING_WHEEL = objects.closest("Spinning Wheel");
	}
	
	@Override
	public int onLoop() throws InterruptedException {
		return super.onLoop();
	}
	
}

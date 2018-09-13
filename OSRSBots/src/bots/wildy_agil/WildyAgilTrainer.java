package bots.wildy_agil;

import java.util.ArrayList;
import java.util.List;

import org.osbot.rs07.script.ScriptManifest;

import lib.BabushkaScript;
import lib.globals.Objects;
import lib.task.Task;

@ScriptManifest(author="TheGreatBabushka",  name="Wilderness Agility", info="Trains your agility at the wilderness agility course.", version=0.1, logo="")
public class WildyAgilTrainer extends BabushkaScript{

	@Override
	protected List<Task> createTasks() {
		List<Task> tasks = new ArrayList<Task>();
		
		tasks.add(new WildyAgilityRopeTask(this));
		tasks.add(new WildyAgilityPipeTask(this));
		
		return tasks;
	}

	@Override
	protected void collectWorldState() {
		Objects.WILDERNESS_AGILITY_COURSE_PIPE				= objects.closest("Obstacle pipe");
		Objects.WILDERNESS_AGILITY_COURSE_LOG_BALANCE 		= objects.closest("Log balance");
		Objects.WILDERNESS_AGILITY_COURSE_ROCK_CLIMB 		= objects.closest("Rocks");
		Objects.WILDERNESS_AGILITY_COURSE_ROPE_SWING 		= objects.closest("Ropeswing");
		Objects.WILDERNESS_AGILITY_COURSE_STEPPING_STONES 	= objects.closest("Stepping-stones");
	}
	
}

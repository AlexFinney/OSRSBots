package bots.wildy_agil;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.osbot.rs07.script.ScriptManifest;

import lib.BabushkaScript;
import lib.globals.Objects;
import lib.globals.Stage;
import lib.globals.Variables;
import lib.task.EatWhenBelowHealthTask;
import lib.task.Task;

@ScriptManifest(author="TheGreatBabushka",  name="Wilderness Agility2", info="Trains your agility at the wilderness agility course.", version=0.1, logo="")
public class WildyAgilTrainer extends BabushkaScript{

	@Override
	protected List<Task> createTasks() throws InterruptedException {
		List<Task> tasks = new ArrayList<Task>();
		
		tasks.add(new EatWhenBelowHealthTask(this, 50));
		tasks.add(new Pipe(this, Stage.WILDERNESS_AGILITY_PIPE));
		tasks.add(new Rope(this, Stage.WILDERNESS_AGILITY_ROPE));
		tasks.add(new Ladder(this, Stage.WILDERNESS_AGILITY_ESCAPE_FALL));
		tasks.add(new SteppingStones(this, Stage.WILDERNESS_AGILITY_STONES));
		tasks.add(new Log(this, Stage.WILDERNESS_AGILITY_LOG));
		tasks.add(new RockClimb(this, Stage.WILDERNESS_AGILITY_ROCK_CLIMB));
		
		current_stage = Stage.WILDERNESS_AGILITY_PIPE;
		
		return tasks;
	}

	@Override
	protected void collectWorldState() {
		Objects.WILDERNESS_AGILITY_COURSE_PIPE				= objects.closest("Obstacle pipe");
		Objects.WILDERNESS_AGILITY_COURSE_LOG_BALANCE 		= objects.closest("Log balance");
		Objects.WILDERNESS_AGILITY_COURSE_ROCK_CLIMB 		= objects.closest("Rocks");
		Objects.WILDERNESS_AGILITY_COURSE_ROPE_SWING 		= objects.closest("Ropeswing");
		Objects.WILDERNESS_AGILITY_COURSE_STEPPING_STONES 	= objects.closest("Stepping stone");
		Objects.WIDLERNESS_AGILITY_COURSE_LADDER			= objects.closest("Ladder");
	}
	
	@Override
	public int onLoop() throws InterruptedException {
		return super.onLoop();
	}
	
	@Override
	public void onPaint(Graphics2D g) {
		super.onPaint(g);
		
		g.drawString("Stage: " + this.current_stage, 10, 60);
		g.drawString("Total XP: " + Variables.XP_EARNED, 10, 80);
		g.drawString("XP/H: " + calcXP_H(), 10, 120);
		g.drawString("Total Time: " + timeRunningFormatted(hoursRunning()), 10, 100);
	}
	
	int calcXP_H() {
		
		return  (int) (Variables.XP_EARNED / hoursRunning());
	}
}

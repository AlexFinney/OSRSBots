package bots.wildy_agil;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.utility.ConditionalSleep;

import lib.BabushkaScript;
import lib.Camera;
import lib.globals.Objects;
import lib.task.Task;

public class Log extends Task {
	Camera camera;
	RS2Object LOG;

	public Log(BabushkaScript apiIn, Camera cam) {
		super(apiIn);
		this.camera = cam;
	}

	@Override
	public boolean canExecute() {
		LOG = Objects.WILDERNESS_AGILITY_COURSE_LOG_BALANCE;
		return LOG != null && !script.isBotBusy();
	}

	@Override
	public int execute() {
		if (this.script.myPosition().distance(this.LOG) > 5) {
			script.getWalking().walk(this.LOG.getArea(1).getRandomPosition());
			this.camera.toEntity(this.LOG);
		} else if (this.LOG.interact(new String[] { "Walk-across" })) {
			final int currentXP = script.skills.getExperience(Skill.AGILITY);
			new ConditionalSleep(8000, 500) {
				public boolean condition() throws InterruptedException {
					return script.skills.getExperience(Skill.AGILITY) > currentXP;
				}
			}.sleep();
		}
		return 0;
	}
}

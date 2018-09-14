package bots.wildy_agil;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import lib.BabushkaScript;
import lib.Camera;
import lib.globals.Objects;
import lib.globals.Stage;
import lib.globals.Variables;
import lib.task.Task;

public class Log extends Task {
	Camera camera;
	RS2Object LOG;

	public Log(BabushkaScript apiIn) {
		super(apiIn);
	}
	
	public Log(BabushkaScript apiIn, Stage s) {
		super(apiIn, s);
	}

	@Override
	public boolean canExecute() {
		LOG = Objects.WILDERNESS_AGILITY_COURSE_LOG_BALANCE;
		return LOG != null && super.canExecute();
	}

	@Override
	public int execute() {
		if (this.script.myPosition().distance(this.LOG) > 3) {
			script.getWalking().walk(this.LOG.getArea(1).getRandomPosition());
			return 4000;
		}else {
			if(!LOG.isVisible() && camera != null)
				this.camera.toEntity(this.LOG);
			
			if (LOG.interact(new String[] { "Walk-across" })) {
				final int currentXP = script.skills.getExperience(Skill.AGILITY);
				new ConditionalSleep(8000, 500) {
					public boolean condition() throws InterruptedException {
						return script.skills.getExperience(Skill.AGILITY) > currentXP;
					}
				}.sleep();
				
				Variables.XP_EARNED += script.skills.getExperience(Skill.AGILITY) - currentXP;
				
				if(script.skills.getExperience(Skill.AGILITY) > currentXP) {
					script.current_stage = Stage.WILDERNESS_AGILITY_ROCK_CLIMB;
				}else {
					script.current_stage = Stage.WILDERNESS_AGILITY_ESCAPE_FALL;
				}
			}
		}
		return 100;
	}
}

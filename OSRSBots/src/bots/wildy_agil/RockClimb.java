package bots.wildy_agil;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

import lib.BabushkaScript;
import lib.Camera;
import lib.globals.Objects;
import lib.globals.Stage;
import lib.globals.Variables;
import lib.task.Task;

public class RockClimb extends Task {
	RS2Object ROCK_CLIMB;
	Camera camera;

	public RockClimb(BabushkaScript apiIn) {
		super(apiIn);
	}
	
	public RockClimb(BabushkaScript apiIn, Stage s) {
		super(apiIn, s);
	}

	@Override
	public boolean canExecute() {
		ROCK_CLIMB = Objects.WILDERNESS_AGILITY_COURSE_ROCK_CLIMB;
		return ROCK_CLIMB != null && super.canExecute();
	}

	public int execute() {
		if (!this.ROCK_CLIMB.isVisible() && this.camera != null) {
			this.camera.toEntity(this.ROCK_CLIMB);
		}
		
		if (this.ROCK_CLIMB.interact(new String[] { "Climb" })) {
			final int currentXP = this.script.skills.getExperience(Skill.AGILITY);
			new ConditionalSleep(8000, 500) {
				public boolean condition() throws InterruptedException {
					return RockClimb.this.script.skills.getExperience(Skill.AGILITY) > currentXP;
				}
			}.sleep();
			
			Variables.XP_EARNED += script.skills.getExperience(Skill.AGILITY) - currentXP;
			Variables.WILDERNESS_STONES_CROSSED = false;
			script.current_stage = Stage.WILDERNESS_AGILITY_PIPE;
		}
		
		return 100;
	}
}

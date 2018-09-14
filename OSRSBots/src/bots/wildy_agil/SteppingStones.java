package bots.wildy_agil;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.utility.ConditionalSleep;

import lib.BabushkaScript;
import lib.Camera;
import lib.globals.Objects;
import lib.globals.Stage;
import lib.globals.Variables;
import lib.task.Task;

public class SteppingStones extends Task {
	Camera camera;
	RS2Object STEPPING_STONES = null;

	public SteppingStones(BabushkaScript apiIn) {
		super(apiIn);
	}
	
	public SteppingStones(BabushkaScript apiIn, Stage s) {
		super(apiIn, s);
	}

	public boolean canExecute() {
		STEPPING_STONES = Objects.WILDERNESS_AGILITY_COURSE_STEPPING_STONES;
		return STEPPING_STONES != null && super.canExecute();
	}
	
	@Override
	public int execute() {
		if (!STEPPING_STONES.isVisible() && this.camera != null)
			this.camera.toEntity(STEPPING_STONES);

		if (STEPPING_STONES.interact(new String[] { "Cross" })) {
			final int currentXP = this.script.skills.getExperience(Skill.AGILITY);
			new ConditionalSleep(12000, 500) {
				public boolean condition() throws InterruptedException {
					return (script.skills.getExperience(Skill.AGILITY) > currentXP);
				}
			}.sleep();

			Variables.XP_EARNED += script.skills.getExperience(Skill.AGILITY) - currentXP;
			
			if (script.skills.getExperience(Skill.AGILITY) > currentXP) {
				script.current_stage = Stage.WILDERNESS_AGILITY_LOG;
				Variables.WILDERNESS_STONES_CROSSED = true;
			} else {
				script.current_stage = Stage.WILDERNESS_AGILITY_STONES;
			}
		}
		return 100;
	}
}

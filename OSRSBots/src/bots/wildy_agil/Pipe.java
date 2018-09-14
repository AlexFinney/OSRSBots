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

public class Pipe extends Task {
	Camera camera;
	RS2Object PIPE = null;

	public Pipe(BabushkaScript apiIn) {
		super(apiIn);
		this.camera = apiIn.camera;
	}
	
	public Pipe(BabushkaScript apiIn, Stage stageRequirement) {
		super(apiIn, stageRequirement);
	}
	
	public boolean canExecute() {
		PIPE = Objects.WILDERNESS_AGILITY_COURSE_PIPE;
		return PIPE != null && super.canExecute();
	}


	@Override
	public int execute() {
		if (script.myPosition().distance(PIPE.getPosition()) > 2) {
			 this.script.getWalking().walk(PIPE.getArea(1).getRandomPosition());
		}else {
			if (PIPE == null) {
				PIPE = (RS2Object) script.objects.closest(new String[] { "Obstacle pipe" });
			}

			if (!PIPE.isVisible()) {
				this.camera.toEntity(PIPE);
			}

			if (PIPE.interact(new String[] { "Squeeze-through" })) {
				final int currentXP = this.script.skills.getExperience(Skill.AGILITY);
				new ConditionalSleep(8000, 500) {
					public boolean condition() throws InterruptedException {
						return script.skills.getExperience(Skill.AGILITY) > currentXP;
					}
				}.sleep();
				
				if(script.skills.getExperience(Skill.AGILITY) > currentXP) {
					Variables.XP_AT_LAP_START = currentXP;
					Variables.XP_EARNED += script.skills.getExperience(Skill.AGILITY) - currentXP;
					script.current_stage = Stage.WILDERNESS_AGILITY_ROPE;
				}
			}

		}
		return 100;
	}
}

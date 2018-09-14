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

public class Rope extends Task {
	RS2Object ROPE;
	Camera camera;
	public Rope(BabushkaScript scr) {
		super(scr);
	}
	
	public Rope(BabushkaScript scr, Stage s) {
		super(scr, s);
	}

	
	public boolean canExecute() {
		ROPE = Objects.WILDERNESS_AGILITY_COURSE_ROPE_SWING;
		return ROPE != null && super.canExecute();
	}

	public int execute() {
		if (!this.ROPE.isVisible() && camera != null) {
			this.camera.toEntity(this.ROPE);
		}
		
		if (this.ROPE.interact(new String[] { "Swing-on" })) {
			final int currentXP = this.script.skills.getExperience(Skill.AGILITY);
			new ConditionalSleep(8000, 500) {
				public boolean condition() throws InterruptedException {
					return script.skills.getExperience(Skill.AGILITY) > currentXP;
				}
			}.sleep();
			
			if(script.skills.getExperience(Skill.AGILITY) > currentXP) {
				script.current_stage = Stage.WILDERNESS_AGILITY_STONES;
			}else {
				script.current_stage = Stage.WILDERNESS_AGILITY_ESCAPE_FALL;
			}
			
			Variables.XP_EARNED += script.skills.getExperience(Skill.AGILITY) - currentXP;
			
			try {
				MethodProvider.sleep(MethodProvider.random(500, 900));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return random(100, 200);
	}
}

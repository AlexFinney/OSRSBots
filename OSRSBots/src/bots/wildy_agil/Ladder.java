package bots.wildy_agil;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.utility.ConditionalSleep;

import lib.BabushkaScript;
import lib.Camera;
import lib.globals.Objects;
import lib.task.Task;

public class Ladder extends Task {
	Camera camera;
	RS2Object LADDER = null;

	public Ladder(BabushkaScript apiIn, Camera cam) {
		super(apiIn);
		this.camera = cam;
	}

	public boolean canExecute() {
		LADDER = Objects.WIDLERNESS_AGILITY_COURSE_LADDER;
		return false;
	}

	public void runTask() {
		if (script.myPosition().distance(LADDER.getPosition()) > 3) {
			// this.api.getWalking().walkPath(Variables.ladderPath);
		} else {
			if (LADDER == null) {
				LADDER = (RS2Object) script.objects.closest(new String[] { "Ladder" });
			}

			if (!LADDER.isVisible()) {
				this.camera.toEntity(LADDER);
			}

			if (LADDER.interact(new String[] { "Climb-up" })) {
				new ConditionalSleep(10000, 500) {
					public boolean condition() throws InterruptedException {
						return (Objects.WILDERNESS_AGILITY_COURSE_ROCK_CLIMB != null) && (Ladder.this.script
								.myPosition().distance(Objects.WILDERNESS_AGILITY_COURSE_ROCK_CLIMB) < 100);
					}
				}.sleep();

			}

		}
	}

	@Override
	public int execute() {
		// TODO Auto-generated method stub
		return 0;
	}
}

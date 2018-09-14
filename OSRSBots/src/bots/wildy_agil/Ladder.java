package bots.wildy_agil;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import lib.BabushkaScript;
import lib.Camera;
import lib.globals.Objects;
import lib.globals.Stage;
import lib.globals.Variables;
import lib.task.Task;

public class Ladder extends Task {
	Camera camera;
	RS2Object LADDER = null;

	public Ladder(BabushkaScript apiIn) {
		super(apiIn);
	}
	
	public Ladder(BabushkaScript apiIn, Stage s) {
		super(apiIn, s);
	}

	public boolean canExecute() {
		LADDER = Objects.WIDLERNESS_AGILITY_COURSE_LADDER;
		script.log(LADDER == null);
		return LADDER != null && super.canExecute();
	}

	public int execute() {
		if(LADDER.getPosition().distance(script.myPosition()) > 7) {
			script.walking.walk(LADDER.getArea(1).getRandomPosition());
			
			return random(3000, 4000);
		}
		
		if (!LADDER.isVisible() && camera != null) {
			this.camera.toEntity(LADDER);
		}

		if (LADDER.interact(new String[] { "Climb-up" })) {
			new ConditionalSleep(10000, 500) {
				public boolean condition() throws InterruptedException {
					Objects.WILDERNESS_AGILITY_COURSE_ROCK_CLIMB = script.objects.closest("Pipe");
					
					return (Objects.WILDERNESS_AGILITY_COURSE_ROCK_CLIMB != null) && (Ladder.this.script.myPosition()
							.distance(Objects.WILDERNESS_AGILITY_COURSE_ROCK_CLIMB) < 100);
				}
			}.sleep();
			if(Variables.WILDERNESS_STONES_CROSSED) {
				script.current_stage = Stage.WILDERNESS_AGILITY_LOG;
			}else {
				script.walking.walk(new Area(new Position(3008, 3949, 0), new Position(3003, 3952, 0)));
				
				script.current_stage = Stage.WILDERNESS_AGILITY_ROPE;
				return 4000;
			}
			
		}
		
		return 100;
	}
	
}

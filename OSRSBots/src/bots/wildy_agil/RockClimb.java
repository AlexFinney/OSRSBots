package bots.wildy_agil;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

import lib.Camera;
import lib.globals.Variables;
import lib.task.Task;

public class RockClimb
  extends Task
{
  RS2Object ROCK_CLIMB;
  Camera camera;
  
  public RockClimb(MethodProvider apiIn, Camera cam)
  {
    super(apiIn);
    this.camera = cam;
  }
  
  public boolean canExecute()
  {
  }
  
  public int execute()
  {
    if (this.ROCK_CLIMB == null) {
      return 50;
    }
    if (!this.ROCK_CLIMB.isVisible()) {
      this.camera.toEntity(this.ROCK_CLIMB);
    }
    if (this.ROCK_CLIMB.interact(new String[] { "Climb" }))
    {
      final int currentXP = this.api.skills.getExperience(Skill.AGILITY);
      new ConditionalSleep(8000, 500)
      {
        public boolean condition()
          throws InterruptedException
        {
          return RockClimb.this.api.skills.getExperience(Skill.AGILITY) > currentXP;
        }
      }.sleep();
      try
      {
        MethodProvider.sleep(MethodProvider.random(200, 900));
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
      if (Variables.currentState != State.CLIMB_LADDER) {
        Variables.currentState = State.DO_PIPE;
      }
    }
  }
}

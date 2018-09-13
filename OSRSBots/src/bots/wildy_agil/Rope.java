package com.drizzybot.scripts.dwildy.tasks;

import com.drizzybot.scripts.dwildy.api.Camera;
import com.drizzybot.scripts.dwildy.api.Functions;
import com.drizzybot.scripts.dwildy.api.State;
import com.drizzybot.scripts.dwildy.api.Task;
import com.drizzybot.scripts.dwildy.data.Variables;
import org.osbot.rs07.api.Skills;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

public class Rope
  extends Task
{
  RS2Object rope;
  Camera camera;
  
  public Rope(MethodProvider apiIn, Camera cam)
  {
    super(apiIn);
    this.camera = cam;
  }
  
  public boolean validate()
  {
    return Variables.currentState == State.DO_ROPE;
  }
  
  public void runTask()
  {
    this.rope = Functions.findObject(this.api, "Ropeswing", "Swing-on");
    if (!this.rope.isVisible()) {
      this.camera.toEntity(this.rope);
    }
    if (this.rope.interact(new String[] { "Swing-on" }))
    {
      final int currentXP = this.api.skills.getExperience(Skill.AGILITY);
      new ConditionalSleep(8000, 500)
      {
        public boolean condition()
          throws InterruptedException
        {
          return Rope.this.api.skills.getExperience(Skill.AGILITY) > currentXP;
        }
      }.sleep();
      try
      {
        MethodProvider.sleep(MethodProvider.random(500, 900));
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
      if (Variables.currentState != State.CLIMB_LADDER) {
        Variables.currentState = State.DO_STEPPING_STONE;
      }
    }
  }
}

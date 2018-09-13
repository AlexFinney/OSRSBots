package lib;

import java.awt.Point;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;

public class Camera {
	private Script script;

	public Camera(Script s) {
		this.script = s;
	}

	public void moveNorth() {
		int r = Script.random(0, 30);
		if (r > 15) {
			r = 375 - r;
		}
		moveYaw(r);
	}

	public void moveWest() {
		moveYaw(75 + Script.random(0, 30));
	}

	public void moveSouth() {
		moveYaw(165 + Script.random(0, 30));
	}

	public void moveEast() {
		moveYaw(255 + Script.random(0, 30));
	}

	public int getLowestPitchAngle() {
		return this.script.getCamera().getLowestPitchAngle();
	}

	public int getX() {
		return this.script.getCamera().getX();
	}

	public int getY() {
		return this.script.getCamera().getY();
	}

	public int getZ() {
		return this.script.getCamera().getZ();
	}

	public int getYawAngle() {
		return this.script.getCamera().getYawAngle();
	}

	public int getPitchAngle() {
		return this.script.getCamera().getPitchAngle();
	}

	public boolean isDefaultScaleZ() {
		return this.script.getCamera().isDefaultScaleZ();
	}

	public boolean movePitch(int pitch) {
		moveCamera(getYawAngle(), pitch);
		return true;
	}

	public boolean moveYaw(int yaw) {
		moveCamera(yaw, getPitchAngle());
		return true;
	}

	public boolean toBottom() {
		return movePitch(0);
	}

	public boolean toTop() {
		return movePitch(67);
	}

	public void toEntity(Entity e) {
		toEntity(this.script.myPosition(), e);
	}

	public void toEntity(Position origin, Entity e) {
		if (e == null) {
			return;
		}
		moveCamera(getAngleTo(origin, e.getPosition()), getPitchTo(origin, e.getPosition()));
	}

	public void toPosition(Position origin, Position position) {
		if (position.distance(this.script.myPosition()) > 16) {
			return;
		}
		moveCamera(getAngleTo(origin, position), getPitchTo(origin, position));
	}

	public void toPosition(Position Position) {
		toPosition(this.script.myPosition(), Position);
	}

	public void moveCamera(int yaw, int pitch) {
		if (pitch > 67) {
			pitch = 67;
		} else if (pitch < 22) {
			pitch = 22;
		}
		int pitchCur = getPitchAngle();
		int yawCur = getYawAngle();
		int pitchDir = pitch < pitchCur ? -1 : 1;
		int pitchDiff = Math.abs(pitch - pitchCur);
		int yawDir = yaw > yawCur ? -1 : 1;
		int yawDiff = Math.abs(yaw - yawCur);
		if (yawDiff > 180) {
			yawDiff = 360 - yawDiff;
			yawDir *= -1;
		}
		if ((yawDiff < 22) && (pitchDiff < 14)) {
			return;
		}
		int x = yawDir * yawDiff * 3;
		int y = pitchDir * pitchDiff * 3;

		int minX = 40 - (yawDir == -1 ? x : 0);
		int maxX = '?' - (yawDir == 1 ? x : 0);
		int minY = 40 + (pitchDir == -1 ? y : 0);
		int maxY = '?' + (pitchDir == 1 ? y : 0);

		Point mp = this.script.getMouse().getPosition();
		for (int i = 0; (i < 5) && (!this.script.getMouse().isOnScreen()); i++) {
			this.script.getMouse().move(Script.random(minX, maxX), Script.random(minY, maxY));
			sleep(5, 50);
		}
		if ((mp.x < minX) || (mp.x > maxX) || (mp.y < minY) || (mp.y > maxY)) {
			this.script.getMouse().move(Script.random(minX, maxX), Script.random(minY, maxY));
			sleep(5, 50);
		}
		mousePress(true);

		mp = this.script.getMouse().getPosition();

		int newX = Math.min(764, Math.max(0, mp.x + x));
		int newY = Math.min(502, Math.max(0, mp.y + y));

		this.script.getMouse().move(newX, newY);

		sleep(5, 50);

		mousePress(false);
	}

	private void sleep(int i, int j) {
		try {
			Script.sleep(Script.random(i, j));
		} catch (InterruptedException localInterruptedException) {
		}
	}

	private int getPitchTo(Position origin, Position t) {
		int pitch = 67 - t.distance(origin) * 5;
		if (pitch > 67) {
			pitch = 67;
		} else if (pitch < 22) {
			pitch = 22;
		}
		return pitch;
	}

	private int getAngleTo(Position origin, Position Position) {
		int degree = (int) Math.toDegrees(Math.atan2(Position.getY() - origin.getY(), Position.getX() - origin.getX()));
		int a = ((degree >= 0 ? degree : 360 + degree) - 90) % 360;
		return a < 0 ? a + 360 : a;
	}

	private void mousePress(boolean press) {
		this.script.getBot().getMouseEventHandler().generateBotMouseEvent(press ? 501 : 502, System.currentTimeMillis(),
				0, this.script.getMouse().getPosition().x, this.script.getMouse().getPosition().y, 1, false, 2, true);
	}
}

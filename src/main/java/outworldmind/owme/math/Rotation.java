package outworldmind.owme.math;

public class Rotation extends Vector3 {
	
	public Rotation() {
		super();
	}
	
	public Rotation(float roll, float yaw, float pitch) {
		super(roll, yaw, pitch);
	}
	
	public Rotation(Rotation rotation) {
		super(rotation);
	}
	
	public Rotation(Vector3 rotation) {
		super(rotation);
	}
	
	public float getRoll() {
		return x;
	}
	
	public void setRoll(float roll) {
		x = roll;
	}
	
	public float getYaw() {
		return y;
	}
	
	public void setYaw(float yaw) {
		y = yaw;
	}
	
	public float getPitch() {
		return z;
	}
	
	public void setPitch(float pitch) {
		z = pitch;
	}

}

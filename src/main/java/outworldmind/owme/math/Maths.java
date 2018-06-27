package outworldmind.owme.math;

public class Maths {
	
	public static final float LOSS_OF_PRECISION = 0.0001f;
	
	public static boolean equal(float a, float b) {
		return ((a - b) <= LOSS_OF_PRECISION);
	}

	public static Matrix4 createTransformationMatrix(Vector2 translation, Vector2 scale) {
		Matrix4 matrix = new Matrix4();
		matrix.setIdentity();
		matrix.translate(translation);
		matrix.scale(new Vector3(scale.x, scale.y, 1f));
		return matrix;
	}

	public static float barryCentric(Vector3 p1, Vector3 p2, Vector3 p3, Vector2 pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static Matrix4 createTransformationMatrix(Vector3 translation, Rotation rotation, float scale) {
		Matrix4 matrix = new Matrix4();
		matrix.translate(translation);
		matrix.rotate((float) Math.toRadians(rotation.getPitch()), new Vector3(1, 0, 0));
		matrix.rotate((float) Math.toRadians(rotation.getYaw()), new Vector3(0, 1, 0));
		matrix.rotate((float) Math.toRadians(rotation.getPitch()), new Vector3(0, 0, 1));
		matrix.scale(new Vector3(scale, scale, scale));
		return matrix;
	}

	public static Matrix4 createViewMatrix(Vector3 position, Rotation rotation) {
		Matrix4 viewMatrix = new Matrix4();
		viewMatrix.setIdentity();
		viewMatrix.rotate((float) Math.toRadians(rotation.getPitch()), new Vector3(1, 0, 0));
		viewMatrix.rotate((float) Math.toRadians(rotation.getYaw()), new Vector3(0, 1, 0));
		viewMatrix.rotate((float) Math.toRadians(rotation.getRoll()), new Vector3(0, 0, 1));
		Vector3 negativeCameraPos = new Vector3(position.clone().negate());
		viewMatrix.translate(negativeCameraPos);
		return viewMatrix;
	}

	public static float sqr(float a) {
		return a * a;
	}
	
	public static int sqr(int a) {
		return a * a;
	}
	
	public static float cube(float a) {
		return a * a * a;
	}
	
	public static int cube(int a) {
		return a * a * a;
	}

	public static void swapFloat(float a, float b) {
		float c = a;
		a = b;
		b = c;
	}

	/**
	 * Distance between 2 points in 3D
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	public static float distance2Points(Vector3 point1, Vector3 point2) {
		float distance = 0;
		distance = Maths.sqr(point1.x - point2.x);
		distance += Maths.sqr(point1.y - point2.y);
		distance += Maths.sqr(point1.z - point2.z);
		distance = (float) Math.sqrt(distance);
		return distance;
	}

	/**
	 * Distance between 2 points in 2D
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	public static float distance2Points(Vector2 point1, Vector2 point2) {
		float distance = 0;
		distance = Maths.sqr(point1.x - point2.x);
		distance += Maths.sqr(point1.y - point2.y);
		distance = (float) Math.sqrt(distance);
		return distance;
	}

	/**
	 * Distance between line and point in 2D
	 * 
	 * @param point
	 * @param linePoint1
	 * @param linePoint2
	 * @return
	 */
	public static float distanceLineAndPoint(Vector2 point, Vector2 linePoint1, Vector2 linePoint2) {
		float x = point.x;
		float y = point.y;

		float x1 = linePoint1.x;
		float y1 = linePoint1.y;

		float x2 = linePoint2.x;
		float y2 = linePoint2.y;

		/* exception if x2 - x1 = 0 */
		float difX = x2 - x1;
		if (difX == 0) {
			difX = 0.01f;
		}

		float k = (y2 - y1) / (difX);
		float b = y1 - k * x1;

		float xH = (y * k + x - b * k) / (sqr(k) + 1);
		float yH = k * xH + b;

		Vector2 vecH = new Vector2(xH, yH);

		return distance2Points(point, vecH);
	}

	/**
	 * @homelleon "tailOfDivisionNoReminder" function to find tail number that
	 * is got after dividing one number on an other without reminder
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static int tailOfDivisionNoReminder(int value1, int value2) {
		return (value2 == 0) ? 0 : value1 - (int) Math.floor(value1 / value2) * value2;
	}
	
	/**
	 * Check if points is on ray
	 * 
	 * @param point
	 * @param ray
	 * @return
	 */
	public static boolean pointIsOnRay(Vector3 point, Vector3 ray) {
		boolean isOnRay = false;

		float x = point.x;
		float y = point.y;
		float z = point.z;

		float p1 = ray.x;
		float p2 = ray.y;
		float p3 = ray.z;

		float oX = x / p1;
		float oY = y / p2;
		float oZ = z / p3;

		if ((oX) == (oY)) {
			if ((oY) == (oZ)) {
				isOnRay = true;
			}
		}
		return isOnRay;
	}

	public static boolean pointIsInQuad(Vector2 point, Vector2 minQuadPoint, Vector2 maxQuadPoint) {
		return (point.x >= minQuadPoint.x) &&
				(point.x <= maxQuadPoint.x) &&
				(point.y >= minQuadPoint.y) &&
				(point.y <= maxQuadPoint.y);
		
	}
	
	public static boolean pointIsInCube(Vector3 point, Vector3 minCubePoint, Vector3 maxCubePoint) {
		return (point.x >= minCubePoint.x) &&
				(point.y >= minCubePoint.y) &&
				(point.z >= minCubePoint.z) &&
				(point.x < maxCubePoint.x) &&
				(point.y < maxCubePoint.y) &&
				(point.z < maxCubePoint.z);
	}
	
	public static int compare(Vector2 a, Vector2 b) {
		if (a == b || (equal(a.x, b.x) && equal(a.y, b.y))) return 0;
		if (equal(a.x, b.x)) {
			if (a.y < b.y) return -1;
		} else if (a.x < b.x)
			return -1;
		
		return 1;
	}

	public static int compare(Vector3 a, Vector3 b) {
		if (a == b || (equal(a.x, b.x) && equal(a.y, b.y) && equal(a.z, b.z)))
			return 0;
		else if (equal(a.x, b.x)) {
			if (equal(a.z, b.z)) {
				if (a.y < b.y) return -1;
			} else if (equal(a.z, b.z))
				return -1;
		} else if (a.x < b.x)
			return -1;
		return 1;
	}

}

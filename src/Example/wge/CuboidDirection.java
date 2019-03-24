package Example.wge;

public enum CuboidDirection {
	North,South,East,West,Down,Up,Unknown;
	public CuboidDirection opposite() {
		switch(this) {
		case North:
			return South;
		case East:
			return West;
		case South:
			return North;
		case West:
			return East;
		case Up:
			return Down;
		case Down:
			return Up;
		default:
			return Unknown;
		}
	}
}
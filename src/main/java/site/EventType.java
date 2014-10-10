package site;

public enum EventType {
    SoulCrystal(1, "SC"), MountWhip(2, "Whip"), Mahra(3, "Mahra"), Sepulcrum(4,
	    "Seps");

    private int id;
    private String dbName;

    private EventType(int id, String name) {
	this.id = id;
	this.dbName = name;
    }

    public int getID() {
	return id;
    }

    public String getDatabaseName() {
	return dbName;
    }

    public String getNiceName() {
	switch (this) {
	case SoulCrystal:
	    return "Soul Crystal";
	case MountWhip:
	    return "Mount Training Whip";
	case Mahra:
	    return "Mahra";
	case Sepulcrum:
	    return "Sepulcrum";
	default:
	    return "INVALID";
	}
    }
}

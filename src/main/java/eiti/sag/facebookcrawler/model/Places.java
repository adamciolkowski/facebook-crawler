package eiti.sag.facebookcrawler.model;

public class Places {

    private final Location currentCity;
    private final Location hometown;

    public Places(Location currentCity, Location hometown) {
        this.currentCity = currentCity;
        this.hometown = hometown;
    }

    public Location getCurrentCity() {
        return currentCity;
    }

    public Location getHometown() {
        return hometown;
    }

    @Override
    public String toString() {
        return "Places{" +
                "currentCity=" + currentCity +
                ", hometown=" + hometown +
                '}';
    }
}

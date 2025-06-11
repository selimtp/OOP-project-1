package game;

import model.User;

public abstract class Game {
    protected String name;
    protected boolean isActive;

    public Game(String name) {
        this.name = name;
        this.isActive = true;
    }

    public String getName() {
        return name;
    }
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public abstract boolean play(User user, double betAmount);

    @Override
    public String toString() {
        return name + " - " + (isActive ? "Active" : "Disabled");
    }
}

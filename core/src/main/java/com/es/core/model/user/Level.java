package com.es.core.model.user;

import java.util.Objects;

public class Level {
    private int number;
    private long minXP;
    private long maxXP;

    public Level() {
    }

    public Level(int number, long minXP, long maxXP) {
        this.number = number;
        this.minXP = minXP;
        this.maxXP = maxXP;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getMinXP() {
        return minXP;
    }

    public void setMinXP(long minXP) {
        this.minXP = minXP;
    }

    public long getMaxXP() {
        return maxXP;
    }

    public void setMaxXP(long maxXP) {
        this.maxXP = maxXP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Level)) return false;
        Level level = (Level) o;
        return number == level.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}

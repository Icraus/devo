package com.icraus.devo;

import java.util.Objects;

public class Pose {
    private char orient;
    private int y;
    private int x;

    public Pose(int startX, int startY, char startOrient) {
        setX(startX);
        setY(startY);
        setOrient(startOrient);
    }


    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public void setOrient(char orient) {
        this.orient = orient;
    }

    public char getOrient() {
        return this.orient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pose pose = (Pose) o;
        return getOrient() == pose.getOrient() && getY() == pose.getY() && getX() == pose.getX();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrient(), getY(), getX());
    }

    @Override
    public String toString() {
        return "Pose{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", orient='" + getOrient() +
                "'}";
    }
}

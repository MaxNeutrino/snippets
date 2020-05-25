package io.github.maxneutrino.webfluxwebsockets.model;

import java.util.Objects;

public class Event {

    private String name;

    private int count;

    public Event() {}

    public Event(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return count == event.count &&
                Objects.equals(name, event.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count);
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}

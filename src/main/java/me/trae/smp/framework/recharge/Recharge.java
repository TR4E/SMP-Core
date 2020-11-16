package me.trae.smp.framework.recharge;

public final class Recharge {

    private final String name;
    private final long duration, systime;
    private boolean inform, expired;

    public Recharge(final String name, final long duration) {
        this.name = name;
        this.duration = duration;
        this.systime = System.currentTimeMillis();
        this.expired = false;
    }

    public Recharge(final String name, final long duration, final boolean inform) {
        this.name = name;
        this.duration = duration;
        this.systime = System.currentTimeMillis();
        this.inform = inform;
        this.expired = false;
    }

    public final String getName() {
        return name;
    }

    public final long getDuration() {
        return duration;
    }

    public final long getSystime() {
        return systime;
    }

    public final boolean isInform() {
        return inform;
    }

    public void setInform(final boolean inform) {
        this.inform = inform;
    }

    public final long getRemaining() {
        return (duration + systime - System.currentTimeMillis());
    }

    public final boolean hasExpired() {
        return (getRemaining() <= 0);
    }

    public final boolean isExpired() {
        return expired;
    }

    public void setExpired(final boolean expired) {
        this.expired = expired;
    }
}
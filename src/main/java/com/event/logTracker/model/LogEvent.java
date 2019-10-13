package com.event.logTracker.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "logtracer")
public class LogEvent implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private long span;

    private String type;

    private String host;

    private boolean alert;

    @Transient
    private Long timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSpan() {
        return span;
    }

    public void setSpan(long span) {
        this.span = span;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEvent event = (LogEvent) o;
        return span == event.span &&
                alert == event.alert &&
                id.equals(event.id) &&
                Objects.equals(type, event.type) &&
                Objects.equals(host, event.host) &&
                timestamp.equals(event.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, span, type, host, alert, timestamp);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + id +
                ", eventDuration='" + span + '\'' +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", alert=" + alert +
                ", timestamp=" + timestamp +
                '}';
    }
}

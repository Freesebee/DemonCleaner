package com.example.demoncleaner.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "streak")
public class Streak {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date startDate;
    private Date endDate;
    private String comment;

    public int getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getComment() { return comment; }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    public void setEndDate(Date date) {
        this.endDate = date;
    }

    public void setComment(String comment) { this.comment = comment; }
}

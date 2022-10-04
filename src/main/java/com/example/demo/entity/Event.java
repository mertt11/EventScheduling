package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="eventdata")
public class Event {
    @Id
    @Column(name="event_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "text")
    private String text;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_enrolled",
            joinColumns = @JoinColumn(name="event_id"),
            inverseJoinColumns = @JoinColumn(name="modelUser_id"))
    private Set<ModelUser> enrolledUsers=new HashSet<>();
    @JsonFormat(pattern="yyyy.MM.dd HH:mm:ss")
    private Date date;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "createdByUserid")
    private ModelUser createdBy;

    public Event(Long id,String title, String text, Date date) {
        this.id=id;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public String toString(){
        return "Your event: "+"\""+text+"\""+" is due: "+date;
    }

    public Event(){}

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<ModelUser> getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(Set<ModelUser> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }

    public ModelUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ModelUser createdBy) {
        this.createdBy = createdBy;
    }


}
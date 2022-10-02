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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "text")
    private String text;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)//bos birak
    @JoinTable(name="user_enrolled",
            joinColumns = @JoinColumn(name="event_id"),
            inverseJoinColumns = @JoinColumn(name="modelUser_id"))
    private Set<ModelUser> enrolledUsers=new HashSet<>();
    @JsonFormat(pattern="yyyy.MM.dd HH:mm:ss")
    private Date date;

    public void enrollUser(ModelUser user){
        enrolledUsers.add(user);
    }

    public void rmvUser(long userId){
        ModelUser usr=this.enrolledUsers.stream().filter(t->t.getId()==userId).findFirst().orElse(null);
        if(usr!=null){
            this.enrolledUsers.remove(usr);
            usr.getEvents().remove(this);
        }
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
}
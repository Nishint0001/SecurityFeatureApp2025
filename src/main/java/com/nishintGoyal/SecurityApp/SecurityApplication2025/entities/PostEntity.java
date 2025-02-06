package com.nishintGoyal.SecurityApp.SecurityApplication2025.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "Posts")
public class PostEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;


    public PostEntity()
    {

    }
    public PostEntity(Long id, String title, String description)
    {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Long getId()
    {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

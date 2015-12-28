package com.crystal.model.entities.audit;

import com.crystal.model.entities.catalog.Area;

import javax.persistence.*;
import javax.xml.stream.events.Comment;

@Entity
@Table(name="comment_area_rel")
public class CommentAreaRel {

    @Id
    @GeneratedValue
    @Column(name = "id_comment_area_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comment", nullable = false)
    private Comment comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}

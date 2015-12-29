package com.crystal.model.entities.audit;

import javax.persistence.*;

@Entity
@Table(name="comment_extension_rel")
public class CommentExtensionRel {

    @Id
    @GeneratedValue
    @Column(name = "id_comment_extension_rel")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comment", nullable = false)
    private Comment comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_extension", nullable = false)
    private Extension extension;

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

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }
}

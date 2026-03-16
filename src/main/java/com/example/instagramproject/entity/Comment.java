package com.example.instagramproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_gen"
    )
    @SequenceGenerator(
            name = "comment_gen",
            sequenceName = "comment_seq",
            allocationSize = 1)
    Long id;
    String comment;
    LocalDate createdAt;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    User user;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    Post post;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    List<Like> likes;

}

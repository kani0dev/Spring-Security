package kani.springsecurity.Domain.Profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kani.springsecurity.Domain.Tags.Tag;
import kani.springsecurity.Domain.Users.Users;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_profiles")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
    @Id
    private Long userId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId@JoinColumn(name = "user_id")
    private Users user;

    @Column(length = 256)
    private String bio;

    @Column(name = "favorite_animal", length = 256)
    private String favoriteAnimal;

    @Column(name = "magic_place", length = 256)
    private String magicPlace;

    private Integer age;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "profile_tags",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

}

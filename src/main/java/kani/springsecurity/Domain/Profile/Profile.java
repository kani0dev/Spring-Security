package kani.springsecurity.Domain.Profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kani.springsecurity.Domain.Tags.Tag;
import kani.springsecurity.Domain.Users.UserRepository;
import kani.springsecurity.Domain.Users.Users;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
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

    @Column(name = "location")
    private String location;

    @Column(name = "occupation")
    private String ocupation;

    private String interests;

    @ManyToMany
    @JoinTable(
            name = "profile_tags",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Set<Tag> thisProfileTags(){
        return this.getTags();
    }
    public Set<Tag> addTagToProfile(Tag tag){
        if (tags.contains(tag)){
            return null;
        }
        tags.add(tag);
        return tags;
    }
    public Set<Tag> rmTagFromProfile(Tag tag){
        if (tags.contains(tag)){
            return null;
        }
        tags.add(tag);
        return tags;
    }
}

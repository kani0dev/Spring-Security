package kani.springsecurity.Domain.UserPreferences;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kani.springsecurity.Domain.Profile.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "profile_tags")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Preferences {
    @Id
    private Long profile_id;
    private Long tag_id;


}

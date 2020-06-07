package ru.olegraskin.sugateway.profile.model;

import lombok.*;
import ru.olegraskin.sugateway.user.model.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * true = visible for everyone
     * false = visible only for responsible users
     */
    private boolean visibility = true;

    /**
     * Custom user profile status
     */
    private String status;

    @EqualsAndHashCode.Exclude
    @ManyToMany(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "whitelist_profile_users",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> whitelist = new HashSet<>();

//    private Map<String, String> linkedProfiles;
}

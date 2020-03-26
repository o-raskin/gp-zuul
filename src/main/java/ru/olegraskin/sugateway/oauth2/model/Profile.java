package ru.olegraskin.sugateway.oauth2.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
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

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "followers_profile_users",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> followers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "whitelist_profile_users",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> whitelist = new HashSet<>();

//    private Map<String, String> linkedProfiles;
}

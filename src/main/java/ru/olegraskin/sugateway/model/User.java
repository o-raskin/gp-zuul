package ru.olegraskin.sugateway.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.olegraskin.sugateway.security.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    @Size(min = 6)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "position_id")
    private Position position;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "mentor_id")
    private User mentor;

    @EqualsAndHashCode.Exclude
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<User> following = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany(
            mappedBy = "following",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private Set<User> userFollowers = new HashSet<>();

//    @EqualsAndHashCode.Exclude
//    @ManyToMany(
//            cascade = CascadeType.PERSIST,
//            fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "whitelist_profile_users",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "profile_id")
//    )
//    private Set<Profile> accessibleProfiles = new HashSet<>();

    private boolean active = true;

    private LocalDate lastPromotionDate;

    private LocalDate futurePromotionDate;

    private LocalDate inCompanySince;

    public void addFollower(User user) {
        this.userFollowers.add(user);
    }

    public void removeFollower(User user) {
        this.userFollowers.remove(user);
    }

    public void addFollowing(User user) {
        this.following.add(user);
    }

    public void removeFollowing(User user) {
        this.following.remove(user);
    }

}

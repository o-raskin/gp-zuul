package ru.olegraskin.sugateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.olegraskin.sugateway.model.Profile;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select p from Profile p where p.user.id = :usedId")
    Optional<Profile> findProfileByUserId(long usedId);
}

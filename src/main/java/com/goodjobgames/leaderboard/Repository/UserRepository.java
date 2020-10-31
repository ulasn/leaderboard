package com.goodjobgames.leaderboard.Repository;

import com.goodjobgames.leaderboard.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByName(String name);

    List<User> findByCountry(String country);

    Iterable<User> findAllById(Iterable<String> ids);

}

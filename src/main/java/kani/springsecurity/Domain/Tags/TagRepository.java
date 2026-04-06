package kani.springsecurity.Domain.Tags;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
   Optional<Tag> findByNome(String tag);
}

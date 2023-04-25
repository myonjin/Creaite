package D6B.D_discover_user.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoveRepository extends JpaRepository<Love, Long> {
    Optional<Love> findByUserIdAndPictureId(Long userId, Long pictureId);
}

package kr.hbcho90.teststudy.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, String> {
    Page<PostEntity> findAllByUserId(Pageable pageable, String userId);

}

package me.gyuri.tripity.domain.comment.repository;

import me.gyuri.tripity.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

package com.jk.module_lecture_resource.lecture_note.repository;

import com.jk.module_lecture_resource.lecture_note.entity.LectureNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureNoteRepository extends JpaRepository<LectureNote, Long> {
}
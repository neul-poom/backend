package com.jk.module_lecture_resource.multimedia.repository;

import com.jk.module_lecture_resource.lecture_note.entity.LectureNote;
import com.jk.module_lecture_resource.multimedia.entity.Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {
    Multimedia findByLectureId(Long lectureId);
}
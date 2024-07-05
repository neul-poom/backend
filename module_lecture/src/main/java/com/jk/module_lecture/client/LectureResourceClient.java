package com.jk.module_lecture.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "lectureResourceClient", url = "${feign.lectureResourceClient.url}")
public interface LectureResourceClient {
    /**
     * 강의 노트 생성 요청
     */
    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/internal/lectures/notes", consumes = "application/json")
    void create(@RequestParam("file") String file, @RequestParam("") Long lectureId);

}

package com.jk.module_lecture.client;

import com.jk.module_lecture.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "lectureResourceClient", url = "${feign.lectureResourceClient.url}", configuration = FeignConfig.class)
public interface LectureResourceClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/internal/lectures/notes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void createNote(@RequestPart("file") MultipartFile file, @RequestParam("lectureId") Long lectureId, @RequestParam("title") String title);

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/internal/lectures/multimedia", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void createVideo(@RequestPart("file") MultipartFile file, @RequestParam("lectureId") Long lectureId);

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/internal/lectures/notes/{lectureId}", consumes = "application/json")
    String getNoteUrl(@PathVariable("lectureId") Long noteId);

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/internal/lectures/multimedia/{lectureId}", consumes = "application/json")
    String getVideoRul(@PathVariable("lectureId") Long videoId);
}
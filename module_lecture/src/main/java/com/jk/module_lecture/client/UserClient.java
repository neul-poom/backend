package com.jk.module_lecture.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userClient", url = "${feign.userClient.url}")
public interface UserClient {
    /**
     * 유저 아이디 요청(토큰 값 전송)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/internal/lectures/notes", consumes = "application/json")
    void create(@RequestParam("file") String file, @RequestParam("") Long lectureId);

}

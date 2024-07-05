package com.jk.module_lecture_resource.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "lectureClient", url = "${feign.lectureClient.url}")
public interface LectureClient {

}

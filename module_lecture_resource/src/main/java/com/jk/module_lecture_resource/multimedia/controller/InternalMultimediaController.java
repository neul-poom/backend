package com.jk.module_lecture_resource.multimedia.controller;

import com.jk.module_lecture_resource.multimedia.service.MultimediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/lectures/multimedia")
public class InternalMultimediaController {
    public final MultimediaService multimediaService;
}

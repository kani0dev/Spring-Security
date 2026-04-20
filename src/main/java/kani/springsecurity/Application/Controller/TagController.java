package kani.springsecurity.Application.Controller;

import kani.springsecurity.Domain.Tags.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Provider;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService service;

    @GetMapping("")
    public ResponseEntity<Map<String, List<String>>> teste(){
        Map<String, List<String>> stringListMap = service.getTagsByCategorie();
        System.out.println(stringListMap);
        return  ResponseEntity.ok(stringListMap);
    }
}

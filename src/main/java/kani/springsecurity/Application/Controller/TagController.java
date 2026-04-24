package kani.springsecurity.Application.Controller;

import kani.springsecurity.Application.Controller.Request.TagRequest;
import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Tags.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{id}")
    public ResponseEntity<?> addATagToProfile(@PathVariable Long id,@RequestBody TagRequest request) throws Exception {
        try {
            Profile profileWithAddedTag = service.addTagToProfile(id, request);
            return  ResponseEntity.ok(ProfileResponse.ToResponse(profileWithAddedTag));
        }catch (Exception e ){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

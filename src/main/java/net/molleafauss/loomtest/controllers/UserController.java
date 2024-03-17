package net.molleafauss.loomtest.controllers;

import lombok.RequiredArgsConstructor;
import net.molleafauss.loomtest.controllers.models.SampleData;
import net.molleafauss.loomtest.controllers.models.UserRequest;
import net.molleafauss.loomtest.data.models.User;
import net.molleafauss.loomtest.service.ScoreService;
import net.molleafauss.loomtest.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ScoreService scoreService;

    @PostMapping(value = "/user/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody UserRequest userRequest) {
        var score = scoreService.getScore(userRequest.name());
        var user = User.builder()
                .name(userRequest.name())
                .tag(userRequest.tag())
                .userScore(score.value())
                .build();
        userService.createUser(user);
    }

    @GetMapping("/country")
    public SampleData getFromCache() {
        return userService.getSampleData();
    }
}

package flowgraph.controller;

import org.springframework.web.bind.annotation.*;
import flowgraph.blackboard.BlackboardService;
import flowgraph.model.BlackboardRequest;
import flowgraph.model.BlackboardResponse;

@RestController
@RequestMapping("/api/blackboard")
public class BlackboardController {

    private final BlackboardService blackboardService;

    public BlackboardController(BlackboardService blackboardService) {
        this.blackboardService = blackboardService;
    }

    @PostMapping
    public BlackboardResponse handle(@RequestBody BlackboardRequest request) {
        return blackboardService.handle(request);
    }
}
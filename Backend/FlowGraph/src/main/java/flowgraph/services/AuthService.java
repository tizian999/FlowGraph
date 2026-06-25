package flowgraph.services;

import flowgraph.model.FlowGraphFunction;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("AuthService")
public class AuthService {

    @FlowGraphFunction("login")
    public Map<String, Object> login(Map<String, Object> input) {

        String username = String.valueOf(input.get("username"));
        String password = String.valueOf(input.get("password"));

        if ("admin".equals(username) && "admin".equals(password)) {
            return Map.of("token", "test-token");
        }

        throw new RuntimeException("Login fehlgeschlagen");
    }
}
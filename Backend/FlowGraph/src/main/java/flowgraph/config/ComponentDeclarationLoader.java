package flowgraph.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;

@Component
public class ComponentDeclarationLoader {

    private List<Map<String, Object>> components;

    @PostConstruct
    public void load() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            File file = new File("src/main/java/flowgraph/config/ComponentDeclaration.json");
            if (!file.exists()) {
                file = new File("config/ComponentDeclaration.json");
            }

            Map<String, Object> root = mapper.readValue(file, Map.class);
            components = (List<Map<String, Object>>) root.get("components");

            System.out.println("Geladene Components: " + components);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Laden der ComponentDeclaration.json", e);
        }
    }

    public Map<String, Object> findById(String id) {
        return components.stream()
                .filter(c -> id.equals(c.get("id")))
                .findFirst()
                .orElse(null);
    }
}
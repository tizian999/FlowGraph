package flowgraph.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;

@Component
public class DependencyLoader {

    private List<Map<String, Object>> dependencies;

    @PostConstruct
    public void load() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            File file = new File("src/main/java/flowgraph/config/Dependency.json");
            if (!file.exists()) {
                file = new File("config/Dependency.json");
            }

            Map<String, Object> root = mapper.readValue(file, Map.class);
            dependencies = (List<Map<String, Object>>) root.get("dependencies");

            System.out.println("Geladene Dependencies: " + dependencies);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Laden der Dependency.json", e);
        }
    }

    public Map<String, Object> findBySource(String source) {
        return dependencies.stream()
                .filter(d -> source.equals(d.get("source")))
                .findFirst()
                .orElse(null);
    }
}
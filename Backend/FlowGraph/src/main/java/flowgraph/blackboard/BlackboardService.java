package flowgraph.blackboard;

import flowgraph.model.FlowGraphFunction;
import flowgraph.config.ComponentDeclarationLoader;
import flowgraph.config.DependencyLoader;
import flowgraph.model.BlackboardRequest;
import flowgraph.model.BlackboardResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Map;

@Service
public class BlackboardService {

    private final DependencyLoader dependencyLoader;
    private final ComponentDeclarationLoader componentDeclarationLoader;
    private final ApplicationContext applicationContext;

    public BlackboardService(DependencyLoader dependencyLoader,
                             ComponentDeclarationLoader componentDeclarationLoader,
                             ApplicationContext applicationContext) {
        this.dependencyLoader = dependencyLoader;
        this.componentDeclarationLoader = componentDeclarationLoader;
        this.applicationContext = applicationContext;
    }

    public BlackboardResponse handle(BlackboardRequest request) {

        if (request.getCapability() == null || request.getCapability().isBlank()) {
            return new BlackboardResponse(false, null, null, "Capability fehlt");
        }

        BlackboardBoard board = new BlackboardBoard();

        board.putDone("request", request);
        board.putDone("LoginRequest", request.getPayload());

        try {
            Map<String, Object> dependency =
                    dependencyLoader.findBySource(request.getCapability());

            if (dependency == null) {
                return new BlackboardResponse(
                        false,
                        request.getCapability(),
                        null,
                        "Keine Dependency gefunden für: " + request.getCapability()
                );
            }

            String componentId = String.valueOf(dependency.get("source"));
            String requiredSchema = String.valueOf(dependency.get("requires"));
            String providedSchema = String.valueOf(dependency.get("provides"));

            Object inputData = board.getValue(requiredSchema);

            if (inputData == null) {
                throw new RuntimeException("Benötigte Daten fehlen auf dem Blackboard: " + requiredSchema);
            }

            Map<String, Object> component =
                    componentDeclarationLoader.findById(componentId);

            if (component == null) {
                throw new RuntimeException("Keine ComponentDeclaration gefunden für: " + componentId);
            }

            String functionName = String.valueOf(component.get("function"));

            Object result = executeFunction(functionName, inputData);

            board.putDone(providedSchema, result);

            return new BlackboardResponse(
                    true,
                    request.getCapability(),
                    result,
                    "Blackboard Routing erfolgreich ausgeführt"
            );

        } catch (Exception e) {
            board.putFailed("workflow", e.getMessage());

            return new BlackboardResponse(
                    false,
                    request.getCapability(),
                    board.getEntries(),
                    "Blackboard Routing fehlgeschlagen: " + e.getMessage()
            );
        }
    }

    private Object executeFunction(String functionName, Object inputData) throws Exception {

        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanName);

            for (Method method : bean.getClass().getMethods()) {
                FlowGraphFunction annotation =
                        method.getAnnotation(FlowGraphFunction.class);

                if (annotation == null) {
                    continue;
                }

                if (!annotation.value().equals(functionName)) {
                    continue;
                }

                return method.invoke(bean, inputData);
            }
        }

        throw new RuntimeException("Keine FlowGraphFunction gefunden für: " + functionName);
    }
}
package dev.buno.app;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@RestController @RequestMapping("/api/agents")
public class AgentController {
 private final AegisRuntimeClient aegis;
 private final Map<String,List<Map<String,Object>>> sessions=new ConcurrentHashMap<>();
 public AgentController(AegisRuntimeClient aegis){this.aegis=aegis;}
 public record RunRequest(@NotBlank String sessionId,@NotBlank String prompt,String persona){}
 @PostMapping("/run") public Map<String,Object> run(@Valid @RequestBody RunRequest r){
  String persona=(r.persona()==null||r.persona().isBlank())?"assistant":r.persona();
  String answer=aegis.complete("Você é um agente Buno Aegis 2. Persona: "+persona+". Respeite o princípio do menor privilégio.",r.prompt());
  Map<String,Object> item=Map.of("role","assistant","content",answer,"createdAt",Instant.now().toString());
  sessions.computeIfAbsent(r.sessionId(),k->Collections.synchronizedList(new ArrayList<>())).add(item);
  return Map.of("sessionId",r.sessionId(),"persona",persona,"answer",answer,"runtime","buno-aegis-2");
 }
 @GetMapping("/sessions/{id}") public List<Map<String,Object>> history(@PathVariable String id){return sessions.getOrDefault(id,List.of());}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("status","ok","service","aegis-agent-gateway");}
}

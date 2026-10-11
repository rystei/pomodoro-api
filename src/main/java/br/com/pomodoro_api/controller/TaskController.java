package br.com.pomodoro_api.controller;

import br.com.pomodoro_api.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private List<Task> tasks = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong(1);

    public TaskController() {
        tasks.add(new Task(nextId.getAndIncrement(), "Estudar Spring Boot", false));
        tasks.add(new Task(nextId.getAndIncrement(), "Fazer exercícios de revisão", false));
        tasks.add(new Task(nextId.getAndIncrement(), "Assistir aula de testes", true));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(@RequestParam(required = false) Boolean completed) {
        if (completed == null) {
            return ResponseEntity.ok(tasks);
        }

        return ResponseEntity.ok(tasks.stream()
                .filter(task -> task.getCompleted().equals(completed))
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setId(nextId.getAndIncrement());
        tasks.add(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

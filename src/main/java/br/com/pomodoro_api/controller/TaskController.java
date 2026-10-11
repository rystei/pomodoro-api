package br.com.pomodoro_api.controller;

import br.com.pomodoro_api.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();

    public TaskController() {
        tasks.add(new Task(1L, "Estudar Spring Boot", false));
        tasks.add(new Task(2L, "Fazer exercicios de revisao", false));
        tasks.add(new Task(3L, "Assistir aula de testes", true));
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) Boolean completed) {
        if (completed == null) {
            return tasks;
        }
        return tasks.stream()
                .filter(task -> task.getCompleted().equals(completed))
                .toList();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}

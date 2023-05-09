package com.jamiltonquintero.mvc.controller;

import com.jamiltonquintero.mvc.model.dto.TaskDto;
import com.jamiltonquintero.mvc.model.dto.request.TaskRequest;
import com.jamiltonquintero.mvc.model.service.ITaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final ITaskService iTaskService;

    public TaskController(ITaskService iTaskService) {
        this.iTaskService = iTaskService;
    }


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", iTaskService.getAll());
        return "tasks-list";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("newTask", new TaskRequest());
        return "add-task";
    }


    @PostMapping
    public String createNew(@ModelAttribute("newTask") TaskRequest taskRequest, BindingResult result) {
        if (result.hasErrors()) {
            return "tasks-list";
        }
        iTaskService.create(taskRequest);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {
        TaskDto task = iTaskService.getById(id);
        TaskRequest taskRequest = new TaskRequest(task.getName(), task.getDescription());
        model.addAttribute("task", taskRequest);
        model.addAttribute("taskId", id);
        return "edit-task";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute("task") TaskRequest taskRequest, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return "edit-task";
        }
        iTaskService.update(taskRequest, id);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        iTaskService.deleteById(id);
        return "redirect:/tasks";
    }



}
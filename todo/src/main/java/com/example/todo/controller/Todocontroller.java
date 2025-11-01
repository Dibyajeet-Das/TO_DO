package com.example.todo.controller;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.model.Todomodel;



@RestController
@RequestMapping("/todos")
public class Todocontroller {
     
	private List<Todomodel> todonew = new ArrayList<>(Arrays.asList(
			new Todomodel(1, "complete my task1", "completed"),
			new Todomodel(2, "new requirement by client gather the user details using the employee id", "Not completed"),
            new Todomodel(3, "prepare the documents for the new project", "in progress")
   ));
	
	@GetMapping("/tasks")
	public List<Todomodel> getAllTodos() {
		return todonew;
	}
	
	@GetMapping("/tasks/{id}")
	public Todomodel getTaskById (@PathVariable int id) {
		//return todonew.get(id);
		return todonew.stream().filter(t-> t.getId()== id).findFirst().orElse(null);
	}
	
    @PostMapping("/add-task")
    public Todomodel addTask(@RequestBody Todomodel newTodo) {
    	//FIXME: validation request body before adding
        newTodo.setId(todonew.size() + 1);
        todonew.add(newTodo);
        return newTodo;
    }
    
    @PutMapping("/add-task/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable int id, @RequestBody Todomodel updatedTodo) {
        Todomodel todo = todonew.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);

        if (todo == null) {
            return ResponseEntity.notFound().build();
        }

        //FIXME: validation request body before adding
        if (updatedTodo.getTask() == null || updatedTodo.getTask().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Task name cannot be empty");
        }
        
        if(updatedTodo.getCompleted() == null || updatedTodo.getCompleted().trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Completion status cannot be empty");
		}
        //used for numeric validation
        if(updatedTodo.getCompleted().matches("\\d+")){
           return ResponseEntity.badRequest().body("Completion status cannot be numeric");
        }
        if(updatedTodo.getTask().matches("\\d+")){
			return ResponseEntity.badRequest().body("Task name cannot be numeric");
		}
        
        todo.setTask(updatedTodo.getTask().trim());
        todo.setCompleted(updatedTodo.getCompleted().trim());

        return ResponseEntity.ok(todo);
    }
    
    //TODO: delete task
    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<Object> DeleteTask (@PathVariable int id) {
    	Todomodel todo = todonew.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    	if(todo == null) {
    		return ResponseEntity.notFound().build();
    	}
    	todonew.remove(todo);
    	return ResponseEntity.ok().build();
    }
    
    
    //search-task
    @GetMapping("/search-task/search")
    public  ResponseEntity<List<Todomodel>> searchTask (@RequestParam("keyword") String keyword) {
    	
        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        List<Todomodel> result = todonew.stream()
                .filter(t -> t.getTask().trim().contains(keyword.trim()) ||
                             t.getCompleted().trim().contains(keyword.trim()))
                .toList();

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }
    
    
       //GET
      //users/{userId}/todos
     //Fetch tasks for specific user
    //shows the task that are completed only
    @GetMapping("/update-status")
    public ResponseEntity<List<Todomodel>> getCompletedTasks() {
    	
        List<Todomodel> completedTasks = todonew.stream()
                .filter(t -> t.getCompleted().equalsIgnoreCase("completed"))
                .toList();

        if (completedTasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }
        

        return ResponseEntity.ok(completedTasks);
    }

    //List pending task
    @GetMapping("/pending-tasks")
    public ResponseEntity<?> getPendingTasks() {
		
		List<Todomodel> pendingTasks = todonew.stream()
				.filter(t -> t.getCompleted().equalsIgnoreCase("Not completed"))
				.toList();

		if (pendingTasks.isEmpty()) {
			//can use custom status 
			//Generally build is used to create the response with no body around
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
		}

		return ResponseEntity.ok(pendingTasks);
	}
     
    
   
    //TODO: naming conventions
    
	
}

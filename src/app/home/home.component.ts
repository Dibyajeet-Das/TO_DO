import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface Todo {
  id: number;
  task: string;
  completed: string;
  currenttasks: string;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  todos: Todo[] = [];
  task: string = '';
  status: string = 'Not completed';
  loading = false;
  apiBase = 'http://localhost:8080/todos';
  message:any = '';
  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks() {
    this.loading = true;
    this.http.get<Todo[]>(`${this.apiBase}/tasks`).subscribe({
      next: (data) => {
        this.todos = data;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  addTask() {
    if (!this.task.trim()) {
      alert('Task cannot be empty!');
      return;
    }
    const newTask = { task: this.task, completed: this.status };
    this.http.post(`${this.apiBase}/add-task`, newTask).subscribe(() => {
      this.task = '';
      this.status = 'Not completed';
      this.loadTasks();
    });
  }


  deleteTask(id: number) {
    this.http.delete(`${this.apiBase}/delete-task/${id}`).subscribe(() => {
      this.loadTasks();
    });
  }

  toggleComplete(todo: Todo) {
    const updatedStatus =
      todo.completed === 'Completed' ? 'Not completed' : 'Completed';
    const updatedTask = { ...todo, completed: updatedStatus };

    this.http
      .put(`${this.apiBase}/update-task/${todo.id}`, updatedTask)
      .subscribe(() => {
        this.loadTasks();
      });
  }
  
  openAddTaskModal() {
    const newTask = prompt('Enter your new task:');
    if (newTask) {
      const newTodo = { task: newTask, completed: 'Not completed' };
      this.http.post(`${this.apiBase}/add-task`, newTodo).subscribe(() => {
        this.loadTasks();
      });
    }
  }

  searchTasks() {
    const showTasks = prompt('Search the Tasks');
    if(showTasks === null){
        let message: String = "There is no Task Present Currently";
    } else {
      const assigned = {task: showTasks, currenttasks: 'Current Tasks'};
      this.http.get(`${this.apiBase}/search-task/search`).subscribe(()=> {
        this.loadTasks();
      });
    }
  }

}

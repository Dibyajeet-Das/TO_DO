package com.example.todo.model;

public class Todomodel {
	
	private int id;
	private String task;
	private String completed;
	
	 public Todomodel() {}
	
	public Todomodel(int id, String task, String completed) {
		this.id = id;
	    this.task = task;
	    this.completed = completed;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	
}

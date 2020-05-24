import { Component, OnInit } from '@angular/core';
import {TaskList} from "./TaskList/task-list";
import {ApiServiceService} from "../services/api-service.service";
import {Task} from "./Task/task";

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  taskLists: TaskList[] = [];
  tasks: Task[] = [];
  selectedTaskList: TaskList;

  constructor(private apiService: ApiServiceService) { }

  ngOnInit(): void {
    this.getAllTaskList();
    this.getAllTasks();
  }

  getAllTaskList(){
    this.apiService.getAllTaskList().subscribe(
      res =>{
        this.taskLists = res;
      },
      error => {
        alert("An error has occurred;")
      }
    );
  }

  createTaskList() {
    let  newTaskList: TaskList = {
      id: null,
      categoryOfTask: "new list"
    }
    this.apiService.addTaskList(newTaskList).subscribe(
      res=>{
        newTaskList.id = res.id
        this.taskLists.push(newTaskList);
      },
      error => {
        alert("Error occurred on createTaskList method")
      }
    )
  }

  updateTaskList(updatedTaskList: TaskList) {
    this.apiService.addTaskList(updatedTaskList).subscribe(
      res=>{},
      error => {
        alert("Error occurred on updateTaskList method")
      }
    )
  }

  deleteTaskList(taskList: TaskList) {
    if (confirm("Are you sure you want to delete List of Tasks")) {
      this.apiService.deleteTaskList(taskList.id).subscribe(
        res=>{
          let indexOfTaskList= this.taskLists.indexOf(taskList)
          this.taskLists.splice(indexOfTaskList, 1)
        },
        error => {
          alert("Error occurred on deleteTaskList method")
        }
      )
    }
  }

  selectTaskList(taskList: TaskList) {
    this.selectedTaskList = taskList;
    this.apiService.getTaskOfTaskList(taskList.id).subscribe(
      res=>{
        this.tasks= res;
      },
      error => {
        alert("An error has occurred while getting all tasks of task list")
      }
    )
  }

  getAllTasks(){
    this.selectedTaskList = null;
    this.apiService.getAllTasks().subscribe(
      res=>{
        this.tasks = res;
      },
      error => {
        alert("An error has occurred while getting all tasks")
      }
    )
  }

  createTask(id: number) {
    let newTask: Task={
      id : null,
      title : "new task",
      description : "put text here",
      dateOfCreate : new Date()
    }
    this.apiService.addTask(newTask, id).subscribe(
      res=>{
        newTask.id = res.id;
        this.tasks.push(newTask);
      },
      error => {
        alert("Error occurred while saving task")
      }
    )
  }

  deleteTask(task: Task) {
    if (confirm("Are you sure to delete task ?")) {
      this.apiService.deleteTask(task.id).subscribe(
        res => {
          let indexOfTask = this.tasks.indexOf(task);
          this.tasks.slice(indexOfTask, 1)
        },
        error => {
          alert("Error occurred on deleteTask method")
        }
      )
    }
  }

  updateTask(task: Task, id: number) {
    this.apiService.addTask(task, id).subscribe(
      res=>{},
      error => {
        alert("Error occurred while saving task")
      }
    )
  }
}

import { Component, OnInit } from '@angular/core';
import {TaskList} from "./models/task-list";
import {ApiServiceService} from "../../services/api-service.service";
import {Task} from "./models/task";

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
      });
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
      }
    )
  }

  updateTaskList(updatedTaskList: TaskList) {
    this.apiService.addTaskList(updatedTaskList).subscribe(
      res=>{}
    )
  }

  deleteTaskList(taskList: TaskList) {
    if (confirm("Are you sure you want to delete List of Tasks")) {
      this.apiService.deleteTaskList(taskList.id).subscribe(
        res=>{
          let indexOfTaskList= this.taskLists.indexOf(taskList)
          this.taskLists.splice(indexOfTaskList, 1)
        }
      )
    }
  }

  selectTaskList(taskList: TaskList) {
    this.selectedTaskList = taskList;
    this.apiService.getTaskOfTaskList(taskList.id).subscribe(
      res=>{
        this.tasks= res;
      })
  }

  getAllTasks(){
    this.selectedTaskList = null;
    this.apiService.getAllTasks().subscribe(
      res=>{
        this.tasks = res;
      })
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
      })
  }

  deleteTask(task: Task) {
    if (confirm("Are you sure to delete task ?")) {
      this.apiService.deleteTask(task.id).subscribe(
        res => {
          let indexOfTask = this.tasks.indexOf(task);
          this.tasks.slice(indexOfTask, 1)
        })
    }
  }

  updateTask(task: Task, id: number) {
    this.apiService.addTask(task, id).subscribe(
      res=>{})
  }
}

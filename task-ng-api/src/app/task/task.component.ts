import { Component, OnInit } from '@angular/core';
import {TaskList} from "./TaskList/task-list";
import {ApiServiceService} from "../services/api-service.service";

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  taskLists: TaskList[] = [];
  selectedTaskList: TaskList;

  constructor(private apiService: ApiServiceService) { }

  ngOnInit(): void {
    this.getAllTaskList();
  }

  public getAllTaskList(){
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
  }
}

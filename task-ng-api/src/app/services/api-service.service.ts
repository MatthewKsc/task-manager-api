import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TaskList} from "../task/TaskList/task-list";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {
  private BASIC_URL = "http://localhost:8080";
  private ALL_TASK_LIST= `${this.BASIC_URL}\\task\\lists`;
  private DELETE_TASK_LIST= `${this.BASIC_URL}\\task\\lists\\`;

  constructor(private http: HttpClient) { }

  getAllTaskList() : Observable<TaskList[]>{
    return this.http.get<TaskList[]>(this.ALL_TASK_LIST)
  }

  addTaskList(taskList: TaskList) : Observable<TaskList>{
    return this.http.post<TaskList>(this.ALL_TASK_LIST, taskList)
  }

  deleteTaskList(id: number) : Observable<any>{
      return this.http.delete(this.DELETE_TASK_LIST + id)
  }
}

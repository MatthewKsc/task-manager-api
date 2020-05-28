import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TaskList} from "../components/task/models/task-list";
import {Observable} from "rxjs";
import {Task} from "../components/task/models/task";
import {AuthServiceService} from "./auth-service.service";

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {
  private BASIC_URL = "http://localhost:8082";
  private ALL_USER_TASK_LISTS= `${this.BASIC_URL}\\user\\taskList\\${this.authService.userId}`;
  private ADD_USER_TASK_LIST = `${this.BASIC_URL}\\user\\${this.authService.userId}`;
  private DELETE_TASK_LIST= `${this.BASIC_URL}\\tasks\\lists\\`;
  private GET_ALL_TASKS = `${this.BASIC_URL}\\user\\tasks\\${this.authService.userId}`;
  private GET_TASK_BY_TASK_LIST = `${this.BASIC_URL}\\task\\byTaskList\\`;
  private POST_TASK = `${this.BASIC_URL}\\task\\`;
  private DELETE_TASK = `${this.BASIC_URL}\\task\\`;

  constructor(private http: HttpClient, private authService: AuthServiceService) {
  }

  getAllTaskList() : Observable<TaskList[]>{
    return this.http.get<TaskList[]>(this.ALL_USER_TASK_LISTS)
  }

  addTaskList(taskList: TaskList) : Observable<TaskList>{
    return this.http.post<TaskList>(this.ADD_USER_TASK_LIST, taskList)
  }

  deleteTaskList(id: number) : Observable<any>{
      return this.http.delete(this.DELETE_TASK_LIST + id)
  }

  getAllTasks() : Observable<Task[]>{
    return this.http.get<Task[]>(this.GET_ALL_TASKS)
  }

  getTaskOfTaskList(taskListId: number) : Observable<Task[]> {
    return this.http.get<Task[]>(this.GET_TASK_BY_TASK_LIST + taskListId)
  }

  addTask(task: Task, id: number) : Observable<Task>{
    return this.http.post<Task>(this.POST_TASK + id, task)
  }

  deleteTask(id: number) : Observable<any>{
    return this.http.delete(this.DELETE_TASK+id)
  }
}

using Microsoft.AspNetCore.Mvc;
using TodoList.Application.Request.TodoItem;
using TodoList.Domain.Entities;

namespace TodoList.Application.AppService.Interface;

public interface ITodoItemAppService
{
    public List<TodoItem> GetAll();
    public TodoItem GetById(Guid id);
    public TodoItem Create(CreateTodoItemRequest request);
    public TodoItem Update(UpdateTodoItemRequest request);
    public bool Delete(Guid id);
}

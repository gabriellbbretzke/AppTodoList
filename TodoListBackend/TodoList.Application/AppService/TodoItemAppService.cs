using Microsoft.AspNetCore.Mvc;
using TodoList.Application.AppService.Interface;
using TodoList.Application.Request.TodoItem;
using TodoList.Domain.Entities;
using TodoList.Domain.Interfaces;
using TodoList.Domain.Interfaces.Repositories;

namespace TodoList.Application.AppService;

public class TodoItemAppService : ITodoItemAppService
{
    private readonly ITodoItemRepository _todoItemRepository;
    private readonly IUnitOfWork _unitOfWork;

    public TodoItemAppService(ITodoItemRepository todoItemRepository, IUnitOfWork unitOfWork)
    {
        _todoItemRepository = todoItemRepository;
        _unitOfWork = unitOfWork;
    }

    public List<TodoItem> GetAll()
    {
        var result = _todoItemRepository.GetAll();
        return result;
    }

    public TodoItem GetById(Guid id)
    {
        var result = _todoItemRepository.GetById(id);
        return result;
    }

    public TodoItem Create(CreateTodoItemRequest request)
    {
        if (request == null)
            return null;

        var result = _todoItemRepository.Add(new TodoItem(request.Title, request.Description));
        _unitOfWork.Commit();

        return result;
    }

    public TodoItem Update(UpdateTodoItemRequest request)
    {
        var todoItemToUpdate = _todoItemRepository.GetById(request.Id);
        todoItemToUpdate.Update(request.Title, request.Description, request.IsCompleted);
        
        _todoItemRepository.Update(todoItemToUpdate);
        _unitOfWork.Commit();

        return todoItemToUpdate;
    }

    public bool Delete(Guid id)
    {
        var todoItemToRemove = _todoItemRepository.GetById(id);
        if (todoItemToRemove == null)
            return false;

        _todoItemRepository.Remove(todoItemToRemove);
        _unitOfWork.Commit();

        return true;
    }
}

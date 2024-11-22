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

    public TodoItem Create(CreateTodoItemRequest request)
    {
        if (request == null)
            return null;

        var result = _todoItemRepository.Add(new TodoItem
        {
            Title = request.Title,
            Description = request.Description,
            IsCompleted = false,
            CreatedAt = DateTime.Now
        });

        _unitOfWork.Commit();

        return result;
    }

    public IActionResult Delete(Guid id)
    {
        throw new NotImplementedException();
    }

    public List<TodoItem> GetAll()
    {
        throw new NotImplementedException();
    }

    public TodoItem GetById(Guid id)
    {
        var result = _todoItemRepository.GetById(id);

        return result;
    }

    public TodoItem Update(UpdateTodoItemRequest request)
    {
        throw new NotImplementedException();
    }
}

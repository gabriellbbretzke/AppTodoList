using Microsoft.AspNetCore.Mvc;
using TodoList.Application.AppService.Interface;
using TodoList.Application.Request.TodoItem;
using TodoList.Domain.Entities;

namespace TodoList.Api.Controllers;

[ApiController]
[Route("api/[controller]")]
public class TodoItemController : ControllerBase
{
    private readonly ITodoItemAppService _todoItemAppService;

    public TodoItemController(ITodoItemAppService todoItemAppService)
    {
        _todoItemAppService = todoItemAppService;
    }

    [HttpGet]
    public ActionResult<List<TodoItem>> GetAll()
    {
        return Ok(_todoItemAppService.GetAll());
    }

    [HttpGet("{id}")]
    public ActionResult<TodoItem> GetById(Guid id)
    {
        var result = _todoItemAppService.GetById(id);
        return result == null ? NotFound() : Ok(result);
    }

    [HttpPost]
    public ActionResult<TodoItem> Create(CreateTodoItemRequest request)
    {
        if (request == null)
            return BadRequest("Invalid data.");

        var result = _todoItemAppService.Create(request);
        return result == null ?
            BadRequest() :
            CreatedAtAction(nameof(GetById), new { id = result.Id }, result);
    }

    [HttpPut]
    public ActionResult<TodoItem> Update(UpdateTodoItemRequest request)
    {
        var result = _todoItemAppService.Update(request);
        return result == null ? NotFound() : Accepted(result);
    }

    [HttpDelete]
    public ActionResult Delete(Guid id)
    {
        var result = _todoItemAppService.Delete(id);
        return result ? Ok(id) : NotFound();
    }
}

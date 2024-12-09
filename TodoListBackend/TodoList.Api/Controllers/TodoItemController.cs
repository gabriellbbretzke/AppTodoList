using Microsoft.AspNetCore.Authorization;
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
    [Authorize]
    public ActionResult<List<TodoItem>> GetAll()
    {
        var result = _todoItemAppService.GetAll();
        return result == null ? NotFound() : Ok(result);
    }

    [HttpGet("{id}")]
    [Authorize]
    public ActionResult<TodoItem> GetById(Guid id)
    {
        var result = _todoItemAppService.GetById(id);
        return result == null ? NotFound() : Ok(result);
    }

    [HttpPost]
    [Authorize]
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
    [Authorize]
    public ActionResult<TodoItem> Update(UpdateTodoItemRequest request)
    {
        var result = _todoItemAppService.Update(request);
        return result == null ? NotFound() : Accepted(result);
    }

    [HttpDelete]
    [Authorize]
    public IActionResult Delete([FromQuery] Guid id)
    {
        var result = _todoItemAppService.Delete(id);
        return result.Success ? Ok(result) : NotFound(result);
    }
}

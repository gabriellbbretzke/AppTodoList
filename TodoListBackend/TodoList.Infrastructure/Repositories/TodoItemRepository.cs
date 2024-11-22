using TodoList.Domain.Entities;
using TodoList.Domain.Interfaces.Repositories;
using TodoList.Infrastructure.Context;

namespace TodoList.Infrastructure.Repositories;

public class TodoItemRepository : Repository<TodoListDbContext,TodoItem>, ITodoItemRepository
{
    public TodoItemRepository(TodoListDbContext context) : base(context)
    {
    }
}

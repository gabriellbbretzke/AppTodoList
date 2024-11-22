using TodoList.Domain.Interfaces;
using TodoList.Infrastructure.Context;

namespace TodoList.Infrastructure;

public class UnitOfWork : IUnitOfWork
{
    private readonly TodoListDbContext _context;

    public UnitOfWork(TodoListDbContext context)
    {
        _context = context;
    }

    public void Commit()
    {
        _context.SaveChanges();
    }

    public async Task CommitAsync(CancellationToken cancellationToken = default)
    {
        await _context.SaveChangesAsync(cancellationToken);
    }

    public void Dispose()
    {
        GC.SuppressFinalize(this);
        _context?.Dispose();
    }
}

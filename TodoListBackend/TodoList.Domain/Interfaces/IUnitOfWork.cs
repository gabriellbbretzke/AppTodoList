namespace TodoList.Domain.Interfaces;

public interface IUnitOfWork : IDisposable
{
    Task CommitAsync(CancellationToken cancellationToken = default);

    void Commit();
}

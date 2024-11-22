using System.Linq.Expressions;

namespace TodoList.Domain.Interfaces.Repositories;

public interface IRepository<TEntity> : IDisposable where TEntity : class
{
    TEntity Add(TEntity obj);
    void AddSeveral(List<TEntity> listaObjs);
    TEntity GetById(int id);
    TEntity GetById(Guid id);
    List<TEntity> GetAll();
    void Update(TEntity obj);
    void Remove(TEntity entity);
    List<TEntity> SearchFor(Expression<Func<TEntity, bool>> predicate);
    bool Exists(Expression<Func<TEntity, bool>> predicate);
}

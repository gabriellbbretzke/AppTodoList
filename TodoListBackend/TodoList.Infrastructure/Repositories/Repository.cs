using Microsoft.EntityFrameworkCore;
using System.Linq.Expressions;
using TodoList.Domain.Interfaces.Repositories;

namespace TodoList.Infrastructure.Repositories;

public abstract class Repository<TContext, TEntity> : IRepository<TEntity>
    where TEntity : class where TContext : DbContext
{
    protected TContext Db;
    protected DbSet<TEntity> DbSet;

    protected Repository(TContext context)
    {
        Db = context;
        DbSet = Db.Set<TEntity>();
    }

    public virtual TEntity Add(TEntity obj)
    {
        var entry = DbSet.Add(obj);
        return entry.Entity;
    }

    public virtual void AddSeveral(List<TEntity> listaObjs)
    {
        DbSet.AddRange(listaObjs);
    }

    public virtual TEntity GetById(int id)
    {
        return DbSet.Find(id);
    }

    public virtual TEntity GetById(Guid id)
    {
        return DbSet.Find(id);
    }

    public virtual List<TEntity> GetAll()
    {
        return DbSet.AsNoTracking().ToList();
    }

    public virtual void Update(TEntity obj)
    {
        Db.Entry(obj).State = EntityState.Modified;

        DbSet.Update(obj);
    }

    public virtual void Remove(TEntity entity)
    {
        if (Db.Entry(entity).State == EntityState.Detached)
            DbSet.Attach(entity);

        DbSet.Remove(entity);
    }

    public List<TEntity> SearchFor(Expression<Func<TEntity, bool>> predicate)
    {
        return  DbSet.AsNoTracking().Where(predicate).ToList();
    }

    public bool Exists(Expression<Func<TEntity, bool>> predicate)
    {
        return  DbSet.Where(predicate).Any();
    }

    protected virtual IQueryable<TEntity> Get()
    {
        return DbSet;
    }

    protected virtual IQueryable<TEntity> ObterAsNoTracking()
    {
        return DbSet.AsNoTrackingWithIdentityResolution();
    }

    public void Dispose()
    {
        Db.Dispose();
        GC.SuppressFinalize(this);
    }
}
using Microsoft.EntityFrameworkCore;
using System.Reflection;
using TodoList.Domain.Entities;

namespace TodoList.Infrastructure.Context
{
    public class TodoListDbContext(DbContextOptions<TodoListDbContext> options) : DbContext(options)
    {
        public DbSet<TodoItem> TodoItem { get; set; }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            base.OnModelCreating(builder);

            SetDefaultTypes(builder);

            builder.ApplyConfigurationsFromAssembly(Assembly.GetExecutingAssembly());
        }

        private static void SetDefaultTypes(ModelBuilder builder)
        {
            foreach (var property in builder.Model.GetEntityTypes()
                         .SelectMany(t => t.GetProperties())
                         .Where(p => p.ClrType == typeof(DateTime) || p.ClrType == typeof(DateTime?)))
            {
                property.SetColumnType("timestamp without time zone");
            }
        }
    }
}

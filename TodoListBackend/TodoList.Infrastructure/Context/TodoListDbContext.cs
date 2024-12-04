using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using System.Reflection;
using System.Reflection.Emit;
using TodoList.Domain.Entities;

namespace TodoList.Infrastructure.Context;

public class TodoListDbContext : IdentityDbContext<IdentityUser>
{
    public TodoListDbContext(DbContextOptions<TodoListDbContext> options)
        : base(options) { }

    public DbSet<TodoItem> TodoItem { get; set; }

    protected override void OnModelCreating(ModelBuilder builder)
    {
        base.OnModelCreating(builder);

        RenameTablesFromIdentity(builder);

        SetDefaultTypes(builder);

        builder.ApplyConfigurationsFromAssembly(Assembly.GetExecutingAssembly());
    }

    private void RenameTablesFromIdentity(ModelBuilder builder)
    {
        builder.Entity<IdentityUser>(b => { b.ToTable("User"); }); 
        builder.Entity<IdentityRole>(b => { b.ToTable("Role"); }); 
        builder.Entity<IdentityUserRole<string>>(b => { b.ToTable("UserRole"); });
        builder.Entity<IdentityUserClaim<string>>(b => { b.ToTable("UserClaim"); });
        builder.Entity<IdentityUserLogin<string>>(b => { b.ToTable("UserLogin"); });
        builder.Entity<IdentityRoleClaim<string>>(b => { b.ToTable("RoleClaim"); });
        builder.Entity<IdentityUserToken<string>>(b => { b.ToTable("UserToken"); });
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

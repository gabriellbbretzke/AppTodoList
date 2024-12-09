using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.IdentityModel.Tokens;
using System.Text;
using TodoList.Application.AppService;
using TodoList.Application.AppService.Interface;
using TodoList.Domain.Interfaces;
using TodoList.Domain.Interfaces.Repositories;
using TodoList.Infrastructure.Context;
using TodoList.Infrastructure.IoC.Configuration;
using TodoList.Infrastructure.Repositories;

namespace TodoList.Infrastructure.IoC;

public static class DependencyInjection
{
    public static void AddConfiguration(this IServiceCollection services, IConfiguration configuration)
    {
        services.AddScoped<IUnitOfWork, UnitOfWork>();
        services.AddAppServices();
        services.AddRepositories();
        services.AddDbContext(configuration);
    }

    private static void AddAppServices(this IServiceCollection services)
    {
        services.AddScoped<ITodoItemAppService, TodoItemAppService>();
        services.AddScoped<IAccountAppService, AccountAppService>();
    }

    private static void AddRepositories(this IServiceCollection services)
    {
        services.AddScoped<ITodoItemRepository, TodoItemRepository>();
    }

    private static void AddDbContext(this IServiceCollection services, IConfiguration configuration) 
    {
        services.AddDbContext<TodoListDbContext>(options =>
        {
            options.UseNpgsql(configuration.GetConnectionString("TodoListDb"));
            options.EnableSensitiveDataLogging();
        });
    }
}

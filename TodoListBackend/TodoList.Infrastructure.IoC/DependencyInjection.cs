using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using TodoList.Application.AppService;
using TodoList.Application.AppService.Interface;
using TodoList.Domain.Interfaces;
using TodoList.Domain.Interfaces.Repositories;
using TodoList.Infrastructure.Context;
using TodoList.Infrastructure.Repositories;

namespace TodoList.Infrastructure.IoC;

public static class DependencyInjection
{
    public static void AddConfiguration(this IServiceCollection services, IConfiguration configuration)
    {
        //services.AddHttpContextAccessor();
        //services.AddSingleton<Migrator>();
        //services.AddSingleton<MigratorInitialData>();
        services.AddScoped<IUnitOfWork, UnitOfWork>();
        //services.AddScoped<INotifier, Notifier>();
        //services.AddScoped<ILoggedUser, LoggedUser>();
        //services.AddAutoMapperFromAssemblyContaining<IApplicationAssemblyMarker>();
        //services.AddValidatorsFromAssemblyContaining<IApplicationAssemblyMarker>();
        services.AddAppServices();
        //services.AddServices();
        services.AddRepositories();
        //services.AddHttpClients();
        services.AddDbContext(configuration);
        //services.AddAppSettings(configuration);
        //services.AddBlobService(configuration);
    }

    private static void AddAppServices(this IServiceCollection services)
    {
        services.AddScoped<ITodoItemAppService, TodoItemAppService>();
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

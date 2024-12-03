using Microsoft.EntityFrameworkCore;
using TodoList.Application.AppService;
using TodoList.Application.AppService.Interface;
using TodoList.Domain.Interfaces.Repositories;
using TodoList.Infrastructure.Context;
using TodoList.Infrastructure.Repositories;
using TodoList.Infrastructure.IoC;
using Microsoft.AspNetCore.Identity;


var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddConfiguration(builder.Configuration);
builder.Services.AddIdentity<IdentityUser, IdentityRole>()
    .AddEntityFrameworkStores<TodoListDbContext>()
    .AddDefaultTokenProviders();
builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();
app.UseAuthentication();
app.UseAuthorization();

app.MapControllers();

app.Run();

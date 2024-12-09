using Microsoft.AspNetCore.Mvc;
using System.Configuration;
using TodoList.Api.Extensions;
using TodoList.Infrastructure.IoC;


var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddConfiguration(builder.Configuration);
builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
//builder.Services.AddSwaggerGen();
builder.Services.AddSwaggerConfiguration();
builder.Services.AddIdentityConfiguration(builder.Configuration);
builder.Services.AddConfigurationJwtSettings(builder.Configuration);
builder.Services.AddApiVersioning(options =>
{
    options.AssumeDefaultVersionWhenUnspecified = true;
    options.DefaultApiVersion = new ApiVersion(1, 0);
    options.ReportApiVersions = true;
});
builder.Services.AddVersionedApiExplorer(options =>
{
    options.GroupNameFormat = "'v'VVV"; // Define o formato do grupo de versões
    options.SubstituteApiVersionInUrl = true; // Substitui a versão na URL
});

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    //app.UseSwagger();
    //app.UseSwaggerUI();
}
app.UseHttpsRedirection();
app.UseAuthentication();
app.UseAuthorization();

app.UseSwaggerConfiguration();

app.MapControllers();

app.Run();

using Microsoft.OpenApi.Models;

namespace TodoList.Api.Extensions;

public static class SwaggerExtensions
{
    //public static void AddSwaggerConfiguration(this IServiceCollection services)
    //{
    //    _ = services.AddSwaggerGen(options =>
    //    {
    //        //options.EnableAnnotations();
    //        options.AddSecurityDefinition("Bearer", new OpenApiSecurityScheme
    //        {
    //            In = ParameterLocation.Header,
    //            Description = "Please insert JWT with Bearer into field",
    //            Name = "Authorization",
    //            Type = SecuritySchemeType.ApiKey,
    //            Scheme = "bearer",
    //        });
    //        options.AddSecurityRequirement(new OpenApiSecurityRequirement
    //        {
    //            {
    //                new OpenApiSecurityScheme
    //                {
    //                    Reference = new OpenApiReference { Type = ReferenceType.SecurityScheme, Id = "Bearer" }
    //                },
    //                Array.Empty<string>()
    //            }
    //        });
    //    });
    //}

    //public static void UseSwaggerConfiguration(this WebApplication app)
    //{
    //    var apiVersionDescriptionProvider = app.Services.GetRequiredService<IApiVersionDescriptionProvider>();

    //    app.UseSwagger();
    //    app.UseSwaggerUI(options =>
    //    {
    //        foreach (var description in apiVersionDescriptionProvider.ApiVersionDescriptions)
    //        {
    //            options.SwaggerEndpoint($"/swagger/{description.GroupName}/swagger.json",
    //                description.GroupName.ToUpperInvariant());
    //        }
    //    });
    //}
}

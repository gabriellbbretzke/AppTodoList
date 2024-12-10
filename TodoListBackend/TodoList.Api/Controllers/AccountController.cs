using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity.Data;
using Microsoft.AspNetCore.Mvc;
using TodoList.Application.AppService.Interface;
using TodoList.Application.Request.Account;

namespace TodoList.Api.Controllers;

[ApiController]
[ApiVersion("1.0")]
[AllowAnonymous]
[Route("api/v{version:apiVersion}/[controller]")]
public class AccountController : ControllerBase
{
    private readonly IAccountAppService _accountAppService;

    public AccountController(IAccountAppService accountAppService)
    {
        _accountAppService = accountAppService;
    }

    [HttpPost("create-user")]
    public async Task<IActionResult> CreateUser([FromBody] CreateAccountRequest model)
    {
        var result = await _accountAppService.CreateUserAsync(model);
        if (!result.Success)
            return BadRequest(result);

        return Ok(result);
    }

    [HttpPost("login")]
    public async Task<IActionResult> Login([FromBody] LoginRequest request)
    {
        var result = await _accountAppService.LoginAsync(request);

        if (!result.Success)
            return BadRequest(result.Message);

        return Ok(new { Token = result.Token });
    }
}

using Microsoft.AspNetCore.Mvc;
using TodoList.Application.AppService.Interface;

namespace TodoList.Api.Controllers;

[ApiController]
[Route("api/[controller]")]
public class AccountController : ControllerBase
{
    private readonly IUserManagerAppService _userManagerAppService;

}

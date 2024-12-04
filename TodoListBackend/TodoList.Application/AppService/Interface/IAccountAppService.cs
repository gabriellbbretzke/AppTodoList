using Microsoft.AspNetCore.Identity.Data;
using TodoList.Application.Request.Account;
using TodoList.Application.Response.Account;

namespace TodoList.Application.AppService.Interface
{
    public interface IAccountAppService
    {
        public Task<CreateAccountResponse> CreateUserAsync(CreateAccountRequest request);
        public Task<LoginAccountResponse> LoginAsync(LoginRequest loginRequest);
    }
}

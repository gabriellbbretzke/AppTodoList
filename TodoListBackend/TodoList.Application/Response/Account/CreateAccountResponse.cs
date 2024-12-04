using Microsoft.AspNetCore.Identity;

namespace TodoList.Application.Response.Account
{
    public class CreateAccountResponse
    {
        public string Message { get; set; }
        public bool Success { get; set; }
        public IdentityUser IdentityUser { get; set; }
    }
}

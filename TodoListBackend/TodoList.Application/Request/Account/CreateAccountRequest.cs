using System.ComponentModel.DataAnnotations;

namespace TodoList.Application.Request.Account
{
    public class CreateAccountRequest
    {
        [Required]
        [EmailAddress]
        public string Email { get; set; }

        [Required]
        [MinLength(6)]
        public string Password { get; set; }
    }
}

using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.Data;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using TodoList.Application.AppService.Interface;
using TodoList.Application.Request.Account;
using TodoList.Application.Response.Account;
using TodoList.Infrastructure.IoC.Configuration;

namespace TodoList.Application.AppService
{
    public class AccountAppService : IAccountAppService
    {
        private readonly UserManager<IdentityUser> _userManager;
        private readonly JwtSettings _jwtSettings;

        public AccountAppService(UserManager<IdentityUser> userManager, IOptions<JwtSettings> jwtSettings) 
        {
            _userManager = userManager;
            _jwtSettings = jwtSettings.Value;
        }

        public async Task<CreateAccountResponse> CreateUserAsync(CreateAccountRequest request)
        {
            var user = new IdentityUser { UserName = request.Email, Email = request.Email };
            var result = await _userManager.CreateAsync(user, request.Password);

            if (result.Succeeded)
                return new CreateAccountResponse
                {
                    Message = "User created successfully",
                    Success = true,
                    IdentityUser = user
                };

            return new CreateAccountResponse
            {
                Message = string.Join(";", result.Errors.Select(e => e.Description)),
                Success = false
            };
        }

        public async Task<LoginAccountResponse> LoginAsync(LoginRequest request)
        {
            var user = await _userManager.FindByEmailAsync(request.Email);
            if (user == null || !await _userManager.CheckPasswordAsync(user, request.Password))
            {
                return new LoginAccountResponse
                {
                    Success = false,
                    Message = "Invalid username or password."
                };
            }

            var token = GenerateJwtToken(user);
            return new LoginAccountResponse
            {
                Success = true,
                Token = token,
                Message = "Login successful."
            };
        }

        #region Métodos privados
        private string GenerateJwtToken(IdentityUser user)
        {
            var claims = new List<Claim>
            {
                new Claim(ClaimTypes.NameIdentifier, user.Id),
                new Claim(ClaimTypes.Email, user.Email),
                new Claim(ClaimTypes.Name, user.UserName)
            };

            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_jwtSettings.SecretKey));
            var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

            var token = new JwtSecurityToken(
                issuer: _jwtSettings.Issuer,
                audience: _jwtSettings.Audience,
                claims: claims,
                expires: DateTime.UtcNow.AddHours(2),
                signingCredentials: creds);

            return new JwtSecurityTokenHandler().WriteToken(token);
        }
        #endregion
    }
}

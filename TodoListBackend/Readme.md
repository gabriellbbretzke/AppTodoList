docker run --name postgres-container -e POSTGRES_USER=todoadmuser -e POSTGRES_PASSWORD=asjd8612213jjSAJ -e POSTGRES_DB=todolist -p 5432:5432 -v postgres_data:/var/lib/postgresql/data -d postgres:15

Update:
dotnet ef database update -p TodoList.Infrastructure -s TodoList.Api

EF Migrations:
dotnet ef migrations add InitialCreate -p TodoList.Infrastructure -s TodoList.Api
dotnet ef migrations add InitialIdentitySetup -p TodoList.Infrastructure -s TodoList.Api
dotnet ef migrations add RenameIdentityTables -p TodoList.Infrastructure -s TodoList.Api
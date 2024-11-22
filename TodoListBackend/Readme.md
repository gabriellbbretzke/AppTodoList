docker run --name postgres-container -e POSTGRES_USER=todoadmuser -e POSTGRES_PASSWORD=asjd8612213jjSAJ -e POSTGRES_DB=todolist -p 5432:5432 -v postgres_data:/var/lib/postgresql/data -d postgres:15

dotnet ef migrations add InitialCreate -p TodoList.Infrastructure -s TodoList.Api
dotnet ef database update -p TodoList.Infrastructure -s TodoList.Presentation
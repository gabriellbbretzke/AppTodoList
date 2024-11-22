namespace TodoList.Domain.Entities;

public class TodoItem : Base.Entity
{
    public string Title { get; set; }
    public string Description { get; set; }
    public bool IsCompleted { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime? CompletedAt { get; set; }

    public TodoItem(string title, string description) 
    {
        Title = title;
        Description = description;
        CreatedAt = DateTime.Now;
    }

    public void Update(string title, string description, bool isCompleted)
    {
        Title = title;
        Description = description;
        IsCompleted = isCompleted;
        CompletedAt = isCompleted ? DateTime.Now : null;
    }

    public void Complete() 
    {
        IsCompleted = true;
        CompletedAt = DateTime.Now; 
    }
}

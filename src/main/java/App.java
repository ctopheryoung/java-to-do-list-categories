import java.util.HashMap;
import java.util.ArrayList;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
      staticFileLocation("/public");
      String layout = "templates/layout.vtl";

      get("/", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/todos", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("todos", ToDo.all());
        model.put("template", "templates/todos.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("todos/new", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/todo-form.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      // post("/todos", (request, response) -> {
      //   HashMap<String, Object> model = new HashMap<String, Object>();
      //   Category category = Category.find(Integer.parseInt(request.queryParams("categoryId")));
      //   String description = request.queryParams("description");
      //   ToDo newToDo = new ToDo(description);
      //   category.addToDo(newToDo);
      //   model.put("category", category);
      //   model.put("template", "templates/category.vtl");
      //   return new ModelAndView(model, layout);
      // }, new VelocityTemplateEngine());

      get("/todos/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        ToDo todo = ToDo.find(Integer.parseInt(request.params(":id")));
        model.put("todo", todo);
        model.put("template", "templates/todo.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/categories", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("categories", Category.all());
        model.put("template", "templates/categories.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("categories/new", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/category-form.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/categories", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        String name = request.queryParams("name");
        Category newCategory = new Category(name);
        model.put("category", newCategory);
        model.put("template", "templates/success.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/categories/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();

        Category category = Category.find(Integer.parseInt(request.params(":id")));
        model.put("category", category);

        model.put("template", "templates/category.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("categories/:id/todos/new", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Category category = Category.find(Integer.parseInt(request.params(":id")));
        ArrayList<ToDo> todos = category.getToDos();
        model.put("category", category);
        model.put("todos", todos);
        model.put("template", "templates/category-todo-form.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/todos", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();

        Category category = Category.find(Integer.parseInt(request.queryParams("categoryId")));
        ArrayList<ToDo> todos = category.getToDos();

        if (todos == null) {
          todos = new ArrayList<ToDo>();
          request.session().attribute("todos", todos);
        }

        String description = request.queryParams("description");
        ToDo newToDo = new ToDo(description);

        todos.add(newToDo);

        model.put("todos", todos);
        model.put("category", category);
        model.put("template", "templates/category.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());
    }
}

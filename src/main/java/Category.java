import java.util.ArrayList;

public class Category {

  private static ArrayList<Category> instances = new ArrayList<Category>();

  private String mName;
  private int mId;
  private ArrayList<ToDo> mToDos;

  public Category(String name) {
    mName = name;
    instances.add(this);
    mId = instances.size();
    mToDos = new ArrayList<ToDo>();
  }

  public String getName() {
    return mName;
  }

  public int getId() {
    return mId;
  }

  public ArrayList<ToDo> getToDos() {
    return mToDos;
  }

  public void addToDo(ToDo todo) {
    mToDos.add(todo);
  }

  public static ArrayList<Category> all() {
    return instances;
  }

  public static void clear() {
    instances.clear();
  }

  public static Category find(int id) {
    try {
      return instances.get(id - 1);
    } catch (IndexOutOfBoundsException exception) {
      return null;
    }
  }
}

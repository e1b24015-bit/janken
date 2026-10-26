package oit.is.z3428.kaizi.janken.model;

import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class Entry {
  private ArrayList<String> users = new ArrayList<>();
  private boolean active = false;

  public void addUser(String name) {

    for (String s : this.users) {
      if (s.equals(name)) {
        return;
      }
    }
    this.users.add(name);
  }

  public ArrayList<String> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<String> users) {
    this.users = users;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}

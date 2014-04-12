package model;

 
/**
 * 
 * Allows an interface for any class to receive notifications
 * from an OurObservable object.
 * 
 * Note: For a view, you usually use a JPanel to implement this
 * interface because the JPanel can be drawn any number of ways
 * whenever the model notifies that class that it's state has changed.  
 * 
 * @author Mercer
 *
 */
public interface OurObserver {
  public void update();
}

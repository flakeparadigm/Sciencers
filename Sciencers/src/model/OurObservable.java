package model;

import java.util.HashSet;
import java.util.Set;
/**
 * 
 * The observable object class, maintaining a list of observers,
 * and allowing the OurObserable to easily notify its observers.
 * 
 * @author Mercer
 *
 */
public class OurObservable {

  protected Set<OurObserver> observers = new HashSet<OurObserver>();

  public void addObserver(OurObserver observer) {
    observers.add(observer);
  }

  public void notifyObservers() {
    for (OurObserver observer : observers)
      observer.update();
  }
}
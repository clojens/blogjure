package blogjure.model;

import org.hibernate.Criteria;

public interface ICriteriaSpecifier {
    
    public abstract void specify(final Criteria c);
    
}
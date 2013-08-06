package blogjure.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;


public abstract class AbstractHibernateStruct {
    
    // ---------- Hibernate utility methods ----------
    public AbstractHibernateStruct save(final Session hibernateSession) {
        hibernateSession.save(this);
        return this;
    }
    public AbstractHibernateStruct delete(final Session hibernateSession) {
        hibernateSession.delete(this);
        return this;
    }
    public AbstractHibernateStruct findById(
            final Session hibernateSession, final Serializable id) {
        return (AbstractHibernateStruct) hibernateSession.get(getClass(), id);
    }
    @SuppressWarnings("unchecked")
    public List<AbstractHibernateStruct> findAll(final Session hibernateSession,
            final ICriteriaSpecifier criteriaSpecifier) {
        final Criteria cr = hibernateSession.createCriteria(getClass());
        criteriaSpecifier.specify(cr);
        return cr.list();
    }
    @SuppressWarnings("unchecked")
    public List<AbstractHibernateStruct> findAll(final Session hibernateSession) {
        return findAll(hibernateSession, new ICriteriaSpecifier() {
            public void specify(final Criteria c) { /* do nothing */ }}
        );
    }
    // ---------- End of Hibernate utility methods ----------
    
    private abstract class AbstractStringBuilderProcessor implements ITask {
        protected final StringBuilder sb;
        public AbstractStringBuilderProcessor(final StringBuilder sb) {
            this.sb = sb;
        }
        public void execute() { }  // dummy implementation
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof AbstractHibernateStruct)) {
            return false;
        }
        final AbstractHibernateStruct other = (AbstractHibernateStruct) obj;
        return toString().equals(other.toString());
    }
    
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        final ITask beforeEach = new AbstractStringBuilderProcessor(sb) { };
        final ITask afterEach  = new AbstractStringBuilderProcessor(sb) {
            @Override
            public void execute() { sb.append('\n'); }
        };
        buildFormattedString(beforeEach, sb, afterEach);
        return sb.toString();
    }
    
    public String toHtmlString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append("<pre>");
        final ITask beforeEach = new AbstractStringBuilderProcessor(sb) { };
        final ITask afterEach = new AbstractStringBuilderProcessor(sb) {
            @Override
            public void execute() { sb.append("<br/>\n"); }
        };
        buildFormattedString(beforeEach, sb, afterEach);
        sb.append("</pre>");
        return sb.toString();
    }
    
    private void buildFormattedString(final ITask beforeEach,
            final StringBuilder duringEach, final ITask afterEach) {
        final Field[] fields = this.getClass().getDeclaredFields();
        for (Field each: fields) {
            beforeEach.execute();
            duringEach.append(each.getName());
            duringEach.append('=');
            try {
                duringEach.append(each.get(this));
            } catch (final IllegalAccessException e) {
                duringEach.append("(ERROR: Cannot read value)");
            }
            afterEach.execute();
        }
    }
    
    public interface ITask {
        
        public abstract void execute() throws RuntimeException;
        
    }
}
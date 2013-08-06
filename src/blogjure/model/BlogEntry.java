package blogjure.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@SuppressWarnings("serial")
@Entity
public class BlogEntry extends AbstractHibernateStruct implements Serializable {
    @Id @GeneratedValue  public Long      id;
    @Column(length=100)  public String    title;
    @Column(length=3000) public String    content;
                         public Timestamp when_posted;
                         public Boolean   is_deleted;
    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="ENTRY_ID", nullable=false)
                         public Collection<UserComment> comments = new ArrayList<UserComment>();
    
    // -------- Setters (required for access from Clojure) -------
    
    public void setId(Long id)                { this.id = id; }
    public void setTitle(String title)        { this.title = title; }
    public void setContent(String content)    { this.content = content; }
    public void setWhen_posted(Timestamp w_p) { this.when_posted = w_p; }
    public void setIs_deleted(Boolean i_d)    { this.is_deleted = i_d; }
    
}
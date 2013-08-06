package blogjure.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class UserComment extends AbstractHibernateStruct implements Serializable {
    @Id @GeneratedValue  public Long      id;
    @ManyToOne @JoinColumn(name="ENTRY_ID", nullable=false, updatable=false, insertable=false)
                         public BlogEntry blog_entry;
    @Column(length=1000) public String    content;
                         public Timestamp when_posted;
                         public Boolean   is_deleted;
    @Column(length=100)  public String    name;
    @Column(length=100)  public String    email;
    @Column(length=100)  public String    url;
    
    // -------- Setters (required for access from Clojure) -------
    
    public void setId(Long id)                { this.id = id; }
    public void setBlog_entry(BlogEntry b_e)  { this.blog_entry = b_e; }
    public void setContent(String content)    { this.content = content; }
    public void setWhen_posted(Timestamp w_p) { this.when_posted = w_p;  }
    public void setIs_deleted(Boolean i_d)    { this.is_deleted = i_d;   }
    public void setName(String name)          { this.name = name; }
    public void setEmail(String email)        { this.email = email; }
    public void setUrl(String url)            { this.url = url; }
    
}
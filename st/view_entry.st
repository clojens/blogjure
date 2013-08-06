$!!$
$html_template(
    page_title=blog_entry.("title"),
    css_includes=cfrag_css_includes(),
    blog_header=cfrag_blog_header(),
    html_body={
        <br/>
        <div class='blogentry colborder'>
        <div id='title'><h3><a href='entry/$blog_entry.("id")$'>$blog_entry.("title")$</a></h3></div>
        <div class='whenposted'>Posted at $blog_entry.("when_posted")$</div>
        <br/><br/>
        <div id='content'>$blog_entry.("content")$</div>
        </div>
        <hr/>
        
        $if(many_comments)$
          $comments:{ each |
            $frag_usercomment(user_comment=each)$
          }; separator="\n"$
        $elseif(one_comment)$
          $frag_usercomment(user_comment=comments)$
        $endif$
        
        <div>
          <form method='POST' name='comment' action='/blogjure/blog/entry/$blog_entry.("id")$/newcomment'>
            Name   <input type='text' name='name' /> (required)<br/>
            E-mail <input type='text' name='email' /> (required, will not be published)<br/>
            URL    <input type='text' name='url' /> (optional)<br/>
            <textarea name='content' rows='10' cols='30'></textarea><br/>
            <input type='submit' name='comment' value='Post comment' />
          </form>
        </div>
    }
)$
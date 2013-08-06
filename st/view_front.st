$html_template(
    page_title="Home",
    css_includes=cfrag_css_includes(),
    blog_header=cfrag_blog_header(),
    html_body={
        $if(many_blog_entries)$
          $blog_entries:{ each |
            $frag_blogentry(blog_entry=each)$
          }; separator="\n"$
        $elseif(one_blog_entry)$
          $frag_blogentry(blog_entry=blog_entries)$
        $endif$
    }
)$
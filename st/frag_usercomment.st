    <div id='comment_by'><b
        >$if(!user_comment.("url_is_null"))$<a href='$user_comment.("url")$'
        >$endif$ $user_comment.("name")$ $if(!user_comment.("url_is_null"))$</a
        >$endif$</b></div>
    <div class='whenposted'>at $user_comment.("when_posted")$</div>
    <br/><br/>
    <div id='comment_content'>$user_comment.("content")$</div>
    <br/>
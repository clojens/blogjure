<h1><a class='title' href='/blogjure'>Blogjure</a><h1>
<h2 class='alt'>The personal blog</h2>

$flash_messages$

$!---------------------------------------!$
$! Extra notice for not-logged-in author !$
$!---------------------------------------!$
$if(not_logged_in)$
  <div class='notice'>Authentication is not implemented yet -
    just click on Login button to login. Modifying username/password
    will cause the login to fail.
  </div>
$endif$


$!---------------------------------------!$
$! Display <div> as per logged-in status !$
$!---------------------------------------!$
<div class='loginbar' align='right'>
  $if(not_logged_in)$
    <form name='loginform' method='POST' action='/blogjure/blog/login' autocomplete='off'>
      <input type='text' name='username' value='Username' size='16'
        onFocus="if(this.value=='Username') this.value='';"
        onBlur="if(this.value=='') this.value='Username';" />
        
      <input type='password' name='password' value='Password' size='14'
        onFocus="if(this.value=='Password') this.value='';"
        onBlur="if(this.value=='') this.value='Password';" />
      
      <input type='submit' value='Login' />
    </form>
  $else$
    <a href='#' class='loginbar' onClick="
      var ele = document.getElementById('newentry');
      if(ele.className == 'show') {
        ele.className='hide';
      }
      else if(ele.className == 'hide') {
        ele.className='show';
      }
      ">Post new entry</a>
    &nbsp;|&nbsp;&nbsp;
    <a href='/blogjure/blog/logout' class='loginbar' onClick="
      var ele = document.getElementById('newentry');
      ele.className='hide';
      ">Logout</a>
    &nbsp;&nbsp;
  $endif$
</div>

$!------------------------------!$
$! Special for logged-in author !$
$!------------------------------!$
$if(logged_in)$
  <div id='newentry' class='hide' align='center'>
    <form name='newentry' method='POST' action='/blogjure/blog/newentry'>
      Enter the new blog entry
      <br/>
      <input type='text' name='title' value='Title' onClick=''
        onFocus="if(this.value=='Title') this.value='';"
        onBlur="if(this.value=='') this.value='Title';"
        />
      <br/>
      <textarea name='content' rows='40' cols='80' ></textarea>
      <br/>
      <div align='center'>
      <input type='submit' value='Post new entry' />
      <input type='button' value='Cancel' onClick="
        var ele = document.getElementById('newentry');
        ele.className='hide';
        " />
      </div>
    </form>
  </div>
$endif$

<div class="form-signin">

<#-- <div class="text-center"><img src="assets/img/logo.png" alt="Metis Logo"></div> -->
<div class="tab-content">
    <div id="login" class="tab-pane active">
        <form method="post" action="${sri.makeUrlByType("login", "transition", null, "false").getUrl()}" class="">
            <p class="text-muted text-center">Enter your username and password to sign in</p>
            <input type="text" name="username" value="${(ec.getWeb().getErrorParameters().get("username"))!""}" placeholder="Username" required="required" class="form-control top">
            <input type="password" name="password" placeholder="Password" required="required" class="form-control bottom">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form>
    </div>
    <div id="reset" class="tab-pane">
        <form method="post" action="${sri.makeUrlByType("resetPassword", "transition", null, "false").getUrl()}" class="">
            <p class="text-muted text-center">Enter your username to reset and email your password</p>
            <input type="text" name="username" value="${(ec.getWeb().getErrorParameters().get("username"))!""}" placeholder="Username" required="required" class="form-control">
            <button class="btn btn-lg btn-danger btn-block" type="submit">Reset and Email Password</button>
        </form>
    </div>
    <div id="change" class="tab-pane">
        <form method="post" action="${sri.makeUrlByType("changePassword", "transition", null, "false").getUrl()}" class="">
            <p class="text-muted text-center">Enter details to change your password</p>
            <input type="text" name="username" value="${(ec.getWeb().getErrorParameters().get("username"))!""}" placeholder="Username" required="required" class="form-control top">
            <input type="password" name="oldPassword" placeholder="Old Password" required="required" class="form-control middle">
            <input type="password" name="newPassword" placeholder="New Password" required="required" class="form-control middle">
            <input type="password" name="newPasswordVerify" placeholder="New Password Verify" required="required" class="form-control bottom">
            <button class="btn btn-lg btn-danger btn-block" type="submit">Change Password</button>
        </form>
    </div>
    <div id="new-account" class="tab-pane">
        <form name="createAccount" id="createAccount" class="validation-engine-init" method="post"
              action="${sri.makeUrlByType("createAccount", "transition", null, "false").getUrl()}">
            <p class="text-muted text-center">Enter details to create a new account</p>
            <input type="hidden" name="moquiFormName" value="createAccount">
            <input type="text" name="firstName" value="${(ec.getWeb().getErrorParameters().get("firstName"))!""}" placeholder="First Name" class="form-control top required" required="required">
            <input type="text" name="lastName" value="${(ec.getWeb().getErrorParameters().get("lastName"))!""}" placeholder="Last Name" class="form-control middle required" required="required">
            <input type="email" name="emailAddress" value="${(ec.getWeb().getErrorParameters().get("emailAddress"))!""}" placeholder="Email" class="form-control middle email required" required="required">
            <input type="password" class="form-control middle required" name="newPassword" placeholder="Password" required="required">
            <input type="password" class="form-control bottom required" name="newPasswordVerify" placeholder="Verify" required="required">
            <button class="btn btn-lg btn-success btn-block" type="submit">Create Account</button>
        </form>
    </div>
</div>
<div class="text-center">
    <ul class="list-inline">
        <li><a class="text-muted" href="#login" data-toggle="tab">Login</a></li>
        <li><a class="text-muted" href="#reset" data-toggle="tab">Reset</a></li>
        <li><a class="text-muted" href="#change" data-toggle="tab">Change</a></li>
        <li><a class="text-muted" href="#new-account" data-toggle="tab">New Account</a></li>
    </ul>
</div>

</div>

<script>
  $(document).ready(function() {
    $('.list-inline li > a').click(function() {
        var activeForm = $(this).attr('href') + ' > form';
        $(activeForm).addClass('animated fadeIn');
        //set timer to 1 seconds, after that, unload the magic animation
        setTimeout(function() {
            $(activeForm).removeClass('animated fadeIn');
        }, 1000);
    });
  });
</script>

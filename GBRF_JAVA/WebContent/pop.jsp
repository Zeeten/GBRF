<!DOCTYPE html>
<html>
<head>
<link href="css/pop.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/style.css" media="screen"
	type="text/css" />
<title></title>
<style type="text/css">
body {
	margin: 0;
}

.pop {
	margin: 0px auto 0 auto;
	width: auto;
}

.relative {
	float: left;
	position: relative;
}

.pop img {
	box-shadow: 0px 0px 170px 0 #000;
	float: left;
	width: 100%;
	height: 500px;
	margin-left: 0px;
}

.pop form input {
	padding: 10px;
	border: 0;
	background: #fff;
}

.pop form input.notify {
	background: #000;
	border-radius: 3px;
	color: #fff;
	cursor: pointer;
}

.accept {
	width: 68px;
	height: 35px;
	background: #000;
	color: #fff;
	border-radius: 3px;
	cursor: pointer;
	border: none;
}
</style>
<style>
a:link {
	color: #000000;
	background-color: transparent;
	text-decoration: none;
}

a:visited {
	color: #000000;
	background-color: transparent;
	text-decoration: none;
}

a:hover {
	color: #ff0000;
	background-color: transparent;
	text-decoration: underline;
}

a:active {
	color: #ff0000;
	background-color: transparent;
	text-decoration: underline;
}

.view_more {
	width: 200px;
	float: right;
	height: 100px;
	font-family: Arial;
	font-weight: bold;
	margin-right: 47px;
	background: #C70F0F;
	margin-top: -103px;
}
</style>
</head>
<body>
	<div class="pop">
		<div class="relative">
			<img src="img/index.jpg">
			<!----------------slide box start---------------------------->
			<div id="pop1" class="popbox">
				<h2>Be a Partner in Creating History</h2>

				<p1>Global Book Release Forum, a first of its kind platform
				created in the world to achieve the following:</p1>

				<ul style="margin-left: 32px;">
					<li>To release the Book in e-Space, globally and not in a
						room, a hall or an auditorium</li>

					<li>To create an opportunity to release the book by hundreds
						and thousands of 'Guests of Honour' and not by One 'Guest of
						Honour'</li>

					<li>To release the book in the presence of millions of
						audience across the globe and not in the mere presence of few in a
						room, a hall or an auditorium.</li>
				</ul>

				<p2> Be the 'Guest of Honour' and get the feel of releasing
				this book on creativity in the world in the form of a 'STIMULI' and
				be a proud partner of history.</p2>

				<p3>Your name will be entered in the 'Roll of Guest of Honour'
				and the world will view it and appreciate it.</p3>

				<p4>A 'Guest of Honour' will be especially honoured with an
				e-Plaque.</p4>

				<p5>First time in the world awards are instituted for reading a
				book, a unique concept to know about the changing world and earn. </p5>

				<h6>Kindly Accept the Invite....</h6>
			</div>
			<a href="#" class="popper" data-popbox="pop1">Click here to Know
				More >>></a>
			<!----------------slide box end---------------------------->
			<div class="e-mail">
				<div id="login-form">
					<form action="InviteCtl" method="post" target="_top">
						<input id="email" name="email" type="email" class="submit"
							required placeholder="Kindly enter email for updates..."
							onBlur="if(this.value=='')this.value=''"
							onFocus="if(this.value=='Email')this.value='' ">
						<!-- JS because of IE support; better: placeholder="Email" -->
						<input type="submit" name="operation"
							value="I &nbsp;&nbsp;&nbsp; Accept&nbsp;&nbsp;&nbsp; INVITATION"
							id="test">
					</form>
				</div>
				<!-- end login-form -->
			</div>
		</div>
	</div>
	<script src="js/jquery.js" type="text/javascript"></script>
	<script src="js/jquery.plugin.js"></script>
	<script type="text/javascript" src="source/jquery.fancybox.js?v=2.1.5"></script>
	<link rel="stylesheet" type="text/css"
		href="source/jquery.fancybox.css?v=2.1.5" media="screen" />
	<script>
		$("#test")
				.click(
						function() {
							var email = document.getElementById("email").value;
							var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

							if ($.fancybox != null && email != '') {
								if (filter.test(email)) {
									this.value = "Please wait, sending confirmation...";
								}
							}
						});

		$(function() {
			var moveLeft = 0;
			var moveDown = 0;
			$('a.popper').hover(function(e) {
				var target = '#' + ($(this).attr('data-popbox'));
				$(target).show();
				moveLeft = $(this).outerWidth();
				moveDown = ($(target).outerHeight() / 2);
			}, function() {
				var target = '#' + ($(this).attr('data-popbox'));
				$(target).hide();
			});

			$('a.popper')
					.mousemove(
							function(e) {
								var target = '#'
										+ ($(this).attr('data-popbox'));
								leftD = e.pageX + parseInt(moveLeft);
								maxRight = leftD + $(target).outerWidth();
								windowLeft = $(window).width() - 40;
								windowRight = 0;
								maxLeft = e.pageX
										- (parseInt(moveLeft)
												+ $(target).outerWidth() + 20);
								if (maxRight > windowLeft
										&& maxLeft > windowRight) {
									leftD = maxLeft;
								}
								topD = e.pageY - parseInt(moveDown);
								maxBottom = parseInt(e.pageY
										+ parseInt(moveDown) + 20);
								windowBottom = parseInt(parseInt($(document)
										.scrollTop())
										+ parseInt($(window).height()));
								maxTop = topD;
								windowTop = parseInt($(document).scrollTop());
								if (maxBottom > windowBottom) {
									topD = windowBottom
											- $(target).outerHeight() - 20;
								} else if (maxTop < windowTop) {
									topD = windowTop + 20;
								}
							});

		});
	</script>
</body>
</html>
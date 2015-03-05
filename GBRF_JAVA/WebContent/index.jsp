<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<link href="css/main.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/countertest.css" rel="stylesheet" type="text/css"
	media="all" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>GBRF Coming Soon</title>

<style type="text/css">
.fancybox-custom .fancybox-skin {
	box-shadow: 0 0 50px #222;
}

.fancybox-inner {
	height: 500px !important;
}
</style>

<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon">
<link rel="icon" href="img/favicon.ico" type="image/x-icon">

<link rel="stylesheet" type="text/css"
	href="ParallaxContentSlider/css/demo.css" />
<link rel="stylesheet" type="text/css"
	href="ParallaxContentSlider/css/style2.css" />
<script type="text/javascript"
	src="ParallaxContentSlider/js/modernizr.custom.28468.js"></script>
<link
	href='http://fonts.googleapis.com/css?family=Economica:700,400italic'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="css/jquery.countdown.css" />

<noscript>
	<link rel="stylesheet" type="text/css"
		href="ParallaxContentSlider/css/nojs.css" />
</noscript>

<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/script.js" type="text/javascript"></script>
<script src="js/jquery.plugin.js"></script>
<script src="js/jquery.countdown.js"></script>
<script type="text/javascript">
	$(function() {
		var austDay = new Date();
		austDay = new Date('January 26, 2015 04:00:00 pm');
		$('#year').text(austDay.getFullYear());

		var roundDown = function(num) {
			var full = num.toString();
			var reg = /([\d]+)/i;
			var res = reg.exec(full);
			return res[1];
		}

		function test() {
			var today = new Date(); // date and time right now
			var goLive = new Date("February 14, 2015 00:00:00 pm"); // target date
			var diffMs = (goLive - today); // milliseconds between now & target date
			var diffDays = roundDown(diffMs / 86400000); // days
			var diffHrs = roundDown((diffMs % 86400000) / 3600000); // hours
			var diffMins = roundDown(((diffMs % 86400000) % 3600000) / 60000); // minutes
			var diffSecs = roundDown((((diffMs % 86400000) % 3600000) % 60000) / 1000);

			if (diffDays.toString().length == 1) {
				document.getElementById('date').innerHTML = '0' + diffDays
						+ '<br><pstrday>Days</pstrday>';
			} else {
				document.getElementById('date').innerHTML = diffDays
						+ '<br><pstrday>Days</pstrday>';
			}
			if (diffHrs.toString().length == 1) {
				document.getElementById('hour').innerHTML = '0' + diffHrs
						+ '<br><pstrhour>Hours</pstrhour>';
			} else {
				document.getElementById('hour').innerHTML = diffHrs
						+ '<br><pstrhour>Hours</pstrhour>';
			}
			if (diffMins.toString().length == 1) {
				document.getElementById('minute').innerHTML = '0' + diffMins
						+ '<br><pstrminute>Minutes</pstrminute>';
			} else {
				document.getElementById('minute').innerHTML = diffMins
						+ '<br><pstrminute>Minutes</pstrminute>';
			}
			if (diffSecs.toString().length == 1) {
				document.getElementById('second').innerHTML = '0' + diffSecs
						+ '<br><pstrsecond>Seconds</pstrsecond>';
			} else {
				document.getElementById('second').innerHTML = diffSecs
						+ '<br><pstrsecond>Seconds</pstrsecond>';
			}

		}

		setInterval(test, 1000);
	});
</script>
</head>

<body>

	<div id="wrapper">
		<section id="counter">
			<div class="top_block">
				<div class="title">
					<img src="img/logo.png" alt="Global Book Release Forum">

					<div class="col">
						<div class="container">
							<div id="da-slider" class="da-slider">
								<div class="da-slide" style="background: #bf0a30">
									<p>Mothers may still want their favorite sons to grow
									up to be<br>
									president, but they donot want them to<br>
									become politicians in the process€</p>
									<h3>-John F. Kennedy</h3>
								</div>
								<div class="da-slide" style="background: #002868">
									<p
										style="color: #fff; text-align: left; margin-left: -19px; margin-top: 0px; font-family: Times New Roman; height: 140px; overflow: hidden;">Changing
										mothers' mind ...</p>
									<p>
										€œFuture mothers will surely want their favorite sons and
										daughters <br> to grow up to be Presidents, Prime
										Ministers, Chief Ministers, Ministers,<br>and People
										Representatives etc., across the Globe only if die hard <br>professionals
										come forward in numbers for the sake of Welfare Security <br>
										of people, do the Cleansing and make Politics
										<i1>Creative</i1>
										"<br> <i>It is possible NOW!</i>
									</p>
									<h2>
										<i>Creative </i> Youth<br>
										<p
											style="font-size: 12px; float: left; margin-right: 75px; margin-left: -143px; margin-top: -8px;">(Available
											Now)</p>
									</h2>
								</div>
							</div>
						</div>
					</div>

				</div>

				<div class="cal">
					<div class="left_social_icon">
						<a
							href="https://www.facebook.com/CreativePoliticsCreativeIndiaCreativeYouth"
							target="_blank"><img src="img/fb.jpg" /></a> <a
							href="https://in.linkedin.com/in/creativepoliticscreativeindia"
							target="_blank"><img src="img/in.jpg" /></a>
					</div>

					<prel>Release Date</prel>
					<pbas>Being Announced Shortly</pbas>

					<div class="text">
						<p5>LAUNCH DATE</p5>
						<pb>Being</pb>
						<pr>Released</pr>
						<pi>In</pi>
						<p3>February</p3>
						<div class="arwright"></div>
					</div>
				</div>

				<div class="bottom">
					<div class="abt5">
						<a
							href="https://www.youtube.com/channel/UCXVnCPBPGASVSE2-DLXnWyQ/videos"
							target="_blank"><img src="img/yt.jpg"></a>
					</div>
					<ul>
						<li style="margin-left: 15px"><a
							href="https://twitter.com/KissmatIntpub" target="_blank"><img
								src="img/tw.jpg"></a></li>
						<li style="margin-left: 4px"><a
							href="https://plus.google.com/u/0/105319190033976248976/about"
							target="_blank"><img src="img/g+.png"></a></li>
						<li style="margin-left: 4px"><a
							href="http://creativeindiacreativepolitics.blogspot.in/"
							target="_blank"><img src="img/blg.jpg"></a></li>
					</ul>
					<div class="abt">
						<a href="#"><img src="img/about.jpg" id="aboutus"></a>
					</div>
				</div>
		</section>
	</div>
	</div>

	<script type="text/javascript"
		src="ParallaxContentSlider/js/jquery.cslider.js"></script>
	<script type="text/javascript">
		$(function() {

			$('#da-slider').cslider({
				autoplay : true,
				bgincrement : 450,
				interval : 10000
			});

		});
	</script>
	<script type="text/javascript" src="source/jquery.fancybox.js?v=2.1.5"></script>
	<link rel="stylesheet" type="text/css"
		href="source/jquery.fancybox.css?v=2.1.5" media="screen" />

	<script type="text/javascript">
		var fancyobj = null;

		$(document).ready(function() {

			$.fancybox.helpers.overlay.defaults.closeClick = false;

			$.fancybox.open({
				href : 'pop.jsp',
				type : 'iframe',
				'closeBtn' : false,
				padding : 5,
			});

		});
	</script>
</body>
</html>
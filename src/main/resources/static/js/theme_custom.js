	/* ==============================================
	Main Navigation Dropdown Menu
	=============================================== */	
	
	$(window).bind("load", function() {
		$(function(){
			
			'use strict';
			
			$('.js-activated').dropdownHover().dropdown();			
		});
	});

	
	
	/* ==============================================
	Revolution Slider Plugin
	=============================================== */
	$(window).bind("load", function() {
		$(function(){
			
			'use strict';
			
			var revapi;

			jQuery(document).ready(function() {
	
				   revapi = jQuery('.tp-banner').revolution(
					{
						delay:9000,
						startwidth:1170,
						startheight:500,
						hideThumbs:10,
						fullWidth:"on",
					});
	
			});	//ready

		});
	});
	
	/* ==============================================
	Isotope Masonary
	=============================================== */
	$(window).bind("load", function() {
		$(function(){
			
			'use strict';
		
			var container = jQuery('#bloglist');
			container.masonry({
				layoutMode: 'fitRows'
			});
		});
	});


	/* ==============================================
	Parallax Scrolling
	=============================================== */

	$(window).bind("load", function() {
		
		'use strict';
		
		$.stellar({
			responsive: true,
			horizontalScrolling: false,
			verticalOffset: 40
		});
	});
	
	
	/* ==============================================
	Owl Carousel
	=============================================== */

	$(document).ready(function () {
		
		'use strict';
	
		//For Testimonial Slider
		$("#testimonial-slider").owlCarousel({
			items: 1, //10 items above 1000px browser width
			itemsDesktop: [1000, 1], //5 items between 1000px and 901px
			itemsDesktopSmall: [900, 1], // betweem 900px and 601px
			itemsTablet: [600, 1], //2 items between 600 and 0;
			itemsMobile: false, // itemsMobile disabled - inherit from itemsTablet option
			autoPlay: true,
			navigation: false,
			responsive: true,
			pagination: true,
			lazyLoad: true,
			lazyFollow: true,
			lazyEffect: "backSlide",
			transitionStyle: "goDown",
			responsiveRefreshRate: 200,
			responsiveBaseWidth: window,
			stopOnHover: true,
			navigationText: ["", ""],
		});
	});
	
	/* ==============================================
	Custom Javascript For Different Effects
	=============================================== */
	
	$(document).ready(function () {
		
		'use strict';
		
		
		/* Fancybox Lightbox */
		$(".fancybox").fancybox({
			helpers: {
				overlay: {
					locked: false, // try changing to true and scrolling around the page
					title: {
						type: 'outside'
					},
					thumbs: {
						width: 50,
						height: 50
					}
				}
			}
		});
		
		/* Tooltip */
		$('.social_icons a, .tooltips').tooltip({
			placement: 'top',
		});
		
		/* Responsive Videos */
		$(".media.video").fitVids();
		
		/* Twitter Feed */
		$(".tweet-stream").tweet({
			username: "mannatstudio",
			modpath: "twitter/",
			count: 1,
			template: "{text}{time}",
			loading_text: "loading twitter feed..."
		});
		
		/* Google Map */
		$('.google-maps').gmap().bind('init', function(ev, map) {
			$('.google-maps').gmap('addMarker', {
				'position': '-33.87695388579145,151.22183918952942', 
				zoom : 16, 
				'bounds': true,
				}).click(function() {
				$('.google-maps').gmap('openInfoWindow', {'content': 
						'<h4>Office</h4>' +
						'<address>' +
						'<div>' +
						'<div><b>Address:</b></div>' +
						'<div>Envato Pty Ltd, 13/2<br> Elizabeth St Melbourne VIC 3000,<br> Australia</div>' +
						'</div>' +
						'<div>' +
						'<div><b>Phone:</b></div>' +
						'<div>+1 (408) 786 - 5117</div>' +
						'</div>' +
						'<div>' +
						'<div><b>Fax:</b></div>' +
						'<div>+1 (408) 786 - 5227</div>' +
						'</div>' +
						'<div>' +
						'<div><b>Email:</b></div>' +
						'<div><a href="mailto:info@zinestudio.com">info@zinestudio.com</a></div>' +
						'</div>' +
						'</address>',
				}, this);
			});
		});
		
		/* Client Box */
		$(".client-box img").css({
			"opacity": "0.5"
		});
		$(".client-box img").hover(function () {
			$(this).stop().animate({
				opacity: 1,
				top: "-5px"
			}, 300)
		}, function () {
			$(this).stop().animate({
				opacity: 0.5,
				top: "0"
			}, 300)
		});
		
		/* Flickr Photostream */
		if ($.fn.jflickrfeed) $(".flickr-stream ul").jflickrfeed({
			qstrings: {
				id: "52617155@N08"
			},
			limit: 12,
			itemTemplate: '<li><a href="{{link}}" title="{{title}}" target="_blank"><img src="{{image_s}}" alt="{{title}}" /></a></li>'
		});
		$(".accordion-group").each(function () {
			var that = $(this);
			$(this).find(".accordion-heading a").on("click", function () {
				that.parent().find(".accordion-heading a.active").removeClass("active");
				$(this).toggleClass("active")
			})
		});
		
		/* IN AND OUT EFFECT FOR CAROUSEL */
		$('.item-on-hover, .item-on-hover-white').hover(function(){		 		 
			$(this).animate({ opacity: 1 }, 200);
			$(this).children('.hover-link, .hover-image, .hover-video').animate({ opacity: 1 }, 200);
		}, function(){
			$(this).animate({ opacity: 0 }, 200);
			$(this).children('.hover-link, .hover-image, .hover-video').animate({ opacity: 0 }, 200);
		});
		
		
		/* hide #back-top first */
		$("#back-top").hide();		
		// fade in #back-top
		$(function () {
			$(window).scroll(function () {
				if ($(this).scrollTop() > 100) {
					$('#back-top').fadeIn();
				} else {
					$('#back-top').fadeOut();
				}
			});	
			// scroll body to 0px on click
			$('#back-top a').click(function () {
				$('body,html').animate({
					scrollTop: 0
				}, 800);
				return false;
			});
		});	
		
		/* Placeholder For IE */
		$('input[placeholder]').each(function(){
			var $placeInput = $(this);			
			if( 'placeholder' in document.createElement('input') ) {
				var placeholder = true;
			}
			else {
				var placeholder = false;
				$placeInput.val( $placeInput.attr('placeholder') );
			}			
			if( !placeholder ) {
				$placeInput.focusin(function(){
					if( $placeInput.val() === $placeInput.attr('placeholder') ) {				
						$placeInput.val('');				
					}
				})
				.focusout(function(){
					if( $placeInput.val() === '' ) {
						$placeInput.val( $placeInput.attr('placeholder') );
					}
				});
			}		
		});
		
		/* Custom javascript End */
	});

	/* ==============================================
	Contact Form
	=============================================== */

	$(document).ready(function () {
		
		'use strict';
		
		jQuery("#contact_form").validate({
			meta: "validate",
			submitHandler: function (form) {
				
				var s_name=$("#name").val();
				var s_lastname=$("#lastname").val();
				var s_email=$("#email").val();
				var s_phone=$("#phone").val();
				var s_comment=$("#comment").val();
				$.post("contact.php",{name:s_name,lastname:s_lastname,email:s_email,phone:s_phone,comment:s_comment},
				function(result){
				  $('#sucessmessage').append(result);
				});
				$('#contact_form').hide();
				return false;
			},
			/* */
			rules: {
				name: "required",
				
				lastname: "required",
				// simple rule, converted to {required:true}
				email: { // compound rule
					required: true,
					email: true
				},
				phone: {
					required: true,
				},
				comment: {
					required: true
				}
			},
			messages: {
				name: "Please enter your name.",
				lastname: "Please enter your last name.",
				email: {
					required: "Please enter email.",
					email: "Please enter valid email"
				},
				phone: "Please enter a phone.",
				comment: "Please enter a comment."
			},
		}); /*========================================*/
	});
	
	/* ==============================================
	Animated Skills Bar
	=============================================== */

	$(document).ready(function () {
		
		'use strict';

		jQuery(function() {	
			jQuery('.skillbar').appear(function() {
				jQuery('.skillbar').each(function(){
					jQuery(this).find('.skillbar-bar').animate({
						width:jQuery(this).attr('data-percent')
					},6000);
				});
			});
		});
	});

	/* ==============================================
	Header Shrink Animation
	=============================================== */

	$(document).ready(function () {
		
		'use strict';

		$(function(){
		 var shrinkHeader = 130;
		  $(window).scroll(function() {
			var scroll = getCurrentScroll();
			  if ( scroll >= shrinkHeader ) {
				   $('header').addClass('shrink');
				}
				else {
					$('header').removeClass('shrink');
				}
		  });
		function getCurrentScroll() {
			return window.pageYOffset || document.documentElement.scrollTop;
			}
		});
	});
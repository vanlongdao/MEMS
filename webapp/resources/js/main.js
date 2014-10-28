Offline.options = {
	checks : {
		xhr : {
			url : function() {
				return '/favicon.ico?_='
						+ (Math.floor(Math.random() * 1000000000));
			},
			timeout : 5000
		},
		image : {
			url : function() {
				return '/favicon.ico?_='
						+ (Math.floor(Math.random() * 1000000000));
			}
		},
		active : 'xhr'
	},
	// Should we check the connection status immediatly on page load.
	checkOnLoad : true,

	// Should we monitor AJAX requests to help decide if we have a connection.
	interceptRequests : true,

	// Should we automatically retest periodically when the connection is down
	// (set to false to disable).
	reconnect : {

		// How many seconds should we wait before rechecking.
		initialDelay : 3,

		// How long should we wait between retries.
		delay : (1.5 * 1)

	},

	// Should we store and attempt to remake requests which fail while the
	// connection is down.
	requests : true,

	// Should we show a snake game while the connection is down to keep the user
	// entertained?
	// It's not included in the normal build, you should bring in js/snake.js in
	// addition to

	// offline.min.js.
	game : false

};

jQuery(document).ready(function(){
    jQuery("#drawingForm").ready(function(){
    	var scribble = $("#myDrawing").scribble();
        var scribbleFidel = $("#myDrawing").data("scribble");
    });
});

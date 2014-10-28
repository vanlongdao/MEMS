(function ($) {	
	var getCanvasLocalCoordinates = function(theCanvas, touchEvent) {		
		var casvasOffset = $(theCanvas).offset();
		
		return({
			x: (touchEvent.pageX - casvasOffset.left),
			y: (touchEvent.pageY - casvasOffset.top)
		});
	}; 

	
	var getTouchEvent = function(event) {
        // for multi-touch events, just return the first touch event
        // The touch event is NOT part of the jQuery event object. Access the orginalEvent to retrieve touch event
        if (event.originalEvent.targetTouches) return event.originalEvent.targetTouches[0];
        return event;
	};
	
	
    $.fn.makeScribble = function(lineWidth, strokeStyle) {    	
        return this.each(function () {
        	var canvas = this;
        	var pen = canvas.getContext("2d");
        	var jCanvas = $(this);        	
        	var currentPath = [];
        	
        	pen.lineWidth = lineWidth;        	
        	pen.strokeStyle = strokeStyle;
        	
        	canvas.clear = function() {        		
        		pen.clearRect(0,0, canvas.width, canvas.height);        		
        		canvas.scribble = [];            	
        	};
        	
        	canvas.redraw = function() {        		
				pen.clearRect(0,0, canvas.width, canvas.height);
				
				for(var i=0;i < canvas.scribble.length;i++) {
					pen.lineWidth = canvas.scribble[i].lineWidth;					
					pen.strokeStyle = canvas.scribble[i].strokeStyle;
					
					pen.beginPath();
					pen.moveTo(canvas.scribble[i].path[0][0] , canvas.scribble[i].path[0][1]);
					for(var j=1;j<canvas.scribble[i].path.length;j++){
						pen.lineTo(canvas.scribble[i].path[j][0] , canvas.scribble[i].path[j][1]);
					}
					
					pen.stroke();
				}        		
        	};        	
        	
        	canvas.getScribbleJSON = function() {
        		return JSON.stringify({
        			lines: canvas.scribble,
					width: canvas.width,
					height: canvas.height
        		});
        	};
        	
        	canvas.setScribble = function(jsonObject) {
				if (jsonObject && jsonObject.lines) {
					canvas.scribble = jsonParse(jsonObject.lines);	            	
	            	canvas.redraw();
				}
        	};        	
        	
        	canvas.clear();
        	
			
			var onTouchMove = function(event) {				
				// Get the native touch event.
				var touch = getTouchEvent(event);
 
				// Get the local position of the touch event				
				var localPosition = getCanvasLocalCoordinates(canvas, touch);				
 
				// Draw a line from the last pen point to the current touch point.
				pen.lineTo( localPosition.x, localPosition.y );
 
				// Render the line.
				pen.stroke();

				// Save current position to currentPath
				currentPath.push([localPosition.x, localPosition.y]);
				
				// Return FALSE to prevent the default behavior of the touch event (scroll / gesture) since
				// we only want this to perform a drawing operation on the canvas.				
				return false;
			};
			
			var onTouchEnd = function(event) {
				canvas.scribble.push({
					path: currentPath,
					lineWidth: pen.lineWidth,					
					strokeStyle: pen.strokeStyle
				});
				
				// clear currentPath
				currentPath=[];

                // Unbind event listeners.
				jCanvas.unbind("touchmove");
                jCanvas.unbind("mousemove");
				jCanvas.unbind("touchend");
                jCanvas.unbind("mouseup");                
                jCanvas.unbind("touchcancel");
                jCanvas.unbind("mouseout");
                
                canvas.redraw();
                
                // Return FALSE to prevent the default behavior of the touch event (scroll / gesture) since
				// we only want this to perform a drawing operation on the canvas.                
                return false;
			};
			
			var onTouchStart = function(event){				
				// Get the native touch event.
				var touch = getTouchEvent(event);
 
				// Get the local position of the touch event				
				var localPosition = getCanvasLocalCoordinates(canvas, touch);				
 
				// Since we are starting a new line, let's move the pen to the new point and beign a path.
				pen.beginPath();
				pen.moveTo(localPosition.x, localPosition.y);				

				// Save current position to currentPath
				currentPath=[];				
				currentPath.push([localPosition.x, localPosition.y]);
 
				// Now that we have initiated a line, we need to bind the touch/mouse event listeners.
				jCanvas.bind("touchmove", onTouchMove);
                jCanvas.bind("mousemove", onTouchMove);
 
				// Bind the touch/mouse end events so we know when to end the line.
				jCanvas.bind("touchend", onTouchEnd);
				jCanvas.bind("mouseup", onTouchEnd);
				jCanvas.bind("touchcancel", onTouchEnd);
				jCanvas.bind("mouseout", onTouchEnd);				
				
				// Return FALSE to prevent the default behavior of the touch event (scroll / gesture) since
				// we only want this to perform a drawing operation on the canvas.
				return false;
			};

			jCanvas.bind("touchstart", onTouchStart);
            jCanvas.bind("mousedown", onTouchStart);
        });
    };
})(jQuery);



jQuery(function() {
    jQuery("canvas.scribble").makeScribble(2, "blue");
});
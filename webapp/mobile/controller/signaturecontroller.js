/**
 * 
 */

/**
 * Created by arrow on 17/09/2014.
 */
var canvas,sketch,ctx,compile,scope;
var isDraw = false;
var app = angular.module('myapp',[]);
app.controller('SignatureController',function($scope,$compile){
    $scope.mouse = {x: 0 , y: 0};
    $scope.color = 'Black';
    $scope.size = 1;
    $scope.image = null;

    scope = $scope;
    compile = $compile;

    $scope.startDraw = startDraw;
    $scope.endDraw = endDraw;
    $scope.drawing = drawing;
    $scope.clear = clear;
    $scope.done = done;
});

pageLoad();

function pageLoad(){
//    canvas = document.createElement('canvas');
//    canvas.setAttribute('width',sketch.width);
//    canvas.setAttribute('height',sketch.height);
//    canvas.setAttribute('id','canvas');
//    canvas.setAttribute('ng-mousedown','mouseDown($event)');
//    canvas.setAttribute('ng-mouseup','mouseUp($event)');
//    canvas.setAttribute('ng-mousemove','mouseMove($event)');
//
//    sketch.appendChild(canvas);
//
//    compile(sketch)(scope);
//

    canvas = document.querySelector('#paint');
    ctx = canvas.getContext('2d');

    sketch = document.querySelector('#sketch');
    var sketch_style = getComputedStyle(sketch);
    canvas.width = parseInt(sketch_style.getPropertyValue('width'));
    canvas.height = parseInt(sketch_style.getPropertyValue('height'));
}

function startDraw(event){
    isDraw = true;
    scope.mouse.x = event.layerX;
    scope.mouse.y = event.layerY;
}

function endDraw(event){
    isDraw = false;
}

function drawing(event){
    if(isDraw){
        ctx.beginPath();
        ctx.strokeStyle = scope.color;
        ctx.lineWidth = 5;
        ctx.lineJoin = 'round';
        ctx.lineCap = 'round';

        ctx.moveTo(scope.mouse.x,scope.mouse.y);
        ctx.lineTo(event.layerX, event.layerY);
        ctx.closePath();
        ctx.stroke();

        scope.mouse.x = event.layerX;
        scope.mouse.y = event.layerY;
    }
}

function clear(){
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}

function done(){
    scope.image = canvas.toDataURL('image/png').split(',');    
}
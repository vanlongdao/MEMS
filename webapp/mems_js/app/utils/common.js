/**
 * Created by michael on 10/21/14.
 */
function toggleOnlyOne(enabledTab){
    if(document.querySelector(enabledTab).opened){
        document.querySelector(enabledTab).toggle();
        return;
    }

    [].forEach.call(document.querySelectorAll("core-collapse"), function(tab){
        if(tab.opened){
            tab.toggle();
        }
    } );
    document.querySelector(enabledTab).opened=true;
}

function toggleCurrent(currentTab){
    document.querySelector(currentTab).toggle();
}

function stickHeader(selector){
    $(selector).stick_in_parent();

    var div = $(selector);
    var start = $(div).offset().top;

    $.event.add(window, "scroll", function() {
        var p = $(window).scrollTop();
        $(div).css('position',((p)>start) ? 'fixed' : 'static');
        $(div).css('top',((p)>start) ? '0px' : '');
    });
}

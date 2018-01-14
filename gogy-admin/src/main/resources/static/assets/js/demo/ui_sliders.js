"use strict";
$(document).ready(function() {
    $("#slider-default").slider();
    $("#slider-snap-inc").slider({
        value: 100,
        min: 0,
        max: 500,
        step: 50,
        slide: function(a, b) {
            $("#slider-snap-inc-amount").text("$" + b.value)
        }
    });
    $("#slider-snap-inc-amount").text("$" + $("#slider-snap-inc").slider("value"));
    $("#slider-range").slider({
        range: true,
        min: 0,
        max: 500,
        values: [75, 300],
        slide: function(a, b) {
            $("#slider-range-amount").text("$" + b.values[0] + " - $" + b.values[1])
        }
    });
    $("#slider-range-amount").text("$" + $("#slider-range").slider("values", 0) + " - $" + $("#slider-range").slider("values", 1));
    $("#slider-range-min").slider({
        range: "min",
        value: 37,
        min: 1,
        max: 700,
        slide: function(a, b) {
            $("#slider-range-min-amount").text("$" + b.value)
        }
    });
    $("#slider-range-min-amount").text("$" + $("#slider-range-min").slider("value"));
    $("#slider-range-max").slider({
        range: "max",
        min: 1,
        max: 700,
        value: 300,
        slide: function(a, b) {
            $("#slider-range-max-amount").text("$" + b.value)
        }
    });
    $("#slider-range-max-amount").text("$" + $("#slider-range-max").slider("value"));
    $("#slider-vertical-multiple > span").each(function() {
        var a = parseInt($(this).text(), 10);
        $(this).empty().slider({
            value: a,
            range: "min",
            animate: true,
            orientation: "vertical"
        })
    });
    $("#slider-vertical-range-min").slider({
        range: "min",
        value: 500,
        min: 1,
        max: 700,
        orientation: "vertical",
        slide: function(a, b) {
            $("#slider-vertical-range-min-amount").text("$" + b.value)
        }
    });
    $("#slider-vertical-range-min-amount").text("$" + $("#slider-vertical-range-min").slider("value"))
});
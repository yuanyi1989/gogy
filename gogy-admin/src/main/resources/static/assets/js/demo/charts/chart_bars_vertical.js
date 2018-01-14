"use strict";
$(document).ready(function() {
    var e = [];
    for (var a = 0; a <= 7; a += 1) {
        e.push([a, parseInt(Math.random() * 30)])
    }
    var c = [];
    for (var a = 0; a <= 7; a += 1) {
        c.push([a, parseInt(Math.random() * 30)])
    }
    var b = [];
    for (var a = 0; a <= 7; a += 1) {
        b.push([a, parseInt(Math.random() * 30)])
    }
    var d = new Array();
    d.push({
        label: "Bar #1",
        data: e,
        bars: {
            show: true,
            barWidth: 0.2,
            order: 1
        }
    });
    d.push({
        label: "Bar #2",
        data: c,
        bars: {
            show: true,
            barWidth: 0.2,
            order: 2
        }
    });
    d.push({
        label: "Bar #3",
        data: b,
        bars: {
            show: true,
            barWidth: 0.2,
            order: 3
        }
    });
    $.plot("#chart_bars_vertical", d, $.extend(true, {},
        Plugins.getFlotDefaults(), {
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: false
                }
            },
            grid: {
                hoverable: true
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s: %y"
            }
        }))
});
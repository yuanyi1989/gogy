"use strict";
$(document).ready(function() {
    var d = [];
    var c = [];
    for (var a = 0; a < 200; a += 5) {
        var g = Math.floor(50 - 15 + Math.random() * 30);
        d.push([a, g])
    }
    function f() {
        if (c.length > 0) {
            c = c.slice(1)
        }
        while (c.length < 200) {
            var k = c.length > 0 ? c[c.length - 1] : 50;
            var l = k + Math.random() * 10 - 5;
            if (l < 0) {
                l = 0
            }
            if (l > 100) {
                l = 100
            }
            c.push(l)
        }
        var j = [];
        for (var h = 0; h < c.length; ++h) {
            j.push([h, c[h]])
        }
        return j
    }
    var b = [{
        label: "Used RAM",
        data: f(),
        color: App.getLayoutColorCode("red"),
        lines: {
            fill: true
        },
        points: {
            show: false
        }
    },
        {
            label: "Server load",
            data: d,
            color: App.getLayoutColorCode("blue")
        }];
    var e = $.plot("#chart_multiple", b, $.extend(true, {},
        Plugins.getFlotDefaults(), {
            series: {
                lines: {
                    show: true
                },
                points: {
                    show: true
                },
                grow: {
                    active: true
                }
            },
            grid: {
                hoverable: true,
                clickable: true
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s: %y"
            }
        }))
});
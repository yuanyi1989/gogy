"use strict";
$(document).ready(function() {
    var a = [],
        c = [];
    for (var b = 0; b < 16; b += 0.5) {
        a.push([b, Math.sin(b)]);
        c.push([b, Math.cos(b)])
    }
    var d = $.plot("#chart_simple", [{
        data: a,
        label: "sin(x)"
    },
        {
            data: c,
            label: "cos(x)"
        }], $.extend(true, {},
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
            yaxis: {
                min: -1.1,
                max: 1.1
            },
            xaxis: {
                min: 0,
                max: 15
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s: %y"
            }
        }))
});
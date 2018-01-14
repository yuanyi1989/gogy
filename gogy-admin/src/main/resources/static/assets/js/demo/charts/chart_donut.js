"use strict";
$(document).ready(function() {
    var a = [];
    var c = Math.floor(Math.random() * 10) + 1;
    for (var b = 0; b < c; b++) {
        a[b] = {
            label: "Series " + (b + 1),
            data: Math.floor(Math.random() * 100) + 1
        }
    }
    $.plot("#chart_donut", a, $.extend(true, {},
        Plugins.getFlotDefaults(), {
            series: {
                pie: {
                    show: true,
                    innerRadius: 0.5,
                    radius: 1
                }
            },
            grid: {
                hoverable: true
            },
            tooltip: true,
            tooltipOpts: {
                content: "%p.0%, %s",
                shifts: {
                    x: 20,
                    y: 0
                }
            }
        }))
});
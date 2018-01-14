"use strict";
$(document).ready(function() {
    var a = [];
    for (var d = 0; d <= 3; d += 1) {
        a.push([parseInt(Math.random() * 30), d])
    }
    var b = [];
    for (var d = 0; d <= 3; d += 1) {
        b.push([parseInt(Math.random() * 30), d])
    }
    var c = [];
    for (var d = 0; d <= 3; d += 1) {
        c.push([parseInt(Math.random() * 30), d])
    }
    var e = new Array();
    e.push({
        label: "Bar #1",
        data: a,
        bars: {
            show: true,
            barWidth: 0.2,
            order: 1
        }
    });
    e.push({
        label: "Bar #2",
        data: b,
        bars: {
            show: true,
            barWidth: 0.2,
            order: 2
        }
    });
    e.push({
        label: "Bar #3",
        data: c,
        bars: {
            show: true,
            barWidth: 0.2,
            order: 3
        }
    });
    $.plot("#chart_bars_horizontal", e, $.extend(true, {},
        Plugins.getFlotDefaults(), {
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: false
                },
                bars: {
                    fillColor: {
                        colors: [{
                            opacity: 1
                        },
                            {
                                opacity: 0.7
                            }]
                    },
                    horizontal: true
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
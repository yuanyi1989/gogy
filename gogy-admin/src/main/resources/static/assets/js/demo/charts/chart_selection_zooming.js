"use strict";
$(document).ready(function() {
    function b(h, g) {
        var k = [];
        for (var j = 0; j <= 100; ++j) {
            var f = h + j * (g - h) / 100;
            k.push([f, Math.sin(f * Math.sin(f))])
        }
        return [{
            label: "sin(x sin(x))",
            data: k
        }]
    }
    var d = b(0, 3 * Math.PI);
    var c = $.extend(true, {},
        Plugins.getFlotDefaults(), {
            legend: {
                show: false
            },
            series: {
                lines: {
                    show: true
                },
                points: {
                    show: true
                }
            },
            yaxis: {
                ticks: 10
            },
            selection: {
                mode: "x"
            }
        });
    var e = $.plot("#chart_selection_zooming", d, c);
    var a = $.plot("#chart_selection_zooming_overview", d, $.extend(true, {},
        Plugins.getFlotDefaults(), {
            legend: {
                show: false
            },
            series: {
                lines: {
                    show: true,
                    lineWidth: 1
                },
                shadowSize: 0
            },
            xaxis: {
                ticks: 3
            },
            yaxis: {
                ticks: 1
            },
            selection: {
                mode: "x"
            }
        }));
    $("#chart_selection_zooming").bind("plotselected",
        function(g, f) {
            if (f.xaxis.to - f.xaxis.from < 0.00001) {
                f.xaxis.to = f.xaxis.from + 0.00001
            }
            if (f.yaxis.to - f.yaxis.from < 0.00001) {
                f.yaxis.to = f.yaxis.from + 0.00001
            }
            e = $.plot("#chart_selection_zooming", b(f.xaxis.from, f.xaxis.to), $.extend(true, {},
                c, {
                    xaxis: {
                        min: f.xaxis.from,
                        max: f.xaxis.to
                    },
                    yaxis: {
                        min: f.yaxis.from,
                        max: f.yaxis.to
                    }
                }));
            a.setSelection(f, true)
        });
    $("#chart_selection_zooming_overview").bind("plotselected",
        function(g, f) {
            e.setSelection(f)
        })
});
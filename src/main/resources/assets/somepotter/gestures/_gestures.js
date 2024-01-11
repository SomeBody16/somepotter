// https://jsfiddle.net/91hu4jg3/5/


var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");
var points = [];
var strokeId = 0;

const dotDistance = 1;

ctx.fillStyle = 'black';

const colors = ['black', 'red', 'green', 'blue', 'magenta']

function nextStroke() {
    strokeId++;
}

function addPoint(x, y) {
    const lastPoint = points[points.length - 1];
    x = Math.round(x)
    y = Math.round(y)
    points.push({x, y, strokeId})
}

function drawDottedLine(x1, y1, x2, y2) {
    const dx = x2 - x1;
    const dy = y2 - y1;
    const length = Math.sqrt(dx * dx + dy * dy);
    const steps = length / dotDistance;
    const xStep = dx / steps;
    const yStep = dy / steps;

    for (let i = 0; i <= steps; i++) {
        const x = x1 + i * xStep;
        const y = y1 + i * yStep;

        addPoint(x, y)
    }
}

function drawDottedBezierCurve(startX, startY, cp1x, cp1y, cp2x, cp2y, endX, endY) {
    function getBezierPoint(t) {
        const u = 1 - t;
        const tt = t * t;
        const uu = u * u;
        const uuu = uu * u;
        const ttt = tt * t;

        let p = {x: 0, y: 0};
        p.x = uuu * startX;
        p.x += 3 * uu * t * cp1x;
        p.x += 3 * u * tt * cp2x;
        p.x += ttt * endX;

        p.y = uuu * startY;
        p.y += 3 * uu * t * cp1y;
        p.y += 3 * u * tt * cp2y;
        p.y += ttt * endY;

        return p;
    }

    // Adjust the increment of t based on the curve length and dot distance
    const approximateLength = Math.hypot(cp1x - startX, cp1y - startY) +
        Math.hypot(cp2x - cp1x, cp2y - cp1y) +
        Math.hypot(endX - cp2x, endY - cp2y);
    const tIncrement = dotDistance / approximateLength;

    for (let t = 0; t <= 1; t += tIncrement) {
        const point = getBezierPoint(t);
        addPoint(point.x, point.y);
    }
}

function drawDottedCircle(cx, cy, r) {
    const circumference = 2 * Math.PI * r;
    const steps = circumference / dotDistance;

    for (let i = 0; i < steps; i++) {
        const theta = (i / steps) * 2 * Math.PI;
        const x = cx + r * Math.cos(theta);
        const y = cy + r * Math.sin(theta);

        addPoint(x, y)
    }
}

//drawDottedLine(10, 10, 90, 90)
//drawDottedBezierCurve(5,95, 25,10, 75,10, 95,95)
//drawDottedCircle(50, 50, 20)

// CODE HERE


var data = {name, points};
document.getElementById("coords").value = JSON.stringify(data, null, 2);

for (let point of points) {
    ctx.fillStyle = colors[point.strokeId % colors.length]
    ctx.beginPath();
    ctx.arc(point.x, point.y, 1, 0, 2 * Math.PI);
    ctx.fill();
}

var fs = require('fs');

var data = fs.readFileSync('./marks.txt', 'UTF8')
if (!data) { return false; }

var marks = data.split('\n');
marks.pop();

var sorted = new Array();

for (var i in marks) {
    marks[i] = marks[i].split(' ');
    marks[i][1] = parseFloat(marks[i][1]);
    sorted.push({id: marks[i][0], grade: marks[i][1]});
}

var done = sorted.sort(function(a,b) {
    return b.grade - a.grade;
});

console.log(done);
console.log("Count: " + marks.length);

var sum = 0;
for (var i in marks) {
    sum += marks[i][1];
}
console.log("Sum: " + sum);

var mean = sum / marks.length;
console.log("Mean: " + mean);

var stdDev = (57.45 - mean) / 1.405;
console.log("Std dev: " + stdDev);

var hist = {
    "1": 0,
    "2": 0,
    "3": 0,
    "4": 0,
    "5": 0,
    "6": 0,
    "7": 0,
    "8": 0,
    "9": 0,
    "10": 0,
    "11": 0,
    "12": 0
}

for (var i in done) {
    var gr = done[i].grade;
    if (gr >= 30 && gr < 32.5) { hist[1]++ }
    else if (gr >= 32.5 && gr < 35) { hist[2]++ }
    else if (gr >= 35  && gr < 37.5) { hist[3]++ }
    else if (gr >= 37.5&& gr < 40  ) { hist[4]++ }
    else if (gr >= 40  && gr < 42.5) { hist[5]++ }
    else if (gr >= 42.5&& gr < 45  ) { hist[6]++ }
    else if (gr >= 45  && gr < 47.5) { hist[7]++ }
    else if (gr >= 47.5&& gr < 50  ) { hist[8]++ }
    else if (gr >= 50  && gr < 52.5) { hist[9]++ }
    else if (gr >= 52.5&& gr < 55  ) { hist[10]++ }
    else if (gr >= 55  && gr < 57.5) { hist[11]++ }
    else if (gr >= 57.5&& gr < 60  ) { hist[12]++ }
}
console.log(hist);

for (var i in hist) {
    var num = hist[i];
    var bar = "";
    for (var j = 0; j < num; j++) {
        bar += "*";
    }
    console.log(bar);
}

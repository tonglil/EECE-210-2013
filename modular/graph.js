helper = require('./helper.js');

// Outputs general stats about input
// @par:    object  list of grades
// @par:    string  grade column name (default 'grade')
exports.stats = function(data, colName) {
    colName = (colName || 'grade');

    var count = data.length;
    console.log("Count: " + count);

    var sum = 0;
    for (var i in data) {
        sum += data[i][colName];
    }

    var mean = sum / count;
    console.log("Mean: " + mean);

    var stdDev = (57.45 - mean) / 1.405;
    console.log("Standard Deviation: " + stdDev);
}

// Graphs the given data into a horizontal bar graph
// @par:    object  descending sorted list of grades
// @par:    string  grade column name (default 'grade')
// @par:    number  categories to plot (default 5)
// @par:    number  graph min (default calculated)
// @par:    number  graph max (default calculated)
// @ret:    none
exports.plot = function(input, colName, categories, min, max) {
    colName = (colName || 'grade');
    categories = (categories || 5);

    var data = helper.clone(input);

    var hist = Array.apply(null, new Array(categories)).map(Number.prototype.valueOf,0);

    var count = data.length;

    // TODO: finds value assuming sorted
    max = (max || Math.ceil(data[0][colName]));
    min = (min || Math.floor(data[count-1][colName]));

    var interval = (max - min) / categories;
    var divisions = new Array();

    var current = min;
    divisions.push(current);

    while (current < max) {
        current = (Math.round((current + interval) * 10) / 10);
        divisions.push(current);
    }

    var maxCount = 0;
    for (var i = 0; i < categories; i++) {
        for (var j in data) {
            var grade = data[j][colName];
            if (grade >= divisions[i] && grade < divisions[i+1]) {
                hist[i]++;
                // TODO this is hacky...
                delete data[j][colName];
            }
        }
        var set = hist[i].toString().length;
        if (set > maxCount) maxCount = set;
    }

    console.log();
    for (var i in hist) {
        var num = hist[i];
        var bar = hist[i] + ' ';
        while (bar.length < maxCount + 1) {
            bar += ' ';
        }
        bar += '|';
        for (var j = 0; j < num; j++) {
            bar += '*';
        }
        console.log(bar);
    }
}

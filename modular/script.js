var parser = require('./parser.js');
var sort = require('./sort.js');
var graph = require('./graph.js');

var fs = require('fs');

var data = fs.readFileSync('../marks.csv', 'UTF8');

var parsed = parser.csvToArray(data);
//console.log(parsed);

var cleaned = parser.cleanData(parsed);
//console.log(cleaned);

var sorted = sort.sort(cleaned);
//console.log(sorted);

var sorted = sort.sort(cleaned, null, 'asc');
//console.log(sorted);

graph.stats(sorted);
graph.plot(sorted, null , 12);
graph.plot(sorted, null , 12, 30, 60);

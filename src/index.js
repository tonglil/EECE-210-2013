var fs = require('fs');
var path = require('path');
var readline = require('readline');
var parser = require(path.resolve(__dirname, 'parser.js'));
var helper = require(path.resolve(__dirname, 'helper.js'));
var graph = require(path.resolve(__dirname, 'graph.js'));

var data = fs.readFileSync(path.resolve(__dirname, '../data/data.csv'));
if (!data) {
    console.log('Error: no input data found');
}

// TODO: get input...
// your student number (for ranking th/all)
// option to attach names to ids?
var rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

// make sync
var sid;
rl.question('Enter your student number for ranking: ', function(answer) {
    sid = answer;
    rl.close();
});

var parsed = parser.csvToArray(data);
//console.log(parsed);

var cleaned = parser.cleanData(parsed);
//console.log(cleaned);

var sorted = helper.sort(cleaned, null, 'asc');
//console.log(sorted);

var sorted = helper.sort(cleaned);
//console.log(sorted);

graph.stats(sorted);
graph.plot(sorted, null , 12);
graph.plot(sorted, null , 12, 30, 60);

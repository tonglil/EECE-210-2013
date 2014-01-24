var exec = require('child_process').exec;

//http://stackoverflow.com/questions/10730712/download-unpublished-google-spreadsheet-as-csv
//wget --no-check-certificate --output-document=test.csv 'https://docs.google.com/spreadsheet/ccc?key=0ArnTyCD0ZB-_dDh1TGp1UVAyM3owMDQyd1p3R2hIbEE&output=csv'

var options = '--no-check-certificate --output-document=../data/data.csv';
var url = 'http://docs.google.com/spreadsheet/ccc?key=0ArnTyCD0ZB-_dDh1TGp1UVAyM3owMDQyd1p3R2hIbEE';
var format = 'csv';
var command = 'wget ' + options + ' ' + '"' + url + '&output=' + format + '"';
child = exec(command, function (error, stdout, stderr) {
    if (error) {
        console.log(error.toString());
    } else {
        console.log("Data retrieved!");
    }
});

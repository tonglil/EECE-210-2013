var fs = require('fs');

var data = fs.readFileSync('../marks.csv', 'UTF8');

var parsed = csvToArray(data, ",");
parsed.pop();
parsed.pop();

var cleaned = new Array();
var width = parsed[0].length - 1;

for (var i in parsed) {
    for (var j = 1; j <= width; j++) {
        var num = parseFloat(parsed[i][j]);
        if (isNaN(num)) {
            parsed[i][j] = 0;
        } else {
            parsed[i][j] = num;
        }
    }
    cleaned.push({id: parsed[i][0], currentGrade: parsed[i][width]});
}

console.log(parsed);
console.log(cleaned);

// http://www.bennadel.com/blog/1504-Ask-Ben-Parsing-CSV-Strings-With-Javascript-Exec-Regular-Expression-Command.htm
// This will parse a delimited string into an array of
// arrays. The default delimiter is the comma, but this
// can be overriden in the second argument.
function csvToArray(strData, strDelimiter) {
    strDelimiter = (strDelimiter || ",");

    // Create a regular expression to parse the CSV values
    var objPattern = new RegExp(
        (
            // Delimiters
            "(\\" + strDelimiter + "|\\r?\\n|\\r|^)" +

            // Quoted fields
            "(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|" +

            // Standard fields
            "([^\"\\" + strDelimiter + "\\r\\n]*))"
    ),
    "gi"
    );

    // Array to hold data, default empty first row
    var arrData = [[]];

    // Array to hold individual pattern matching groups
    var arrMatches = null;

    // Keep looping over the regular expression matches until no matches
    while (arrMatches = objPattern.exec(strData)) {
        // Get the delimiter that was found.
        var strMatchedDelimiter = arrMatches[1];

        // Check to see if the given delimiter has a length
        // (is not the start of string) and if it matches
        // field delimiter. If id does not, then we know
        // that this delimiter is a row delimiter.
        if (strMatchedDelimiter.length && (strMatchedDelimiter != strDelimiter)) {
            // Since we have reached a new row of data,
            // add an empty row to our data array.
            arrData.push([]);
        }

        // Now that we have our delimiter out of the way,
        // let's check to see which kind of value we
        // captured (quoted or unquoted).
        if (arrMatches[2]) {
            // We found a quoted value. When we capture
            // this value, unescape any double quotes.
            var strMatchedValue = arrMatches[2].replace(
                new RegExp( "\"\"", "g" ),
                "\""
            );
        } else {
            // We found a non-quoted value.
            var strMatchedValue = arrMatches[3];
        }

        // Now that we have our value string, let's add
        // it to the data array.
        arrData[arrData.length - 1].push(strMatchedValue);
    }

    // Return the parsed data.
    return(arrData);
}
